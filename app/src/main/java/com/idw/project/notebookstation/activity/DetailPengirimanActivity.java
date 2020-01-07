package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.adapter.PengirimanAdapter;
import com.idw.project.notebookstation.model.Keranjang;
import com.idw.project.notebookstation.response.BeliResponse;
import com.idw.project.notebookstation.response.KeranjangDetailResponse;
import com.idw.project.notebookstation.response.KeranjangSumResponse;
import com.idw.project.notebookstation.response.KonsumenDetailResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengirimanActivity extends AppCompatActivity implements OnMapReadyCallback {
    private RecyclerView recylerView;
    private PengirimanAdapter pengirimanAdapter;
    private ArrayList<Keranjang> keranjangArrayList = new ArrayList<>();
    private TextView tv_total_harga, tv_ongkos_kirim, tv_total_tagihan;
    private Button btn_bayar;
    private EditText edt_alamat;

    int EXTRA_ID_KERANJANG;

    String keranjang_sum, LATITUDE, LONGITUDE;

    DecimalFormat df;

    ApiInterface apiInterface;
    SessionManager sessionManager;

    int ongkos_kirim = 15000;
    int total_tagihan;

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengiriman);
        setTitle("Pengiriman");

        df = new DecimalFormat("#,###");

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        EXTRA_ID_KERANJANG = getIntent().getIntExtra(Keranjang.TAG, 0);

        edt_alamat = findViewById(R.id.edt_alamat);
        tv_total_harga = findViewById(R.id.tv_total_harga);
        tv_ongkos_kirim = findViewById(R.id.tv_ongkos_kirim);
        tv_total_tagihan = findViewById(R.id.tv_total_tagihan);
        recylerView = findViewById(R.id.recylerView2);
        btn_bayar = findViewById(R.id.btn_bayar);

        //Maps
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_lokasi);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        if (sessionManager.isLoggedIn()) {

            getKeranjang();

            apiInterface.konsumenById(sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<KonsumenDetailResponse>() {
                @Override
                public void onResponse(Call<KonsumenDetailResponse> call, Response<KonsumenDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getMaster().size() > 0) {
                            String alamat = response.body().getMaster().get(0).getAlamat();

                            edt_alamat.setText(alamat);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<KonsumenDetailResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                }
            });

            apiInterface.keranjangDetail(sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<KeranjangDetailResponse>() {
                @Override
                public void onResponse(Call<KeranjangDetailResponse> call, final Response<KeranjangDetailResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getMaster().size() > 0) {

                            apiInterface.keranjangSum(sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<KeranjangSumResponse>() {
                                @Override
                                public void onResponse(Call<KeranjangSumResponse> call, Response<KeranjangSumResponse> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body() != null && response.body().getMaster().size() > 0) {
                                            System.out.println("datanya" + response.body().getMaster().get(0).getSUMJumlahHarga());
                                            keranjang_sum = response.body().getMaster().get(0).getSUMJumlahHarga();

                                            tv_total_harga.setText("Rp. " + df.format(Double.valueOf(keranjang_sum)));
                                            tv_ongkos_kirim.setText("Rp. " + df.format(Double.valueOf(ongkos_kirim)));

                                            total_tagihan = Integer.parseInt(keranjang_sum) + ongkos_kirim;

                                            tv_total_tagihan.setText("Rp. " + df.format(Double.valueOf(total_tagihan)));

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


                                btn_bayar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (LATITUDE != null && LONGITUDE != null) {
                                            for (int i = 0; i < response.body().getMaster().size(); i++) {
                                                System.out.println("cek nama produk" + response.body().getMaster().get(i).getNamaProduk());
                                                System.out.println("cek konsumen" + response.body().getMaster().get(i).getIdKonsumen());

                                                System.out.println("cek produk" + response.body().getMaster().get(i).getIdProduk());

                                                String id_konsumens = response.body().getMaster().get(i).getIdKonsumen();
                                                String id_produks = response.body().getMaster().get(i).getIdProduk();
                                                String jumlah = response.body().getMaster().get(i).getJumlah();
                                                String catatan_opsional = response.body().getMaster().get(i).getCatatan_opsional();

                                                //buat string secara acak
                                                int leftLimit = 97; // letter 'a'
                                                int rightLimit = 122; // letter 'z'
                                                int targetStringLength = 10;
                                                Random random = new Random();
                                                StringBuilder buffer = new StringBuilder(targetStringLength);

                                                for (int j = 0; j < targetStringLength; j++) {
                                                    int randomLimitedInt = leftLimit + (int)
                                                            (random.nextFloat() * (rightLimit - leftLimit + 1));
                                                    buffer.append((char) randomLimitedInt);
                                                }
                                                String generatedString = buffer.toString();
                                                final String kode_pesanan = "SC-"+generatedString+"-2020";

                                                System.out.println("oooo" + kode_pesanan);

                                                apiInterface.beli(kode_pesanan, id_konsumens, id_produks, jumlah, String.valueOf(total_tagihan), catatan_opsional, edt_alamat.getText().toString(), LATITUDE, LONGITUDE).enqueue(new Callback<BeliResponse>() {
                                                    @Override
                                                    public void onResponse(Call<BeliResponse> call, Response<BeliResponse> response) {
                                                        if (response.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(getApplicationContext(), DetailPembayaranActivity.class);
                                                            intent.putExtra("kode_pesanan", kode_pesanan);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<BeliResponse> call, Throwable t) {
                                                        t.printStackTrace();
                                                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                            }
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Silahkan mengisi data lokasi dahulu", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });


                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<KeranjangDetailResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void getKeranjang() {
        apiInterface.keranjangDetail(sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<KeranjangDetailResponse>() {
            @Override
            public void onResponse(Call<KeranjangDetailResponse> call, Response<KeranjangDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMaster().size() > 0) {

                        String id_keranjang = response.body().getMaster().get(0).getIdKeranjang();
                        keranjangArrayList.addAll(response.body().getMaster());

                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        recylerView.setLayoutManager(manager);
                        recylerView.setHasFixedSize(true);
                        pengirimanAdapter = new PengirimanAdapter(getApplicationContext(), keranjangArrayList);
                        recylerView.setAdapter(pengirimanAdapter);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KeranjangDetailResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(10);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0.507068,101.447779)));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            LATITUDE = extras.getString("latitude");
            LONGITUDE = extras.getString("longitude");
            System.out.println("kucing"+LATITUDE+" "+ LONGITUDE);

            if (LATITUDE != null && LONGITUDE != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(LATITUDE),Double.valueOf(LONGITUDE))));
            }
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                startActivity(new Intent(DetailPengirimanActivity.this, MapActivity.class));


            }
        });

        //tambahan untuk permission
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            mMap.setMyLocationEnabled(true);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
