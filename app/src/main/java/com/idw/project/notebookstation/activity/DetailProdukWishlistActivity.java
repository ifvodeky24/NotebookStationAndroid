package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.model.Wishlist;
import com.idw.project.notebookstation.response.HapusKeranjangResponse;
import com.idw.project.notebookstation.response.HapusWishlistResponse;
import com.idw.project.notebookstation.response.KeranjangCheckResponse;
import com.idw.project.notebookstation.response.KeranjangCountResponse;
import com.idw.project.notebookstation.response.ProdukDetailResponse;
import com.idw.project.notebookstation.response.TambahKeranjangResponse;
import com.idw.project.notebookstation.response.TambahWishlistResponse;
import com.idw.project.notebookstation.response.WishlistCheckResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.idw.project.notebookstation.util.ViewAnimation;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukWishlistActivity extends AppCompatActivity {
    ImageView iv_foto_produk, iv_foto_produk2, iv_foto_produk3, iv_foto_produk4, iv_logo_toko;
    TextView tv_stok_produk, tv_merk_produk, tv_kondisi, tv_nama_produk, tv_harga_produk, tv_deskripsi_produk, tv_nama_toko, tv_no_handphone;
    TextView tv_notification_badge;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    public static final String TAG = "produk";
    String id;
    int keranjang_count;

    private View ll_tambah_wishlist, ll_hapus_wishlist, ll_beli, ll_tambah_keranjang, ll_hapus_keranjang;
    private boolean rotate = false;

    FloatingActionButton fab_add, fab_tambah_wishlist, fab_beli, fab_hapus_wishlist, fab_tambah_keranjang, fab_hapus_keranjang;

    boolean favorite, keranjang;

    String id_konsumen, id_wishlist, nama_toko, nomor_hp, id_keranjang, nama_produk, merk_produk, kondisi, harga_produk, deskripsi_produk, stok_produk;
    String foto1, foto2, foto3, foto4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Detail Produk");
        setContentView(R.layout.activity_detail_produk_wishlist);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        id = getIntent().getStringExtra(Wishlist.TAG);

        System.out.println("id produk" + id);

        DecimalFormat df = new DecimalFormat("#,###");

        iv_foto_produk = findViewById(R.id.iv_foto_produk);
        iv_foto_produk2 = findViewById(R.id.iv_foto_produk2);
        iv_foto_produk3 = findViewById(R.id.iv_foto_produk3);
        iv_foto_produk4 = findViewById(R.id.iv_foto_produk4);
        iv_logo_toko = findViewById(R.id.iv_logo_toko);
        tv_stok_produk = findViewById(R.id.tv_stok_produk);
        tv_merk_produk = findViewById(R.id.tv_merk_produk);
        tv_kondisi = findViewById(R.id.tv_kondisi);
        tv_nama_produk = findViewById(R.id.tv_nama_produk);
        tv_harga_produk = findViewById(R.id.tv_harga_produk);
        tv_deskripsi_produk = findViewById(R.id.tv_deskripsi_produk);
        tv_nama_toko = findViewById(R.id.tv_nama_toko);
        tv_no_handphone = findViewById(R.id.tv_no_handphone);
        fab_beli = findViewById(R.id.fab_beli);
        fab_tambah_keranjang = findViewById(R.id.fab_tambah_keranjang);
        fab_hapus_keranjang = findViewById(R.id.fab_hapus_keranjang);
        fab_tambah_wishlist = findViewById(R.id.fab_tambah_wishlist);
        fab_hapus_wishlist = findViewById(R.id.fab_hapus_wishlist);
        fab_add = findViewById(R.id.fab_add);
        ll_beli = findViewById(R.id.ll_beli);
        ll_tambah_wishlist = findViewById(R.id.ll_tambah_wishlist);
        ll_hapus_wishlist = findViewById(R.id.ll_hapus_wishlist);
        ll_tambah_keranjang = findViewById(R.id.ll_tambah_keranjang);
        ll_hapus_keranjang = findViewById(R.id.ll_hapus_keranjang);

        ViewAnimation.initShowOut(ll_tambah_wishlist);
        ViewAnimation.initShowOut(ll_hapus_wishlist);
        ViewAnimation.initShowOut(ll_beli);
        ViewAnimation.initShowOut(ll_tambah_keranjang);
        ViewAnimation.initShowOut(ll_hapus_keranjang);

        if (sessionManager.isLoggedIn()) {
            id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

            System.out.println("cek id" + id_konsumen);

            getCount();

            apiInterface.produkById(String.valueOf(id)).enqueue(new Callback<ProdukDetailResponse>() {
                @Override
                public void onResponse(Call<ProdukDetailResponse> call, Response<ProdukDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMaster().size() > 0) {
                            nama_produk = response.body().getMaster().get(0).getNamaProduk();
                            merk_produk = response.body().getMaster().get(0).getMerkProduk();
                            kondisi = response.body().getMaster().get(0).getKondisi();
                            harga_produk = response.body().getMaster().get(0).getHarga();
                            deskripsi_produk = response.body().getMaster().get(0).getDeskripsi();
                            stok_produk = response.body().getMaster().get(0).getStok();
                            nama_toko = response.body().getMaster().get(0).getNamaToko();
                            nomor_hp = response.body().getMaster().get(0).getNomorHp();
                            foto1 = response.body().getMaster().get(0).getFoto1();
                            foto2 = response.body().getMaster().get(0).getFoto2();
                            foto3 = response.body().getMaster().get(0).getFoto3();
                            foto4 = response.body().getMaster().get(0).getFoto4();

                            tv_nama_produk.setText(nama_produk);
                            tv_merk_produk.setText("Merk Produk:  " + merk_produk);
                            tv_kondisi.setText("Kondisi:  " + kondisi);
                            tv_harga_produk.setText("Rp. " + harga_produk);
                            tv_deskripsi_produk.setText(deskripsi_produk);
                            tv_stok_produk.setText("Stok Tersedia " + stok_produk);
                            tv_nama_toko.setText(nama_toko);
                            tv_no_handphone.setText(nomor_hp);

                            Picasso.with(DetailProdukWishlistActivity.this)
                                    .load(ServerConfig.PRODUK_IMAGE + foto1)
                                    .into(iv_foto_produk);

                            Picasso.with(DetailProdukWishlistActivity.this)
                                    .load(ServerConfig.PRODUK_IMAGE + foto2)
                                    .into(iv_foto_produk2);

                            Picasso.with(DetailProdukWishlistActivity.this)
                                    .load(ServerConfig.PRODUK_IMAGE + foto3)
                                    .into(iv_foto_produk3);

                            Picasso.with(DetailProdukWishlistActivity.this)
                                    .load(ServerConfig.PRODUK_IMAGE + foto4)
                                    .into(iv_foto_produk4);

                            Picasso.with(DetailProdukWishlistActivity.this)
                                    .load(ServerConfig.TOKO_IMAGE + response.body().getMaster().get(0).getLogoToko())
                                    .into(iv_logo_toko);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProdukDetailResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });

            fab_add.setVisibility(View.VISIBLE);

            fab_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleFabMode(view);
                }
            });

            fab_beli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailProdukWishlistActivity.this, DetailKonfirmasiProdukActivity.class);
                    intent.putExtra(Produk.TAG, Integer.parseInt(id));
                    System.out.println("nilai dari" + id);
                    startActivity(intent);
                }
            });
        } else {
            fab_add.setVisibility(View.GONE);

            dialog_login();
        }

        fab_tambah_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                apiInterface.tambahWishlist(id_konsumen, String.valueOf(id)).enqueue(new Callback<TambahWishlistResponse>() {
                    @Override
                    public void onResponse(Call<TambahWishlistResponse> call, Response<TambahWishlistResponse> response) {
                        if (response.isSuccessful()) {
                            favorite = true;
                            setFavorite();

                            Snackbar.make(view, "Ditambahkan ke Wishlist", Snackbar.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TambahWishlistResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        fab_hapus_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.checkId(String.valueOf(id), id_konsumen).enqueue(new Callback<WishlistCheckResponse>() {
                    @Override
                    public void onResponse(Call<WishlistCheckResponse> call, Response<WishlistCheckResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMaster().size() > 0) {
                                    id_wishlist = response.body().getMaster().get(0).getIdWishlist();
                                    System.out.println("id wishlistnya adalah" + id_wishlist);

                                    apiInterface.hapusWishlist(id_wishlist).enqueue(new Callback<HapusWishlistResponse>() {
                                        @Override
                                        public void onResponse(Call<HapusWishlistResponse> call, Response<HapusWishlistResponse> response) {
                                            if (response.isSuccessful()) {
                                                favorite = false;
                                                setFavorite();

                                                Snackbar.make(view, "Dihapus dari Wishlist", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                            }
                                        }


                                        @Override
                                        public void onFailure(Call<HapusWishlistResponse> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
//                                    Toast.makeText(getApplicationContext(), "id kosong", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WishlistCheckResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        fab_tambah_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.tambahKeranjang(id_konsumen, String.valueOf(id), "1", harga_produk, "").enqueue(new Callback<TambahKeranjangResponse>() {
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

        fab_hapus_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                apiInterface.checkIdKeranjang(String.valueOf(id), id_konsumen).enqueue(new Callback<KeranjangCheckResponse>() {
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

        iv_foto_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailProdukWishlistActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Galeri Foto");
                alertDialogBuilder.setIcon(R.drawable.logo);

                LayoutInflater inflater = getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.foto_produk_dialog, null);

                alertDialogBuilder.setView(dialogView);

                ImageView iv_tampilkan_foto_produk = dialogView.findViewById(R.id.iv_foto_produk);


                Picasso.with(DetailProdukWishlistActivity.this)
                        .load(ServerConfig.PRODUK_IMAGE + foto1)
                        .into(iv_tampilkan_foto_produk);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        iv_foto_produk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailProdukWishlistActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Galeri Foto");
                alertDialogBuilder.setIcon(R.drawable.logo);

                LayoutInflater inflater = getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.foto_produk_dialog, null);

                alertDialogBuilder.setView(dialogView);

                ImageView iv_tampilkan_foto_produk = dialogView.findViewById(R.id.iv_foto_produk);


                Picasso.with(DetailProdukWishlistActivity.this)
                        .load(ServerConfig.PRODUK_IMAGE + foto2)
                        .into(iv_tampilkan_foto_produk);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        iv_foto_produk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailProdukWishlistActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Galeri Foto");
                alertDialogBuilder.setIcon(R.drawable.logo);

                LayoutInflater inflater = getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.foto_produk_dialog, null);

                alertDialogBuilder.setView(dialogView);

                ImageView iv_tampilkan_foto_produk = dialogView.findViewById(R.id.iv_foto_produk);


                Picasso.with(DetailProdukWishlistActivity.this)
                        .load(ServerConfig.PRODUK_IMAGE + foto3)
                        .into(iv_tampilkan_foto_produk);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        iv_foto_produk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailProdukWishlistActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Galeri Foto");
                alertDialogBuilder.setIcon(R.drawable.logo);

                LayoutInflater inflater = getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.foto_produk_dialog, null);

                alertDialogBuilder.setView(dialogView);

                ImageView iv_tampilkan_foto_produk = dialogView.findViewById(R.id.iv_foto_produk);


                Picasso.with(DetailProdukWishlistActivity.this)
                        .load(ServerConfig.PRODUK_IMAGE + foto4)
                        .into(iv_tampilkan_foto_produk);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void dialog_login() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailProdukWishlistActivity.this);

        //set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda Harus Login Terlebih Dahulu")
                .setCancelable(false)
                .setPositiveButton("login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetailProdukWishlistActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

        //membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        //menampilkan alert dialog
        alertDialog.show();
    }

    private void getCount() {
        apiInterface.keranjangCount(id_konsumen).enqueue(new Callback<KeranjangCountResponse>() {
            @Override
            public void onResponse(Call<KeranjangCountResponse> call, Response<KeranjangCountResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMaster().size() > 0) {
                        System.out.println("datanya" + response.body().getMaster().get(0).getNumber());
                        keranjang_count = Integer.parseInt(response.body().getMaster().get(0).getNumber());

                        setupBadge();
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

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(ll_tambah_wishlist);
            ViewAnimation.showIn(ll_hapus_wishlist);
            ViewAnimation.showIn(ll_beli);
            ViewAnimation.showIn(ll_tambah_keranjang);
            ViewAnimation.showIn(ll_hapus_keranjang);

            apiInterface.checkId(String.valueOf(id), id_konsumen).enqueue(new Callback<WishlistCheckResponse>() {
                @Override
                public void onResponse(Call<WishlistCheckResponse> call, Response<WishlistCheckResponse> response) {
                    System.out.println("responsenya" + response);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getMaster().size() > 0) {
                                String id_produknya = response.body().getMaster().get(0).getIdProduk();
                                String id_konsumennya = response.body().getMaster().get(0).getIdKonsumen();
                                id_wishlist = response.body().getMaster().get(0).getIdWishlist();
                                System.out.println("id produknya " + id_produknya + "id konsumennya " + id_konsumennya);

                                if (String.valueOf(id).equals(id_produknya) && id_konsumen.equals(id_konsumennya)) {
                                    favorite = true;
                                    setFavorite();
                                } else {
                                    favorite = false;
                                    setFavorite();
                                }

                            } else {
//                                Toast.makeText(getApplicationContext(), "id kosong", Toast.LENGTH_LONG).show();
                                favorite = false;
                                setFavorite();
                            }
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WishlistCheckResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });

            apiInterface.checkIdKeranjang(String.valueOf(id), id_konsumen).enqueue(new Callback<KeranjangCheckResponse>() {
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

                                if (String.valueOf(id).equals(id_produknya) && id_konsumen.equals(id_konsumennya)) {
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
        } else {
            ViewAnimation.showOut(ll_tambah_wishlist);
            ViewAnimation.showOut(ll_hapus_wishlist);
            ViewAnimation.showOut(ll_beli);
            ViewAnimation.showOut(ll_tambah_keranjang);
            ViewAnimation.showOut(ll_hapus_keranjang);
        }
    }

    private void setFavorite() {
        if (favorite) {
            ll_tambah_wishlist.setVisibility(View.GONE);
            ll_hapus_wishlist.setVisibility(View.VISIBLE);

        } else {
            ll_hapus_wishlist.setVisibility(View.GONE);
            ll_tambah_wishlist.setVisibility(View.VISIBLE);
        }
    }

    private void setKeranjang() {
        if (keranjang) {
            ll_tambah_keranjang.setVisibility(View.GONE);
            ll_hapus_keranjang.setVisibility(View.VISIBLE);
        } else {
            ll_hapus_keranjang.setVisibility(View.GONE);
            ll_tambah_keranjang.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_produk, menu);

        final MenuItem menuItem = menu.findItem(R.id.keranjang);

        View actionView = menuItem.getActionView();

        tv_notification_badge = actionView.findViewById(R.id.tv_notification_badge);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {
        if (tv_notification_badge != null) {
            if (keranjang_count == 0) {
                if (tv_notification_badge.getVisibility() != View.GONE) {
                    tv_notification_badge.setVisibility(View.GONE);
                }
            } else {
                tv_notification_badge.setText(String.valueOf(Math.min(keranjang_count, 99)));
                if (tv_notification_badge.getVisibility() != View.VISIBLE) {
                    tv_notification_badge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (sessionManager.isLoggedIn()) {
            menu.setGroupVisible(R.id.menu_txt_group, true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.keranjang:
                Toast.makeText(getApplicationContext(), "Klik Keranjang", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.chatting:
//                Toast.makeText(getApplicationContext(), "Klik Chat", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return true;
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(DetailProdukWishlistActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        goToMainActivity();
    }
}
