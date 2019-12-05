package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.adapter.TransaksiAdapter;
import com.idw.project.notebookstation.model.Pesanan;
import com.idw.project.notebookstation.response.PesananDiprosesResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananDiprosesActivity extends AppCompatActivity {
    private RecyclerView recylerView;
    private TransaksiAdapter transaksiAdapter;
    private ArrayList<Pesanan> pesananArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;
    private LinearLayout ll_data_pesanan_kosong;

    String id_konsumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_diproses);

        recylerView = findViewById(R.id.recylerView1);
        ll_data_pesanan_kosong = findViewById(R.id.ll_data_pesanan_kosong);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        getData();
    }

    private void getData() {
        id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

        apiInterface.pesananDiproses(id_konsumen).enqueue(new Callback<PesananDiprosesResponse>() {
            @Override
            public void onResponse(Call<PesananDiprosesResponse> call, Response<PesananDiprosesResponse> response) {
                System.out.println("responya"+response);
                if (response.isSuccessful()){
                    if(response.body().getMaster().size()>0){
                        ll_data_pesanan_kosong.setVisibility(View.GONE);
                        pesananArrayList.addAll(response.body().getMaster());
                        System.out.println(response.body().getMaster().get(0).getIdPesanan());

                        LinearLayoutManager manager = new LinearLayoutManager(PesananDiprosesActivity.this);
                        recylerView.setLayoutManager(manager);
                        recylerView.setHasFixedSize(true);
                        transaksiAdapter = new TransaksiAdapter(PesananDiprosesActivity.this, pesananArrayList);
                        recylerView.setAdapter(transaksiAdapter);

                    }else {
//                        Toast.makeText(getApplicationContext(), "Data Menunggu Pembayaran Kosong", Toast.LENGTH_SHORT).show();
                        ll_data_pesanan_kosong.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PesananDiprosesResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
