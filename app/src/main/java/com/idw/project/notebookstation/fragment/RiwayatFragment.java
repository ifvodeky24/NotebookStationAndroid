package com.idw.project.notebookstation.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.adapter.TransaksiAdapter;
import com.idw.project.notebookstation.model.Pesanan;
import com.idw.project.notebookstation.response.SelesaiResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiwayatFragment extends Fragment {
    private RecyclerView recylerView;
    private TransaksiAdapter transaksiAdapter;
    private ArrayList<Pesanan> pesananArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;
    private LinearLayout ll_data_pesanan_kosong;

    String id_konsumen;


    public RiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        recylerView = view.findViewById(R.id.recylerView1);
        ll_data_pesanan_kosong = view.findViewById(R.id.ll_data_pesanan_kosong);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        getData();

        return view;
    }

    private void getData() {
        id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

        apiInterface.selesai(id_konsumen).enqueue(new Callback<SelesaiResponse>() {
            @Override
            public void onResponse(Call<SelesaiResponse> call, Response<SelesaiResponse> response) {
                System.out.println("responya"+response);
                if (response.isSuccessful()){
                    if(response.body().getMaster().size()>0){
                        ll_data_pesanan_kosong.setVisibility(View.GONE);
                        pesananArrayList.addAll(response.body().getMaster());
                        System.out.println(response.body().getMaster().get(0).getIdPesanan());

                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        recylerView.setLayoutManager(manager);
                        recylerView.setHasFixedSize(true);
                        transaksiAdapter = new TransaksiAdapter(getActivity(), pesananArrayList);
                        recylerView.setAdapter(transaksiAdapter);

                    }else {
//                        Toast.makeText(getApplicationContext(), "Data Menunggu Pembayaran Kosong", Toast.LENGTH_SHORT).show();
                        ll_data_pesanan_kosong.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SelesaiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
