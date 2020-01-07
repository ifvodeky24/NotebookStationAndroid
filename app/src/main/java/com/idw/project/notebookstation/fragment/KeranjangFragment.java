package com.idw.project.notebookstation.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailPengirimanActivity;
import com.idw.project.notebookstation.activity.MainActivity;
import com.idw.project.notebookstation.adapter.KeranjangAdapter;
import com.idw.project.notebookstation.model.Keranjang;
import com.idw.project.notebookstation.response.BeliResponse;
import com.idw.project.notebookstation.response.KeranjangDetailResponse;
import com.idw.project.notebookstation.response.KeranjangSumResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KeranjangFragment extends Fragment {
    private RecyclerView recylerView;
    private KeranjangAdapter keranjangAdapter;
    private ArrayList<Keranjang> keranjangArrayList = new ArrayList<>();
    private LinearLayout ll_label, ll_data_keranjang_kosong;
    private Button btn_belanja_sekarang, btn_beli;

    private ApiInterface apiInterface;
    private SessionManager sessionManager;

    private String id_konsumen;

    TextView tv_harga1;

    String keranjang_sum, id_keranjang;

    DecimalFormat df;


    public KeranjangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setTitle("Keranjang");
        }

        df = new DecimalFormat( "#,###");

        recylerView = view.findViewById(R.id.recylerView1);
        ll_label = view.findViewById(R.id.ll_label);
        ll_data_keranjang_kosong = view.findViewById(R.id.ll_data_keranjang_kosong);
        btn_belanja_sekarang = view.findViewById(R.id.btn_belanja_sekarang);
        btn_beli = view.findViewById(R.id.btn_beli);
        tv_harga1 = view.findViewById(R.id.tv_harga1);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isLoggedIn()){

            id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

        }

        btn_belanja_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

        getKeranjang();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getKeranjang() {
        apiInterface.keranjangDetail(id_konsumen).enqueue(new Callback<KeranjangDetailResponse>() {
            @Override
            public void onResponse(Call<KeranjangDetailResponse> call, final Response<KeranjangDetailResponse> response) {
                System.out.println("responnya"+response);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if(response.body().getMaster().size()>0){
                            ll_data_keranjang_kosong.setVisibility(View.GONE);
                            ll_label.setVisibility(View.VISIBLE);
                            keranjangArrayList.addAll(response.body().getMaster());

                            id_keranjang = response.body().getMaster().get(0).getIdKeranjang();

                            String[] id_produks = null;

                            apiInterface.keranjangSum(id_konsumen).enqueue(new Callback<KeranjangSumResponse>() {
                                @Override
                                public void onResponse(Call<KeranjangSumResponse> call, Response<KeranjangSumResponse> response) {
                                    if (response.isSuccessful()){
                                        if (response.body().getMaster().size()>0){
                                            System.out.println("datanya"+response.body().getMaster().get(0).getSUMJumlahHarga());
                                            keranjang_sum = response.body().getMaster().get(0).getSUMJumlahHarga();

                                            tv_harga1.setText("Rp. "+df.format(Double.valueOf(keranjang_sum)));


                                        }

                                    }else {
                                        Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<KeranjangSumResponse> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                }
                            });

                            btn_beli.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(getActivity(), DetailPengirimanActivity.class);
                                    intent.putExtra(Keranjang.TAG, id_keranjang);
                                    System.out.println("nilai dari"+id_keranjang);
                                    getActivity().startActivity(intent);

                                }
                            });

                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            keranjangAdapter = new KeranjangAdapter(getActivity(), keranjangArrayList);
                            recylerView.setAdapter(keranjangAdapter);

                        }else {
                            ll_label.setVisibility(View.GONE);
                            ll_data_keranjang_kosong.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KeranjangDetailResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
