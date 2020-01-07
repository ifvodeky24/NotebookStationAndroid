package com.idw.project.notebookstation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.freshchat.consumer.sdk.exception.MethodNotAllowedException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.fragment.AkunFragment;
import com.idw.project.notebookstation.fragment.BerandaFragment;
import com.idw.project.notebookstation.fragment.WishlistFragment;
import com.idw.project.notebookstation.fragment.KeranjangFragment;
import com.idw.project.notebookstation.fragment.PesananFragment;
import com.idw.project.notebookstation.response.KonsumenDetailResponse;
import com.idw.project.notebookstation.response.UpdateRestoreIdResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    MenuItem navigation_beranda, navigation_wishlist, navigation_keranjang, navigation_pesanan, navigation_akun;
    Menu menu_bottom;
    BottomNavigationView navigation;
    public SessionManager sessionManager;

    String id_konsumen, restoreId, sessionRestoreId, email, nomor_hp;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FUNGSI UNTUK BOTTOM NAVIGATION
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        menu_bottom = navigation.getMenu();

        navigation_beranda = menu_bottom.findItem(R.id.navigation_beranda);
        navigation_wishlist = menu_bottom.findItem(R.id.navigation_wishlist);
        navigation_keranjang = menu_bottom.findItem(R.id.navigation_keranjang);
        navigation_pesanan = menu_bottom.findItem(R.id.navigation_pesanan);
        navigation_akun = menu_bottom.findItem(R.id.navigation_akun);

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (sessionManager.isLoggedIn()) {
            String id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

            System.out.println("cek id" + id_konsumen);

            initFreshChat();

        } else {
            displayMenuBelumLogin();
        }

        if (savedInstanceState == null) {
            fragment = new BerandaFragment();
            loadFragment(fragment);

        } else {
            Log.d(String.valueOf(getApplicationContext()), "cek");
        }

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();


        String token = FirebaseInstanceId.getInstance().getToken();
        Freshchat.getInstance(this).setPushRegistrationToken(token);


    }

    private void initFreshChat() {
        id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);
        sessionRestoreId = sessionManager.getLoginDetail().get(SessionManager.RESTORE_ID);
        email = sessionManager.getLoginDetail().get(SessionManager.EMAIL);
        nomor_hp = sessionManager.getLoginDetail().get(SessionManager.NOMOR_HP);

        restoreId = receiveFreschatRestoreId();

        if (TextUtils.isEmpty(sessionRestoreId)) {
            saveRestoreIdForUser(restoreId);
        } else {
            System.out.println("Data kosong");
        }

        Freshchat freshchat = Freshchat.getInstance(getApplicationContext());
        if (TextUtils.isEmpty(restoreId)) {
            try {
                freshchat.identifyUser(id_konsumen, null);
            } catch (MethodNotAllowedException e) {
                e.printStackTrace();
            }

            String newRestoreId = freshchat.getUser().getRestoreId();
            if (TextUtils.isEmpty(newRestoreId)) {
                // Toast.makeText(this, "Ini...", Toast.LENGTH_SHORT).show();
                receiveFreschatRestoreId();
            } else {
                saveRestoreIdForUser(newRestoreId);
            }
        } else {
            try {
                freshchat.identifyUser(id_konsumen, sessionRestoreId);
            } catch (MethodNotAllowedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Restore ID :" + restoreId);
        System.out.println("Restore ID Session :" + sessionRestoreId);

        //init
        FreshchatConfig freshchatConfig = new FreshchatConfig("7545a00c-1cc5-4eb7-862e-2dec46239b56", "8d2fcdf1-299b-400c-bcfb-b4fe1389d568");
        freshchatConfig.setCameraCaptureEnabled(true);
        freshchatConfig.setGallerySelectionEnabled(true);
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);
        //Update user information
        final FreshchatUser user = Freshchat.getInstance(getApplicationContext()).getUser();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(nomor_hp)) {
            Toast.makeText(getApplicationContext(), "Kosong", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface.konsumenById(id_konsumen).enqueue(new Callback<KonsumenDetailResponse>() {
                @Override
                public void onResponse(Call<KonsumenDetailResponse> call, Response<KonsumenDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getMaster().size() > 0) {
                            String mNamaLengkap = response.body().getMaster().get(0).getNamaLengkap();
                            String mNoHp = response.body().getMaster().get(0).getNomorHp();
                            String mEmail = response.body().getMaster().get(0).getEmail();

                            user
                                    .setFirstName(mNamaLengkap)
                                    .setEmail(mEmail)
                                    .setPhone("+628", mNoHp);
                            System.out.println("Restore nama:" + mNamaLengkap + " email:" + email + " nomor hp:" + nomor_hp);

                            try {
                                Freshchat.getInstance(getApplicationContext()).setUser(user);
                                Freshchat.getInstance(getApplicationContext()).identifyUser(id_konsumen, sessionRestoreId);
                            } catch (MethodNotAllowedException e) {
                                Log.e("FreshchatError", e.toString());
                            }

                            /* Set any custom metadata to give agents more context, and for segmentation for marketing or pro-active messaging */
                            Map<String, String> userMeta = new HashMap<String, String>();
                            userMeta.put("alamat", response.body().getMaster().get(0).getAlamat());
                            userMeta.put("nama_awal", response.body().getMaster().get(0).getNamaLengkap());
                            userMeta.put("email", response.body().getMaster().get(0).getEmail());
                            userMeta.put("nomor_hp", response.body().getMaster().get(0).getNomorHp());
                            userMeta.put("username", response.body().getMaster().get(0).getUsername());
                            userMeta.put("restore_id", response.body().getMaster().get(0).getRestoreId());

                            //Call setUserProperties to sync the user properties with Freshchat's servers
                            try {
                                Freshchat.getInstance(getApplicationContext()).setUserProperties(userMeta);
                            } catch (MethodNotAllowedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<KonsumenDetailResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }

    private void launchFreshchat() {
        Freshchat.showConversations(getApplicationContext());
    }

    private String receiveFreschatRestoreId() {
        IntentFilter intentFilter = new IntentFilter(Freshchat.FRESHCHAT_USER_RESTORE_ID_GENERATED);
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                restoreId = Freshchat.getInstance(context).getUser().getRestoreId();

                System.out.println("Restore ID1 :" + restoreId);
                if (TextUtils.isEmpty(restoreId)) {
                    receiveFreschatRestoreId2();
                } else {
                    saveRestoreIdForUser(restoreId);
                }
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, intentFilter);
        return restoreId;
    }

    private String receiveFreschatRestoreId2() {
        IntentFilter intentFilter = new IntentFilter(Freshchat.FRESHCHAT_USER_RESTORE_ID_GENERATED);
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                restoreId = Freshchat.getInstance(context).getUser().getRestoreId();

                System.out.println("Restore ID2 :" + restoreId);
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, intentFilter);
        return restoreId;
    }

    private void saveRestoreIdForUser(String restoreId) {
        apiInterface.updateRestoreId(id_konsumen, restoreId).enqueue(new Callback<UpdateRestoreIdResponse>() {
            @Override
            public void onResponse(Call<UpdateRestoreIdResponse> call, Response<UpdateRestoreIdResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("cek"+response);
                } else {
                    System.out.println("cek2"+response);
                }
            }

            @Override
            public void onFailure(Call<UpdateRestoreIdResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void displayMenuBelumLogin() {
        navigation_wishlist.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                harus_login();
                return true;
            }
        });

        navigation_keranjang.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                harus_login();
                return true;
            }
        });


        navigation_pesanan.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                harus_login();
                return true;
            }
        });

        navigation_akun.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                harus_login();
                return true;
            }
        });
    }

    private void harus_login() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setCancelable(true);

        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_popup, null);

        alertDialogBuilder.setView(dialogView);

        Button btn_login = dialogView.findViewById(R.id.btn_login);
        TextView tv_nanti_saja = dialogView.findViewById(R.id.tv_nanti_saja);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        tv_nanti_saja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    //FUNGSI UNTUK BOOTOMNAVIGATION SAAT DI KLIK
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    fragment = new BerandaFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_wishlist:
                    fragment = new WishlistFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_keranjang:
                    fragment = new KeranjangFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_pesanan:
                    fragment = new PesananFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_akun:
                    fragment = new AkunFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        //Load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
