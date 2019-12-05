package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.response.ProdukDetailResponse;
import com.idw.project.notebookstation.response.TambahKeranjangResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import java.text.DecimalFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKonfirmasiProdukActivity extends AppCompatActivity {
    ImageView iv_foto, iv_tambah_keranjang;
    TextView tv_nama_produk, tv_merk, tv_harga, tv_qty, tv_harga1;
    Button btn_beli;
    EditText edt_catatan;
    FloatingActionButton fab_qty_min, fab_qty_add;

    ApiInterface apiInterface;
    SessionManager sessionManager;

    String id_konsumen;

    String nama_produk, merk_produk, harga_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konfirmasi_produk);
        this.setTitle("Detail Pembelian");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        final DecimalFormat df = new DecimalFormat( "#,###");

        iv_foto = findViewById(R.id.iv_foto);
        iv_tambah_keranjang = findViewById(R.id.iv_tambah_keranjang);
        tv_nama_produk = findViewById(R.id.tv_nama_produk);
        tv_merk = findViewById(R.id.tv_merk);
        tv_harga = findViewById(R.id.tv_harga);
        tv_qty = findViewById(R.id.tv_qty);
        tv_harga1 = findViewById(R.id.tv_harga1);
        btn_beli = findViewById(R.id.btn_beli);
        edt_catatan = findViewById(R.id.edt_catatan);
        fab_qty_min = findViewById(R.id.fab_qty_min);
        fab_qty_add = findViewById(R.id.fab_qty_add);

//        produk =  Objects.requireNonNull(getIntent().getExtras()).getParcelable(TAG);
        final int EXTRA_ID_PRODUK = getIntent().getIntExtra(Produk.TAG, 0);

        System.out.println("nilai"+EXTRA_ID_PRODUK);

        if (sessionManager.isLoggedIn()){
            id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

            apiInterface.produkById(String.valueOf(EXTRA_ID_PRODUK)).enqueue(new Callback<ProdukDetailResponse>() {
                @Override
                public void onResponse(Call<ProdukDetailResponse> call, Response<ProdukDetailResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getMaster().size()>0){
                            nama_produk = response.body().getMaster().get(0).getNamaProduk();
                            merk_produk = response.body().getMaster().get(0).getMerkProduk();
                            harga_produk = response.body().getMaster().get(0).getHarga();

                            tv_nama_produk.setText(nama_produk);
                            tv_merk.setText(merk_produk);
                            tv_harga.setText("Rp. "+df.format(Double.valueOf(harga_produk)));
                            tv_harga1.setText("Rp. "+df.format(Double.valueOf(harga_produk)));
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProdukDetailResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }

//        if (produk != null) {
//            System.out.println("cek id saja "+produk.getIdProduk());
//            id_produk = String.valueOf(produk.getIdProduk());
//
//            tv_nama_produk.setText(produk.getNamaProduk());
//            tv_merk.setText(produk.getMerkProduk());
//            tv_harga.setText("Rp. "+df.format(Double.valueOf(produk.getHarga())));
//            tv_harga1.setText("Rp. "+df.format(Double.valueOf(produk.getHarga())));
//        }

        //secara default, kita hide button mines
        fab_qty_min.animate().alpha(0).setDuration(300).start();
        fab_qty_min.setEnabled(false);

        fab_qty_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(tv_qty.getText().toString());
                if (qty > 1) {
                    qty--;
                    tv_qty.setText(qty + "");
                }

                if (qty <10){
                    fab_qty_add.animate().alpha(1).setDuration(300).start();
                    fab_qty_add.setEnabled(true);
                }

                if (qty <2){
                    fab_qty_min.animate().alpha(0).setDuration(300).start();
                    fab_qty_min.setEnabled(false);
                }

                int total = Integer.parseInt(harga_produk) *qty;
                tv_harga1.setText("Rp. "+df.format(Double.valueOf(total)));

            }
        });

        fab_qty_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(tv_qty.getText().toString());
                if (qty < 10) {
                    qty++;
                    tv_qty.setText(qty + "");
                }

                if (qty >9){
                    fab_qty_add.animate().alpha(0).setDuration(300).start();
                    fab_qty_add.setEnabled(false);
                }

                if (qty >1){
                    fab_qty_min.animate().alpha(1).setDuration(300).start();
                    fab_qty_min.setEnabled(true);
                }

                int total = Integer.parseInt(harga_produk) *qty;
                tv_harga1.setText("Rp. "+df.format(Double.valueOf(total)));

            }
        });

        iv_tambah_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.tambahKeranjang(id_konsumen, String.valueOf(EXTRA_ID_PRODUK)).enqueue(new Callback<TambahKeranjangResponse>() {
                    @Override
                    public void onResponse(Call<TambahKeranjangResponse> call, Response<TambahKeranjangResponse> response) {
                        if (response.isSuccessful()){

                            Snackbar.make(view, "Ditambahkan ke keranjang", Snackbar.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TambahKeranjangResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Beli", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
