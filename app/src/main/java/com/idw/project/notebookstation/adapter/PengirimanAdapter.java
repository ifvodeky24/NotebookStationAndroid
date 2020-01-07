package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Keranjang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PengirimanAdapter extends RecyclerView.Adapter<PengirimanAdapter.PengirimanViewHolder> {

    private Context context;

    private ArrayList<Keranjang> keranjangList;

    public PengirimanAdapter(Context context, ArrayList<Keranjang> keranjangList) {
        this.context = context;
        this.keranjangList = keranjangList;
    }

    @NonNull
    @Override
    public PengirimanAdapter.PengirimanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pengiriman_item, viewGroup, false);
        return new PengirimanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PengirimanAdapter.PengirimanViewHolder pengirimanViewHolder, final int i) {

        Picasso.with(context)
                .load(ServerConfig.PRODUK_IMAGE+ keranjangList.get(i).getFoto1())
                .fit()
                .centerCrop()
                .into(pengirimanViewHolder.iv_foto);

        final DecimalFormat df = new DecimalFormat( "#,###");

        pengirimanViewHolder.tv_nama_produk.setText(keranjangList.get(i).getNamaProduk());
        pengirimanViewHolder.tv_merk.setText(keranjangList.get(i).getMerkProduk());
        pengirimanViewHolder.tv_catatan_opsional.setText(keranjangList.get(i).getCatatan_opsional());
        pengirimanViewHolder.tv_jumlah.setText(keranjangList.get(i).getJumlah()+" Pcs");
        pengirimanViewHolder.tv_harga.setText("Rp."+df.format(Double.valueOf(keranjangList.get(i).getJumlahHarga())));

        pengirimanViewHolder.cv_keranjang.setOnClickListener(new View.OnClickListener() {
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

    class PengirimanViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_foto;
        TextView tv_nama_produk, tv_merk, tv_harga, tv_jumlah, tv_catatan_opsional;
        CardView cv_keranjang;

        PengirimanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            tv_merk = itemView.findViewById(R.id.tv_merk);
            tv_jumlah = itemView.findViewById(R.id.tv_jumlah);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_catatan_opsional = itemView.findViewById(R.id.tv_catatan_opsional);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            cv_keranjang = itemView.findViewById(R.id.cv_keranjang);


        }

    }
}
