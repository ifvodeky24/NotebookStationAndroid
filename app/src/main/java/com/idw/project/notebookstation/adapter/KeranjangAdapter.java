package com.idw.project.notebookstation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailProdukKeranjangActivity;
import com.idw.project.notebookstation.activity.MainActivity;
import com.idw.project.notebookstation.config.ServerConfig;
import com.idw.project.notebookstation.model.Keranjang;
import com.idw.project.notebookstation.response.HapusKeranjangResponse;
import com.idw.project.notebookstation.response.KeranjangCheckResponse;
import com.idw.project.notebookstation.response.KeranjangUpdateResponse;
import com.idw.project.notebookstation.response.UpdateCatatanResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public void onBindViewHolder(@NonNull final KeranjangAdapter.KeranjangViewHolder keranjangViewHolder, final int i) {

        Picasso.with(context)
                .load(ServerConfig.PRODUK_IMAGE + keranjangList.get(i).getFoto1())
                .fit()
                .centerCrop()
                .into(keranjangViewHolder.iv_foto);

        final DecimalFormat df = new DecimalFormat("#,###");

        keranjangViewHolder.tv_nama_produk.setText(keranjangList.get(i).getNamaProduk());
        keranjangViewHolder.tv_merk.setText(keranjangList.get(i).getMerkProduk());
        keranjangViewHolder.tv_harga.setText("Rp." + df.format(Double.valueOf(keranjangList.get(i).getJumlahHarga())));
        keranjangViewHolder.tv_qty.setText(keranjangList.get(i).getJumlah());
        keranjangViewHolder.edt_catatan.setText(keranjangList.get(i).getCatatan_opsional());

        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        keranjangViewHolder.edt_catatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//                if(charSequence.length() != 0)
//                    keranjangViewHolder.edt_catatan.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                apiInterface.updateCatatan(keranjangList.get(i).getIdKeranjang(), keranjangViewHolder.edt_catatan.getText().toString()).enqueue(new Callback<UpdateCatatanResponse>() {
                    @Override
                    public void onResponse(Call<UpdateCatatanResponse> call, Response<UpdateCatatanResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateCatatanResponse> call, Throwable t) {
                        Toast.makeText(context, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        keranjangViewHolder.iv_hapus.setOnClickListener(new View.OnClickListener() {
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

                                apiInterface.checkIdKeranjang(String.valueOf(keranjangList.get(i).getIdProduk()), sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN)).enqueue(new Callback<KeranjangCheckResponse>() {
                                    @Override
                                    public void onResponse(Call<KeranjangCheckResponse> call, Response<KeranjangCheckResponse> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body() != null) {
                                                if (response.body().getMaster().size() > 0) {
                                                    String id_keranjang = response.body().getMaster().get(0).getIdKeranjang();
                                                    System.out.println("id keranjangnya adalah" + id_keranjang);

                                                    apiInterface.hapusKeranjang(id_keranjang).enqueue(new Callback<HapusKeranjangResponse>() {
                                                        @Override
                                                        public void onResponse(Call<HapusKeranjangResponse> call, Response<HapusKeranjangResponse> response) {
                                                            if (response.isSuccessful()) {
                                                                Toast.makeText(context, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();

                                                                keranjangList.remove(i);
                                                                notifyItemRemoved(i);
                                                                notifyDataSetChanged();

                                                            } else {
                                                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<HapusKeranjangResponse> call, Throwable t) {
                                                            Toast.makeText(context, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                } else {
//                                    Toast.makeText(getApplicationContext(), "id kosong", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<KeranjangCheckResponse> call, Throwable t) {
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


        keranjangViewHolder.cv_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProdukKeranjangActivity.class);
                intent.putExtra(Keranjang.TAG_PRODUK, keranjangList.get(i).getIdProduk());
                System.out.println("id ini" + keranjangList.get(i).getIdProduk());
                context.startActivity(intent);
            }
        });


        if (Integer.parseInt(keranjangList.get(i).getJumlah()) < 2) {
            keranjangViewHolder.fab_qty_min.animate().alpha(0).setDuration(300).start();
            keranjangViewHolder.fab_qty_min.setEnabled(false);
        }


        keranjangViewHolder.fab_qty_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(keranjangViewHolder.tv_qty.getText().toString());
                if (qty > 1) {
                    qty--;
                    keranjangViewHolder.tv_qty.setText(qty + "");
                }

                if (qty < 10) {
                    keranjangViewHolder.fab_qty_add.animate().alpha(1).setDuration(300).start();
                    keranjangViewHolder.fab_qty_add.setEnabled(true);
                }

                if (qty < 2) {
                    keranjangViewHolder.fab_qty_min.animate().alpha(0).setDuration(300).start();
                    keranjangViewHolder.fab_qty_min.setEnabled(false);
                }

                int total = Integer.parseInt(keranjangList.get(i).getHarga()) * qty;
                keranjangViewHolder.tv_harga.setText("Rp. " + df.format(Double.valueOf(total)));

                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                apiInterface.updateKeranjang(keranjangList.get(i).getIdKeranjang(), String.valueOf(qty), String.valueOf(total), keranjangViewHolder.edt_catatan.getText().toString()).enqueue(new Callback<KeranjangUpdateResponse>() {
                    @Override
                    public void onResponse(Call<KeranjangUpdateResponse> call, Response<KeranjangUpdateResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Item Dikurangi", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KeranjangUpdateResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context, "Gagal Terhubung Ke Server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        keranjangViewHolder.fab_qty_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(keranjangViewHolder.tv_qty.getText().toString());
                if (qty < 10) {
                    qty++;
                    keranjangViewHolder.tv_qty.setText(qty + "");
                }

                if (qty > 9) {
                    keranjangViewHolder.fab_qty_add.animate().alpha(0).setDuration(300).start();
                    keranjangViewHolder.fab_qty_add.setEnabled(false);
                }

                if (qty > 1) {
                    keranjangViewHolder.fab_qty_min.animate().alpha(1).setDuration(300).start();
                    keranjangViewHolder.fab_qty_min.setEnabled(true);
                }

                int total = Integer.parseInt(keranjangList.get(i).getHarga()) * qty;
                keranjangViewHolder.tv_harga.setText("Rp. " + df.format(Double.valueOf(total)));

                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                apiInterface.updateKeranjang(keranjangList.get(i).getIdKeranjang(), String.valueOf(qty), String.valueOf(total), keranjangViewHolder.edt_catatan.getText().toString()).enqueue(new Callback<KeranjangUpdateResponse>() {
                    @Override
                    public void onResponse(Call<KeranjangUpdateResponse> call, Response<KeranjangUpdateResponse> response) {
                        if (response.isSuccessful()) {


                            Toast.makeText(context, "Item Ditambah", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KeranjangUpdateResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context, "Gagal Terhubung Ke Server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

//    private void refresh(ArrayList<Keranjang> keranjangs) {
//        keranjangList.clear();
//
//        keranjangList.addAll(keranjangs);
//        notifyDataSetChanged();
//    }


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
        EditText edt_catatan;

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
            edt_catatan = itemView.findViewById(R.id.edt_catatan);

        }

    }
}
