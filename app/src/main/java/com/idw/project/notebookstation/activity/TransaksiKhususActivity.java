package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailPembayaranKhususTransaksiActivity;
import com.idw.project.notebookstation.response.PesananKhususDetailResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiKhususActivity extends AppCompatActivity {
    EditText edt_kode_pesanan;
    Button btn_cek_status;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_khusus);

        btn_cek_status = findViewById(R.id.btn_cek_status);
        edt_kode_pesanan = findViewById(R.id.edt_kode_pesanan);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btn_cek_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.pesananKhususDetail(edt_kode_pesanan.getText().toString()).enqueue(new Callback<PesananKhususDetailResponse>() {
                    @Override
                    public void onResponse(Call<PesananKhususDetailResponse> call, Response<PesananKhususDetailResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                if (response.body().getMaster().size()>0){
                                    Toast.makeText(getApplicationContext(), "Data ditemukan", Toast.LENGTH_SHORT).show();

                                    String status = response.body().getMaster().get(0).getStatus();

                                    System.out.println("cekkkk"+status);

                                    if (status.equalsIgnoreCase("Menunggu Pembayaran")){
                                        Intent intent = new Intent(getApplicationContext(), DetailPembayaranKhususTransaksiActivity.class);
                                        intent.putExtra("kode_pesanan", edt_kode_pesanan.getText().toString());
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Udah bayar", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), DetailTransaksiKhususActivity.class);
                                        intent.putExtra("kode_pesanan", edt_kode_pesanan.getText().toString());
                                        startActivity(intent);
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Data anda tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PesananKhususDetailResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
