package com.idw.project.notebookstation.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.MainActivity;
import com.idw.project.notebookstation.activity.UbahAkunActivity;
import com.idw.project.notebookstation.activity.UbahPasswordActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment implements View.OnClickListener {

    CircleImageView iv_user_images;
    TextView tv_namalengkap, tv_email, tv_noHp;
    private SessionManager sessionManager;


    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_akun, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
        getActivity().setTitle("Akun");

        LinearLayout ll_pengaturan_akun = view.findViewById(R.id.ll_pengaturan_akun);
        LinearLayout ll_pengaturan_password = view.findViewById(R.id.ll_pengaturan_password);
        LinearLayout keluar = view.findViewById(R.id.keluar);

        iv_user_images = view.findViewById(R.id.iv_user_images);
        tv_namalengkap = view.findViewById(R.id.tv_namalengkap);
        tv_email = view.findViewById(R.id.tv_email);
        tv_noHp = view.findViewById(R.id.tv_noHp);

        sessionManager = new SessionManager(getActivity());

        tv_namalengkap.setText(sessionManager.getLoginDetail().get(SessionManager.NAMA_LENGKAP));
        tv_email.setText(sessionManager.getLoginDetail().get(SessionManager.EMAIL));
        tv_noHp.setText(sessionManager.getLoginDetail().get(SessionManager.NOMOR_HP));

        Picasso.with(getActivity())
                .load(ServerConfig.KONSUMEN_IMAGE+sessionManager.getLoginDetail().get(SessionManager.FOTO))
                .into(iv_user_images);


        //set on click
        ll_pengaturan_akun.setOnClickListener(this);
        ll_pengaturan_password.setOnClickListener(this);
        keluar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() ==R.id.ll_pengaturan_akun){
            Intent intent = new Intent(getActivity(), UbahAkunActivity.class);
            startActivity(intent);
        }
        else if (view.getId() ==R.id.ll_pengaturan_password){
            Intent intent  = new Intent (getActivity(), UbahPasswordActivity.class);
            startActivity(intent);
        }
        else if (view.getId() ==R.id.keluar){
            sessionManager.logout();
            Intent intent_logout = new Intent(getActivity(), MainActivity.class);
            intent_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY );
            getActivity().finish();
            startActivity(intent_logout);
            Toast.makeText(getActivity(), "Anda Berhasil Keluar", Toast.LENGTH_SHORT).show();

        }

    }
}
