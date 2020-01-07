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
import com.idw.project.notebookstation.response.HapusKeranjangResponse;
import com.idw.project.notebookstation.response.KeranjangCheckResponse;
import com.idw.project.notebookstation.response.KeranjangCountResponse;
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
    ImageView iv_foto, iv_tambah_keranjang, iv_hapus_keranjang;
    TextView tv_nama_produk, tv_merk, tv_harga, tv_qty, tv_harga1;
    Button btn_beli;
    EditText edt_catatan;
    FloatingActionButton fab_qty_min, fab_qty_add;

    ApiInterface apiInterface;
    SessionManager sessionManager;

    String id_konsumen, id_keranjang;

    String nama_produk, merk_produk, harga_produk;

    boolean keranjang;

    int keranjang_count, qty, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konfirmasi_produk);
        this.setTitle("Detail Pembelian");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        final DecimalFormat df = new DecimalFormat("#,###");

        iv_foto = findViewById(R.id.iv_foto);
        iv_tambah_keranjang = findViewById(R.id.iv_tambah_keranjang);
        iv_hapus_keranjang = findViewById(R.id.iv_hapus_keranjang);
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

        System.out.println("nilai" + EXTRA_ID_PRODUK);

        if (sessionManager.isLoggedIn()) {
            id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

            apiInterface.produkById(String.valueOf(EXTRA_ID_PRODUK)).enqueue(new Callback<ProdukDetailResponse>() {
                @Override
                public void onResponse(Call<ProdukDetailResponse> call, Response<ProdukDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMaster().size() > 0) {
                            nama_produk = response.body().getMaster().get(0).getNamaProduk();
                            merk_produk = response.body().getMaster().get(0).getMerkProduk();
                            harga_produk = response.body().getMaster().get(0).getHarga();

                            tv_nama_produk.setText(nama_produk);
                            tv_merk.setText(merk_produk);
                            tv_harga.setText("Rp. " + df.format(Double.valueOf(harga_produk)));
                            tv_harga1.setText("Rp. " + df.format(Double.valueOf(harga_produk)));
                            total = Integer.parseInt(harga_produk);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProdukDetailResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });

            apiInterface.checkIdKeranjang(String.valueOf(EXTRA_ID_PRODUK), id_konsumen).enqueue(new Callback<KeranjangCheckResponse>() {
                @Override
                public void onResponse(Call<KeranjangCheckResponse> call, Response<KeranjangCheckResponse> response) {
                    System.out.println("responsenya" + response);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getMaster().size() > 0) {
                                String id_produknya = response.body().getMaster().get(0).getIdProduk();
                                String id_konsumennya = response.body().getMaster().get(0).getIdKonsumen();
                                id_keranjang = response.body().getMaster().get(0).getIdKeranjang();
                                System.out.println("id produknya " + id_produknya + "id konsumennya " + id_konsumennya);

                                if (String.valueOf(EXTRA_ID_PRODUK).equals(id_produknya) && id_konsumen.equals(id_konsumennya)) {
                                    keranjang = true;
                                    setKeranjang();
                                } else {
                                    keranjang = false;
                                    setKeranjang();
                                }
                            } else {
//                                Toast.makeText(getApplicationContext(), "id keranjang kosong", Toast.LENGTH_LONG).show();
                                keranjang = false;
                                setKeranjang();
                            }

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<KeranjangCheckResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }

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

                if (qty < 10) {
                    fab_qty_add.animate().alpha(1).setDuration(300).start();
                    fab_qty_add.setEnabled(true);
                }

                if (qty < 2) {
                    fab_qty_min.animate().alpha(0).setDuration(300).start();
                    fab_qty_min.setEnabled(false);
                }

                int total = Integer.parseInt(harga_produk) * qty;
                tv_harga1.setText("Rp. " + df.format(Double.valueOf(total)));

            }
        });

        fab_qty_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = Integer.parseInt(tv_qty.getText().toString());
                if (qty < 10) {
                    qty++;
                    tv_qty.setText(qty + "");
                }

                if (qty > 9) {
                    fab_qty_add.animate().alpha(0).setDuration(300).start();
                    fab_qty_add.setEnabled(false);
                }

                if (qty > 1) {
                    fab_qty_min.animate().alpha(1).setDuration(300).start();
                    fab_qty_min.setEnabled(true);
                }

                total = Integer.parseInt(harga_produk) * qty;
                tv_harga1.setText("Rp. " + df.format(Double.valueOf(total)));

            }
        });


        btn_beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.tambahKeranjang(id_konsumen, String.valueOf(EXTRA_ID_PRODUK), tv_qty.getText().toString(), String.valueOf(total), edt_catatan.getText().toString()).enqueue(new Callback<TambahKeranjangResponse>() {
                    @Override
                    public void onResponse(Call<TambahKeranjangResponse> call, Response<TambahKeranjangResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), KeranjangActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TambahKeranjangResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        iv_tambah_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.tambahKeranjang(id_konsumen, String.valueOf(EXTRA_ID_PRODUK), "1", harga_produk, "").enqueue(new Callback<TambahKeranjangResponse>() {
                    @Override
                    public void onResponse(Call<TambahKeranjangResponse> call, Response<TambahKeranjangResponse> response) {
                        if (response.isSuccessful()) {
                            keranjang = true;
                            setKeranjang();
                            getCount();

                            Snackbar.make(view, "Ditambahkan ke keranjang", Snackbar.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TambahKeranjangResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        iv_hapus_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.checkIdKeranjang(String.valueOf(EXTRA_ID_PRODUK), id_konsumen).enqueue(new Callback<KeranjangCheckResponse>() {
                    @Override
                    public void onResponse(Call<KeranjangCheckResponse> call, Response<KeranjangCheckResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMaster().size() > 0) {
                                    id_keranjang = response.body().getMaster().get(0).getIdKeranjang();
                                    System.out.println("id keranjangnya adalah" + id_keranjang);

                                    apiInterface.hapusKeranjang(id_keranjang).enqueue(new Callback<HapusKeranjangResponse>() {
                                        @Override
                                        public void onResponse(Call<HapusKeranjangResponse> call, Response<HapusKeranjangResponse> response) {
                                            if (response.isSuccessful()) {
                                                keranjang = false;
                                                setKeranjang();
                                                getCount();

                                                Snackbar.make(view, "Dihapus dari Keranjang", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<HapusKeranjangResponse> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
//                                    Toast.makeText(getApplicationContext(), "id kosong", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KeranjangCheckResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void getCount() {
        apiInterface.keranjangCount(id_konsumen).enqueue(new Callback<KeranjangCountResponse>() {
            @Override
            public void onResponse(Call<KeranjangCountResponse> call, Response<KeranjangCountResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMaster().size() > 0) {
                        System.out.println("datanya" + response.body().getMaster().get(0).getNumber());
                        keranjang_count = Integer.parseInt(response.body().getMaster().get(0).getNumber());

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KeranjangCountResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setKeranjang() {
        if (keranjang) {
            iv_tambah_keranjang.setVisibility(View.GONE);
            iv_hapus_keranjang.setVisibility(View.VISIBLE);
        } else {
            iv_hapus_keranjang.setVisibility(View.GONE);
            iv_tambah_keranjang.setVisibility(View.VISIBLE);
        }
    }

}
