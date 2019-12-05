package com.idw.project.notebookstation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.fragment.AkunFragment;
import com.idw.project.notebookstation.fragment.BerandaFragment;
import com.idw.project.notebookstation.fragment.WishlistFragment;
import com.idw.project.notebookstation.fragment.KeranjangFragment;
import com.idw.project.notebookstation.fragment.PesananFragment;
import com.idw.project.notebookstation.util.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    MenuItem navigation_beranda, navigation_wishlist, navigation_keranjang, navigation_akun;
    Menu menu_bottom;
    BottomNavigationView navigation;
    public SessionManager sessionManager;

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
        navigation_akun = menu_bottom.findItem(R.id.navigation_akun);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            String id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

            System.out.println("cek id" + id_konsumen);

        }else {
            displayMenuBelumLogin();
        }

        if (savedInstanceState == null) {
            fragment = new BerandaFragment();
            loadFragment(fragment);

        }else {
            Log.d(String.valueOf(getApplicationContext()), "cek");
        }

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
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

        //set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda Harus Login Terlebih Dahulu")
                .setCancelable(false)
                .setPositiveButton("login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

        //membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        //menampilkan alert dialog
        alertDialog.show();
    }

    //FUNGSI UNTUK BOOTOMNAVIGATION SAAT DI KLIK
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()){
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
