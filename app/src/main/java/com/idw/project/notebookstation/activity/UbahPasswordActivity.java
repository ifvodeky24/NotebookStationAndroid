package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.util.SessionManager;

public class UbahPasswordActivity extends AppCompatActivity {
    EditText edt_kata_sandi_lama, edt_kata_sandi_baru, edt_kata_sandi_ulang;
    Button btn_ubah_kata_sandi;
    String kata_sandi_lama, kata_sandi_baru, kata_sandi_ulang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        edt_kata_sandi_lama = findViewById(R.id.edt_kata_sandi_lama);
        edt_kata_sandi_baru = findViewById(R.id.edt_kata_sandi_baru);
        edt_kata_sandi_ulang = findViewById(R.id.edt_kata_sandi_ulang);
        btn_ubah_kata_sandi = findViewById(R.id.btn_ubah_kata_sandi);

        btn_ubah_kata_sandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Klik Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
                ubah_password();
            }
        });
    }

    private void ubah_password() {
        intialize();
        if (!validate()){
            Toast.makeText(getApplicationContext(), "Gagal Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Berhasil Ubah Kata Sandi", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (kata_sandi_lama.isEmpty()|kata_sandi_lama.length()>30){
            edt_kata_sandi_lama.setError("Tolong Isi Kata Sandi Lama");
            valid = false;
        }
        if (kata_sandi_baru.isEmpty()|kata_sandi_baru.length()>30){
            edt_kata_sandi_baru.setError("Tolong Isi Kata Sandi Baru");
            valid = false;
        }
        if (kata_sandi_lama.isEmpty()|kata_sandi_lama.length()>30){
            edt_kata_sandi_ulang.setError("Tolong Isi Kata Sandi Ulang");
            valid = false;
        }
        return valid;
    }

    private void intialize() {
        kata_sandi_lama = edt_kata_sandi_lama.getText().toString().trim();
        kata_sandi_baru = edt_kata_sandi_lama.getText().toString().trim();
        kata_sandi_ulang = edt_kata_sandi_lama.getText().toString().trim();
    }
}
