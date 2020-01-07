package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.response.KonsumenLoginResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    TextView tvRegister;
    Button btnLogin;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn() == true) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            final String email = etEmail.getText().toString();
            final String password = etPassword.getText().toString();
            boolean isEmpty = false;

            if (email.equalsIgnoreCase("")) {
                isEmpty = true;
                Toast.makeText(getApplicationContext(), "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }

            if (password.equalsIgnoreCase("")) {
                isEmpty = true;
                Toast.makeText(getApplicationContext(), "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }

            if (isEmpty == false) {

                apiInterface.loginKonsumen(email, password).enqueue(new Callback<KonsumenLoginResponse>() {
                    @Override
                    public void onResponse(Call<KonsumenLoginResponse> call, Response<KonsumenLoginResponse> response) {
                        System.out.println("responsenya" + response);

                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 1) {
                                final Integer strId = response.body().getData().getIdKonsumen();
                                String strUsername = response.body().getData().getUsername();
                                String strNamaLengkap = response.body().getData().getNamaLengkap();
                                String strEmail = response.body().getData().getEmail();
                                String strPassword = response.body().getData().getPassword();
                                String strNomorHp = String.valueOf(response.body().getData().getNomorHp());
                                String strAlamat = response.body().getData().getAlamat();
                                String strRestoreId = response.body().getData().getRestoreId();
                                String strFoto = response.body().getData().getFoto();

                                sessionManager.createLogginSession(String.valueOf(strId),
                                        strUsername,
                                        strNamaLengkap,
                                        strEmail,
                                        strPassword,
                                        strNomorHp,
                                        strAlamat,
                                        strRestoreId,
                                        strFoto);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();


                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KonsumenLoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        }

    }

    //fungsi saat tombol back di handphone ditekan
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMainActivity();
    }

    //fungsi untuk balik ke main activity
    private void goToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(intent);

        finish();
    }
}