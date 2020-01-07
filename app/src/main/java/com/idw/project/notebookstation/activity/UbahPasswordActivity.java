package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.response.KonsumenUbahPasswordResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {
    EditText edt_kata_sandi_lama, edt_kata_sandi_baru, edt_kata_sandi_ulang;
    Button btn_ubah_kata_sandi;
    String kata_sandi_lama, kata_sandi_baru, kata_sandi_ulang;
    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        this.setTitle("Ubah Password");

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        edt_kata_sandi_lama = findViewById(R.id.edt_kata_sandi_lama);
        edt_kata_sandi_baru = findViewById(R.id.edt_kata_sandi_baru);
        edt_kata_sandi_ulang = findViewById(R.id.edt_kata_sandi_ulang);
        btn_ubah_kata_sandi = findViewById(R.id.btn_ubah_kata_sandi);

        btn_ubah_kata_sandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubah_password();
            }
        });
    }

    private void ubah_password() {
        intialize();
        if (!validate()) {
            Toast.makeText(getApplicationContext(), "Gagal Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface.ubahPassword(sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN), kata_sandi_baru).enqueue(new Callback<KonsumenUbahPasswordResponse>() {
                @Override
                public void onResponse(Call<KonsumenUbahPasswordResponse> call, Response<KonsumenUbahPasswordResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Berhasil Ubah Kata Sandi", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UbahPasswordActivity.this, MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        sessionManager.logout();
                        finish();
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                .LENGTH_SHORT).show();

                        Toast.makeText(getApplicationContext(), "Silahkan login kembali", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UbahPasswordActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<KonsumenUbahPasswordResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    private boolean validate() {
        boolean valid = true;
        if (kata_sandi_lama.isEmpty()) {
            edt_kata_sandi_lama.setError("Tolong Isi Kata Sandi Lama");
            valid = false;
        } else if (!kata_sandi_lama.equals(sessionManager.getLoginDetail().get(SessionManager.PASSWORD))) {
            edt_kata_sandi_lama.setError("Kata Sandi Lama Salah");
            valid = false;
        }

        if (kata_sandi_baru.isEmpty() | kata_sandi_baru.length() > 30) {
            edt_kata_sandi_baru.setError("Tolong Isi Kata Sandi Baru");
            valid = false;
        }

        if (kata_sandi_ulang.isEmpty()) {
            edt_kata_sandi_ulang.setError("Tolong Isi Kata Sandi Ulang");
            valid = false;
        } else if (!kata_sandi_ulang.equals(kata_sandi_baru)) {
            edt_kata_sandi_ulang.setError("Kata Sandi Ulang Tidak Sama");
            valid = false;
        }
        return valid;
    }

    private void intialize() {
        kata_sandi_lama = edt_kata_sandi_lama.getText().toString().trim();
        kata_sandi_baru = edt_kata_sandi_baru.getText().toString().trim();
        kata_sandi_ulang = edt_kata_sandi_ulang.getText().toString().trim();
    }
}
