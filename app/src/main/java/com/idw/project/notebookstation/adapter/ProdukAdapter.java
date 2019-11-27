package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailProdukActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.fragment.BerandaFragment;
import com.idw.project.notebookstation.model.Produk;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {

    private Context context;

    private ArrayList<Produk> produkList;

    public ProdukAdapter(Context context, ArrayList<Produk> produkList) {
        this.context = context;
        this.produkList = produkList;
    }

    @NonNull
    @Override
    public ProdukAdapter.ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.produk_item, viewGroup, false);
        return new ProdukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.ProdukViewHolder produkViewHolder, final int i) {

        Picasso.with(context)
                .load(ServerConfig.PRODUK_IMAGE+ produkList.get(i).getFoto1())
                .fit()
                .centerCrop()
                .into(produkViewHolder.iv_foto);

        DecimalFormat df = new DecimalFormat( "#,###");

        produkViewHolder.tv_nama_produk.setText(produkList.get(i).getNamaProduk());
        produkViewHolder.tv_merk.setText(produkList.get(i).getMerkProduk());
        produkViewHolder.tv_harga.setText("Rp."+df.format(Double.valueOf(produkList.get(i).getHarga())));


        produkViewHolder.cv_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProdukActivity.class);
                intent.putExtra(DetailProdukActivity.TAG, produkList.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    class ProdukViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_foto;
        TextView tv_nama_produk, tv_merk, tv_harga;
        CardView cv_produk;

        ProdukViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            tv_merk = itemView.findViewById(R.id.tv_merk);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            cv_produk = itemView.findViewById(R.id.cv_produk);

        }
    }
}

