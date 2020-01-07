package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.adapter.KeranjangAdapter;
import com.idw.project.notebookstation.model.Keranjang;
import com.idw.project.notebookstation.response.KeranjangDetailResponse;
import com.idw.project.notebookstation.response.KeranjangSumResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangActivity extends AppCompatActivity {
    private RecyclerView recylerView;
    private KeranjangAdapter keranjangAdapter;
    private ArrayList<Keranjang> keranjangArrayList = new ArrayList<>();
    private LinearLayout ll_label, ll_data_keranjang_kosong;
    private Button btn_belanja_sekarang, btn_beli;

    private ApiInterface apiInterface;
    private SessionManager sessionManager;

    private String id_konsumen;

    TextView tv_harga1;

    String keranjang_sum, id_keranjang;

    DecimalFormat df;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        setTitle("Keranjang");

        df = new DecimalFormat("#,###");

        recylerView = findViewById(R.id.recylerView1);
        ll_label = findViewById(R.id.ll_label);
        ll_data_keranjang_kosong = findViewById(R.id.ll_data_keranjang_kosong);
        btn_belanja_sekarang = findViewById(R.id.btn_belanja_sekarang);
        btn_beli = findViewById(R.id.btn_beli);
        tv_harga1 = findViewById(R.id.tv_harga1);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {

            id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

            btn_belanja_sekarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            getKeranjang();

            initView();
        }


    }

    private void initView() {

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);

        refresh();
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        keranjangArrayList.clear();
                        getKeranjang();
                    }
                }, 500);
            }
        });
    }

    private void getKeranjang() {
        apiInterface.keranjangDetail(id_konsumen).enqueue(new Callback<KeranjangDetailResponse>() {
            @Override
            public void onResponse(Call<KeranjangDetailResponse> call, final Response<KeranjangDetailResponse> response) {
                System.out.println("responnya" + response);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMaster().size() > 0) {
                            ll_data_keranjang_kosong.setVisibility(View.GONE);
                            ll_label.setVisibility(View.VISIBLE);
                            keranjangArrayList.addAll(response.body().getMaster());

                            id_keranjang = response.body().getMaster().get(0).getIdKeranjang();

                            apiInterface.keranjangSum(id_konsumen).enqueue(new Callback<KeranjangSumResponse>() {
                                @Override
                                public void onResponse(Call<KeranjangSumResponse> call, Response<KeranjangSumResponse> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getMaster().size() > 0) {
                                            System.out.println("datanya" + response.body().getMaster().get(0).getSUMJumlahHarga());
                                            keranjang_sum = response.body().getMaster().get(0).getSUMJumlahHarga();

                                            tv_harga1.setText("Rp. " + df.format(Double.valueOf(keranjang_sum)));


                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<KeranjangSumResponse> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                }
                            });

                            btn_beli.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), DetailPengirimanActivity.class);
                                    intent.putExtra(Keranjang.TAG, id_keranjang);
                                    System.out.println("nilai dari" + id_keranjang);
                                    startActivity(intent);

                                }
                            });

                            LinearLayoutManager manager = new LinearLayoutManager(KeranjangActivity.this);
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            keranjangAdapter = new KeranjangAdapter(KeranjangActivity.this, keranjangArrayList);
                            recylerView.setAdapter(keranjangAdapter);

                        } else {
                            ll_label.setVisibility(View.GONE);
                            ll_data_keranjang_kosong.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KeranjangDetailResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(KeranjangActivity.this, MainActivity.class));

        super.onBackPressed();
    }
}
