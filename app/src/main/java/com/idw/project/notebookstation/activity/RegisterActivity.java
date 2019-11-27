package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView iv_foto_user;
    Button btn_pilih_foto_user, btn_register;
    EditText et_username, et_password, et_namalengkap, et_email, et_nomorhp, et_alamat;

    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        iv_foto_user = findViewById(R.id.iv_foto_user);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        et_namalengkap = findViewById(R.id.et_namalengkap);
        et_nomorhp = findViewById(R.id.et_nomorhp);
        et_alamat = findViewById(R.id.et_alamat);
        btn_register = findViewById(R.id.btn_register);
        btn_pilih_foto_user = findViewById(R.id.btn_pilih_foto_user);

        btn_pilih_foto_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        final String username = et_username.getText(). toString();
        final String password = et_password.getText(). toString();
        final String email = et_email.getText(). toString();
        final String namalengkap = et_namalengkap.getText(). toString();
        final String nomorhp = et_nomorhp.getText(). toString();
        final String alamat = et_alamat.getText(). toString();

        boolean isEmpty = false;

        if(username.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

        if(password.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Password masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(password.length() < 6 ){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
        }

        if(email.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Email masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(namalengkap.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nama lengkap masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(nomorhp.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nomor HP masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(alamat.equalsIgnoreCase ("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Alamat masih kosong", Toast.LENGTH_SHORT).show();
        }

        if(isEmpty == false){
//            apiInterface.registerKonsumen(username,
//                    password,
//                    email,
//                    namalengkap,
//                    nomorhp,
//                    alamat).enqueue(new Callback<KonsumenRegisterResponse>() {
//                @Override
//                public void onResponse(Call<KonsumenRegisterResponse> call, Response<KonsumenRegisterResponse> response) {
//                    System.out.println("responya"+response);
//
//                    if (response.isSuccessful()){
//                        if (response.body().getCode() == 1){
//                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<KonsumenRegisterResponse> call, Throwable t) {
//                    t.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Gagal Koneksi server", Toast.LENGTH_SHORT).show();
//
//                }
//            });

        }


    }
}
