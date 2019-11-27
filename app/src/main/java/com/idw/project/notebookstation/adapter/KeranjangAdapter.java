package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.MainActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Keranjang;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {

    private Context context;

    private ArrayList<Keranjang> keranjangList;

    public KeranjangAdapter(Context context, ArrayList<Keranjang> keranjangList) {
        this.context = context;
        this.keranjangList = keranjangList;
    }

    @NonNull
    @Override
    public KeranjangAdapter.KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.keranjang_item, viewGroup, false);
        return new KeranjangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.KeranjangViewHolder keranjangViewHolder, final int i) {

        Picasso.with(context)
                .load(ServerConfig.PRODUK_IMAGE+ keranjangList.get(i).getFoto1())
                .fit()
                .centerCrop()
                .into(keranjangViewHolder.iv_foto);

        DecimalFormat df = new DecimalFormat( "#,###");

        keranjangViewHolder.tv_nama_produk.setText(keranjangList.get(i).getNamaProduk());
        keranjangViewHolder.tv_merk.setText(keranjangList.get(i).getMerkProduk());
        keranjangViewHolder.tv_harga.setText("Rp."+df.format(Double.valueOf(keranjangList.get(i).getHarga())));


        keranjangViewHolder.cv_keranjang.setOnClickListener(new View.OnClickListener() {
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
        return keranjangList.size();
    }

    class KeranjangViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_foto, iv_hapus;
        TextView tv_nama_produk, tv_merk, tv_harga;
        CardView cv_keranjang;
        FloatingActionButton fab_qty_min, fab_qty_add;
        TextView tv_qty;

        KeranjangViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            tv_merk = itemView.findViewById(R.id.tv_merk);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            cv_keranjang = itemView.findViewById(R.id.cv_keranjang);
            iv_hapus = itemView.findViewById(R.id.iv_hapus);
            fab_qty_min = itemView.findViewById(R.id.fab_qty_min);
            fab_qty_add = itemView.findViewById(R.id.fab_qty_add);
            tv_qty = itemView.findViewById(R.id.tv_qty);

        }

    }
}
