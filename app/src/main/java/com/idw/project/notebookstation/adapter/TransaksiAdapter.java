package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Pesanan;
import com.idw.project.notebookstation.model.Produk;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {

    private Context context;

    private ArrayList<Pesanan> pesananList;
//    private ArrayList<Produk> produkList;

    public TransaksiAdapter(Context context, ArrayList<Pesanan> pesananList) {
        this.context = context;
        this.pesananList = pesananList;
//        this.produkList = produkList;
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
        transaksiViewHolder.tv_harga.setText("Rp."+df.format(Double.valueOf(pesananList.get(i).getHarga())));

        transaksiViewHolder.cv_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailProdukActivity.class);
//                intent.putExtra(DetailProdukActivity.TAG, wishlistList.get(i));
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    class TransaksiViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nama_produk, tv_merk, tv_harga, tv_status;
        CardView cv_transaksi;
        ImageView iv_foto;

        TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            tv_merk = itemView.findViewById(R.id.tv_merk);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            cv_transaksi = itemView.findViewById(R.id.cv_transaksi);

        }

    }
}
