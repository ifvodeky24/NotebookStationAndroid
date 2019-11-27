package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.util.SessionManager;
import com.squareup.picasso.Picasso;

public class UbahAkunActivity extends AppCompatActivity {
    EditText edt_username, edt_nama_lengkap, edt_alamat, edt_email, edt_nomor_handphone;
    Button btn_ubah_akun;
    ImageView iv_foto_profil;

    SessionManager sessionManager;

    String username, nama_lengkap, alamat, email, nomor_handphone, foto_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_akun);

        sessionManager = new SessionManager(this);

        edt_username = findViewById(R.id.edt_username);
        edt_nama_lengkap = findViewById(R.id.edt_nama_lengkap);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_email = findViewById(R.id.edt_email);
        edt_nomor_handphone = findViewById(R.id.edt_nomor_handphone);
        iv_foto_profil = findViewById(R.id.iv_foto_profil);
        btn_ubah_akun = findViewById(R.id.btn_ubah_akun);

        if (sessionManager.isLoggedIn()){
            username = sessionManager.getLoginDetail().get(SessionManager.USERNAME);
            nama_lengkap = sessionManager.getLoginDetail().get(SessionManager.NAMA_LENGKAP);
            alamat = sessionManager.getLoginDetail().get(SessionManager.ALAMAT);
            email = sessionManager.getLoginDetail().get(SessionManager.EMAIL);
            nomor_handphone = sessionManager.getLoginDetail().get(SessionManager.NOMOR_HP);
            foto_profil = ServerConfig.KONSUMEN_IMAGE+sessionManager.getLoginDetail().get(SessionManager.FOTO);

            Picasso
                    .with(UbahAkunActivity.this)
                    .load(foto_profil)
                    .into(iv_foto_profil);


            edt_username.setText(username);
            edt_nama_lengkap.setText(nama_lengkap);
            edt_alamat.setText(alamat);
            edt_email.setText(email);
            edt_nomor_handphone.setText(nomor_handphone);
        }
    }
}
