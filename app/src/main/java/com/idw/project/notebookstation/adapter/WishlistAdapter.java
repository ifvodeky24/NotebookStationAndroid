package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailKonfirmasiProdukActivity;
import com.idw.project.notebookstation.activity.DetailProdukWishlistActivity;
import com.idw.project.notebookstation.activity.MainActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.model.Wishlist;
import com.idw.project.notebookstation.response.HapusWishlistResponse;
import com.idw.project.notebookstation.response.WishlistCheckResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private Context context;

    private ArrayList<Wishlist> wishlistList;


    public WishlistAdapter(Context context, ArrayList<Wishlist> wishlistList) {
        this.context = context;
        this.wishlistList = wishlistList;
    }


    @NonNull
    @Override
    public WishlistAdapter.WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, viewGroup, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.WishlistViewHolder wishlistViewHolder, final int i) {

        Picasso.with(context)
                .load(ServerConfig.PRODUK_IMAGE+ wishlistList.get(i).getFoto1())
                .fit()
                .centerCrop()
                .into(wishlistViewHolder.iv_foto);

        DecimalFormat df = new DecimalFormat( "#,###");

        wishlistViewHolder.tv_nama_produk.setText(wishlistList.get(i).getNamaProduk());
        wishlistViewHolder.tv_merk.setText(wishlistList.get(i).getMerkProduk());
        wishlistViewHolder.tv_harga.setText("Rp."+df.format(Double.valueOf(wishlistList.get(i).getHarga())));

        wishlistViewHolder.cv_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProdukWishlistActivity.class);
                intent.putExtra(Wishlist.TAG, wishlistList.get(i).getIdProduk());
                System.out.println("id ini"+wishlistList.get(i).getIdProduk());
                context.startActivity(intent);
            }
        });

        wishlistViewHolder.iv_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                //set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Anda ingin menghapus item ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                SessionManager sessionManager = new SessionManager(context);

                                apiInterface.checkId(String.valueOf(wishlistList.get(i).getIdProduk()), sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<WishlistCheckResponse>() {
                                    @Override
                                    public void onResponse(Call<WishlistCheckResponse> call, Response<WishlistCheckResponse> response) {
                                        if (response.isSuccessful()){
                                            if (response.body() != null){
                                                if (response.body().getMaster().size()>0){
                                                    String id_wishlist = response.body().getMaster().get(0).getIdWishlist();
                                                    System.out.println("id wishlistnya adalah"+id_wishlist);

                                                    apiInterface.hapusWishlist(id_wishlist).enqueue(new Callback<HapusWishlistResponse>() {
                                                        @Override
                                                        public void onResponse(Call<HapusWishlistResponse> call, Response<HapusWishlistResponse> response) {
                                                            if (response.isSuccessful()){
                                                                Toast.makeText(context, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();

                                                                wishlistList.remove(i);
                                                                notifyItemRemoved(i);
                                                                notifyDataSetChanged();
                                                            }else {
                                                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<HapusWishlistResponse> call, Throwable t) {
                                                            Toast.makeText(context, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }else {
                                                    Toast.makeText(context, "id kosong", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }else {
                                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WishlistCheckResponse> call, Throwable t) {
                                        Toast.makeText(context, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
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
        });

        wishlistViewHolder.btn_beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailKonfirmasiProdukActivity.class);
                intent.putExtra(Produk.TAG, Integer.parseInt(wishlistList.get(i).getIdProduk()));
                System.out.println("nilai dari"+Integer.parseInt(wishlistList.get(i).getIdProduk()));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return wishlistList.size();
    }

    class WishlistViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_foto, iv_hapus;
        TextView tv_nama_produk, tv_merk, tv_harga;
        CardView cv_wishlist;
        Button btn_beli;

        WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            tv_merk = itemView.findViewById(R.id.tv_merk);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            cv_wishlist = itemView.findViewById(R.id.cv_wishlist);
            iv_hapus = itemView.findViewById(R.id.iv_hapus);
            btn_beli = itemView.findViewById(R.id.btn_beli);

        }

    }
}