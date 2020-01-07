package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.response.BeliKhususResponse;
import com.idw.project.notebookstation.response.BeliResponse;
import com.idw.project.notebookstation.response.ProdukDetailResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeliTanpaLoginActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextInputEditText edt_nama_lengkap, edt_email, edt_nomor_handphone, edt_alamat;
    EditText edt_catatan;

    TextView tv_nama_produk, tv_merk, tv_harga, tv_qty, tv_total_tagihan;

    FloatingActionButton fab_qty_min, fab_qty_add;

    ImageView iv_foto;

    Button btn_beli;

    String nama_lengkap, email, nomor_handphone, alamat, catatan, id_produk, kode_pesanan, LATITUDE, LONGITUDE;

    String nama_produk, merk, harga, total_tagihan;

    int total;

    ApiInterface apiInterface;

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_tanpa_login);

        edt_nama_lengkap = findViewById(R.id.edt_nama_lengkap);
        edt_email = findViewById(R.id.edt_email);
        edt_nomor_handphone = findViewById(R.id.edt_nomor_handphone);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_catatan = findViewById(R.id.edt_catatan);
        tv_nama_produk = findViewById(R.id.tv_nama_produk);
        tv_merk = findViewById(R.id.tv_merk);
        tv_harga = findViewById(R.id.tv_harga);
        tv_qty = findViewById(R.id.tv_qty);
        tv_total_tagihan = findViewById(R.id.tv_total_tagihan);
        fab_qty_min = findViewById(R.id.fab_qty_min);
        fab_qty_add = findViewById(R.id.fab_qty_add);
        btn_beli = findViewById(R.id.btn_beli);
        iv_foto = findViewById(R.id.iv_foto);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final DecimalFormat df = new DecimalFormat("#,###");

        //Maps
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_lokasi);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            id_produk = extras.getString("id_produk");
            System.out.println("garong"+id_produk);

            //buat string secara acak
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 11;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);

            for (int j = 0; j < targetStringLength; j++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            kode_pesanan = "SCK-"+generatedString+"-2020";

            System.out.println("oooo" + kode_pesanan);

            apiInterface.produkById(id_produk).enqueue(new Callback<ProdukDetailResponse>() {
                @Override
                public void onResponse(Call<ProdukDetailResponse> call, Response<ProdukDetailResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().getMaster().size() > 0) {
                            nama_produk = response.body().getMaster().get(0).getNamaProduk();
                            harga = response.body().getMaster().get(0).getHarga();
                            merk = response.body().getMaster().get(0).getMerkProduk();


                            tv_nama_produk.setText(nama_produk);
                            tv_harga.setText("Rp. "+df.format(Double.valueOf(harga)));
                            tv_merk.setText(merk);
                            total = Integer.parseInt(harga);
                            tv_total_tagihan.setText("Rp. " + df.format(Double.valueOf(harga)));

                            Picasso.with(BeliTanpaLoginActivity.this)
                                    .load(ServerConfig.PRODUK_IMAGE + response.body().getMaster().get(0).getFoto1())
                                    .into(iv_foto);

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProdukDetailResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                }
            });

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

                    total = Integer.parseInt(harga) * qty;
                    tv_total_tagihan.setText("Rp. " + df.format(Double.valueOf(total)));

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

                    if (qty > 9) {
                        fab_qty_add.animate().alpha(0).setDuration(300).start();
                        fab_qty_add.setEnabled(false);
                    }

                    if (qty > 1) {
                        fab_qty_min.animate().alpha(1).setDuration(300).start();
                        fab_qty_min.setEnabled(true);
                    }

                    total = Integer.parseInt(harga) * qty;
                    tv_total_tagihan.setText("Rp. " + df.format(Double.valueOf(total)));

                }
            });

            System.out.println("mmmmmm" + tv_qty.getText().toString()+" "+edt_catatan.getText().toString());


            btn_beli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isEmpty = false;

                    final String nama_lengkap = edt_nama_lengkap.getText().toString();
                    final String email = edt_email.getText().toString();
                    final String alamat = edt_alamat.getText().toString();
                    final String nomor_handphone = edt_nomor_handphone.getText().toString();

                    if (nama_lengkap.equalsIgnoreCase("")) {
                        isEmpty = true;
                        Toast.makeText(getApplicationContext(), "Nama Lengkap tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }

                    if (email.equalsIgnoreCase("")) {
                        isEmpty = true;
                        Toast.makeText(getApplicationContext(), "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }

                    if (alamat.equalsIgnoreCase("")) {
                        isEmpty = true;
                        Toast.makeText(getApplicationContext(), "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }

                    if (nomor_handphone.equalsIgnoreCase("")) {
                        isEmpty = true;
                        Toast.makeText(getApplicationContext(), "Nomor Handphone tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }

                    if (LATITUDE == null) {
                        isEmpty = true;
                        Toast.makeText(getApplicationContext(), "Titik Lokasi boleh kosong!", Toast.LENGTH_SHORT).show();
                    }

                    if (LONGITUDE == null) {
                        isEmpty = true;
                        Toast.makeText(getApplicationContext(), "Titik Lokasi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    }

                    if (isEmpty == false) {
                        apiInterface.beliKhusus(kode_pesanan, id_produk, tv_qty.getText().toString(), String.valueOf(total), "Belum Lunas", edt_catatan.getText().toString(), edt_nama_lengkap.getText().toString(), edt_alamat.getText().toString(), edt_email.getText().toString(), edt_nomor_handphone.getText().toString(), LATITUDE, LONGITUDE).enqueue(new Callback<BeliKhususResponse>() {
                            @Override
                            public void onResponse(Call<BeliKhususResponse> call, Response<BeliKhususResponse> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Berhasil beli", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), DetailPembayaranKhususActivity.class);
                                    intent.putExtra("kode_pesanan", kode_pesanan);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BeliKhususResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(10);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0.507068,101.447779)));

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            LATITUDE = extras.getString("latitude");
            LONGITUDE = extras.getString("longitude");
            id_produk = extras.getString("id_produk");
            System.out.println("kucing"+LATITUDE+" "+ LONGITUDE);

            if (LATITUDE != null && LONGITUDE != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(LATITUDE),Double.valueOf(LONGITUDE))));
            }
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent1 = new Intent(getApplicationContext(), Map2Activity.class);
                intent1.putExtra("id_produk", id_produk);
                startActivity(intent1);
                finish();

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
