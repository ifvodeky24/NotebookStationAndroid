package com.idw.project.notebookstation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.model.Produk;

import java.text.DecimalFormat;
import java.util.Objects;

public class DetailKonfirmasiProdukActivity extends AppCompatActivity {
    ImageView iv_foto, iv_tambah_keranjang;
    TextView tv_nama_produk, tv_merk, tv_harga, tv_qty, tv_harga1;
    Button btn_beli;
    EditText edt_catatan;
    FloatingActionButton fab_qty_min, fab_qty_add;

    public static  final String TAG ="produk";
    Produk produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konfirmasi_produk);

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

        produk =  Objects.requireNonNull(getIntent().getExtras()).getParcelable(TAG);

        if (produk != null) {
            System.out.println("cek id saja "+produk.getIdProduk());

            tv_nama_produk.setText(produk.getNamaProduk());
            tv_merk.setText(produk.getMerkProduk());
            tv_harga.setText("Rp. "+df.format(Double.valueOf(produk.getHarga())));
            tv_harga1.setText("Rp. "+df.format(Double.valueOf(produk.getHarga())));
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

                if (qty <10){
                    fab_qty_add.animate().alpha(1).setDuration(300).start();
                    fab_qty_add.setEnabled(true);
                }

                if (qty <2){
                    fab_qty_min.animate().alpha(0).setDuration(300).start();
                    fab_qty_min.setEnabled(false);
                }

                int total = produk.getHarga() *qty;
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

                int total = produk.getHarga() *qty;
                tv_harga1.setText("Rp. "+df.format(Double.valueOf(total)));

            }
        });

        iv_tambah_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Klik", Toast.LENGTH_SHORT).show();
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
