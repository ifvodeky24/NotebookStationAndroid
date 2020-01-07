package com.idw.project.notebookstation.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freshchat.consumer.sdk.Freshchat;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailProdukActivity;
import com.idw.project.notebookstation.activity.MainActivity;
import com.idw.project.notebookstation.activity.UbahAkunActivity;
import com.idw.project.notebookstation.activity.UbahPasswordActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.response.KonsumenDetailResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment implements View.OnClickListener {

    private CircleImageView iv_user_images;
    private TextView tv_namalengkap, tv_email, tv_noHp;
    private SessionManager sessionManager;
    private ApiInterface apiInterface;


    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setTitle("Akun");
        }

        LinearLayout ll_pengaturan_akun = view.findViewById(R.id.ll_pengaturan_akun);
        LinearLayout ll_pengaturan_password = view.findViewById(R.id.ll_pengaturan_password);
        LinearLayout keluar = view.findViewById(R.id.keluar);

        iv_user_images = view.findViewById(R.id.iv_user_images);
        tv_namalengkap = view.findViewById(R.id.tv_namalengkap);
        tv_email = view.findViewById(R.id.tv_email);
        tv_noHp = view.findViewById(R.id.tv_noHp);

        sessionManager = new SessionManager(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (sessionManager.isLoggedIn()) {
            apiInterface.konsumenById(sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<KonsumenDetailResponse>() {
                @Override
                public void onResponse(Call<KonsumenDetailResponse> call, Response<KonsumenDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getMaster().size() > 0) {
                            final String foto = response.body().getMaster().get(0).getFoto();
                            String nama_lengkap = response.body().getMaster().get(0).getNamaLengkap();
                            String email = response.body().getMaster().get(0).getEmail();
                            String no_hp = response.body().getMaster().get(0).getNomorHp();


                            tv_namalengkap.setText(nama_lengkap);
                            tv_email.setText(email);
                            tv_noHp.setText(no_hp);

                            if (iv_user_images != null) {
                                Picasso.with(getActivity())
                                        .load(ServerConfig.KONSUMEN_IMAGE + foto)
                                        .into(iv_user_images);
                            } else {
                                Picasso.with(getActivity())
                                        .load(R.drawable.ic_user_image)
                                        .into(iv_user_images);
                            }

                            iv_user_images.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                    alertDialogBuilder.setCancelable(true);
                                    alertDialogBuilder.setTitle("Galeri Foto");
                                    alertDialogBuilder.setIcon(R.drawable.logo);

                                    LayoutInflater inflater = getLayoutInflater();

                                    View dialogView = inflater.inflate(R.layout.foto_produk_dialog, null);

                                    alertDialogBuilder.setView(dialogView);

                                    ImageView iv_tampilkan_foto_konsumen = dialogView.findViewById(R.id.iv_foto_produk);


                                    Picasso.with(getActivity())
                                            .load(ServerConfig.KONSUMEN_IMAGE + foto)
                                            .into(iv_tampilkan_foto_konsumen);

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<KonsumenDetailResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //set on click
        ll_pengaturan_akun.setOnClickListener(this);
        ll_pengaturan_password.setOnClickListener(this);
        keluar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_pengaturan_akun) {
            Intent intent = new Intent(getActivity(), UbahAkunActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ll_pengaturan_password) {
            Intent intent = new Intent(getActivity(), UbahPasswordActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.keluar) {
            sessionManager.logout();
            Freshchat.resetUser(getActivity());
            Intent intent_logout = new Intent(getActivity(), MainActivity.class);
            intent_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            getActivity().finish();
            startActivity(intent_logout);
            Toast.makeText(getActivity(), "Anda Berhasil Keluar", Toast.LENGTH_SHORT).show();

        }

    }
}
