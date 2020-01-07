package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailPembayaranActivity;
import com.idw.project.notebookstation.activity.DetailPembayaranTransaksiActivity;
import com.idw.project.notebookstation.activity.DetailTransaksiActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Pesanan;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {

    private Context context;

    private ArrayList<Pesanan> pesananList;

    public TransaksiAdapter(Context context, ArrayList<Pesanan> pesananList) {
        this.context = context;
        this.pesananList = pesananList;
    }

    @NonNull
    @Override
    public TransaksiAdapter.TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaksi_item, viewGroup, false);
        return new TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.TransaksiViewHolder transaksiViewHolder, final int i) {

        Picasso.with(context)
                .load(ServerConfig.PRODUK_IMAGE+ pesananList.get(i).getFoto1())
                .fit()
                .centerCrop()
                .into(transaksiViewHolder.iv_foto);

        DecimalFormat df = new DecimalFormat( "#,###");

        transaksiViewHolder.tv_nama_produk.setText(pesananList.get(i).getNamaProduk());
        transaksiViewHolder.tv_merk.setText(pesananList.get(i).getMerkProduk());
        transaksiViewHolder.tv_status.setText(pesananList.get(i).getStatus());
        transaksiViewHolder.tv_kode_pesanan.setText(pesananList.get(i).getKodePesanan());
        transaksiViewHolder.tv_tanggal_pesanan.setText(pesananList.get(i).getTanggalPesanan()+" WIB");
        transaksiViewHolder.tv_harga.setText("Rp."+df.format(Double.valueOf(pesananList.get(i).getHarga())));

        transaksiViewHolder.cv_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pesananList.get(i).getStatus().equalsIgnoreCase("Menunggu Pembayaran")){
                    Intent intent = new Intent(context, DetailPembayaranTransaksiActivity.class);
                    intent.putExtra("kode_pesanan", String.valueOf(pesananList.get(i).getKodePesanan()));
                    intent.putExtra("total_tagihan", String.valueOf(pesananList.get(i).getTotalTagihan()));
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, DetailTransaksiActivity.class);
                    intent.putExtra("kode_pesanan", String.valueOf(pesananList.get(i).getKodePesanan()));
                    intent.putExtra("total_tagihan", String.valueOf(pesananList.get(i).getTotalTagihan()));
                    intent.putExtra("id_produk", String.valueOf(pesananList.get(i).getIdProduk()));
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    class TransaksiViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nama_produk, tv_merk, tv_harga, tv_status, tv_tanggal_pesanan, tv_kode_pesanan;
        CardView cv_transaksi;
        ImageView iv_foto;

        TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            tv_merk = itemView.findViewById(R.id.tv_merk);
            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            tv_tanggal_pesanan = itemView.findViewById(R.id.tv_tanggal_pesanan);
            cv_transaksi = itemView.findViewById(R.id.cv_transaksi);

        }

    }
}
