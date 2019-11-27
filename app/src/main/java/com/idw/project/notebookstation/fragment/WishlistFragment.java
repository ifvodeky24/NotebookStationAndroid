package com.idw.project.notebookstation.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.DetailProdukActivity;
import com.idw.project.notebookstation.adapter.ProdukAdapter;
import com.idw.project.notebookstation.adapter.WishlistAdapter;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.model.Wishlist;
import com.idw.project.notebookstation.response.WishlistDetailResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {
    private RecyclerView recylerView;
    private WishlistAdapter wishlistAdapter;
    private ArrayList<Wishlist> wishlistArrayList = new ArrayList<>();

    private ApiInterface apiInterface;
    private SessionManager sessionManager;

    String id_konsumen;

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
        getActivity().setTitle("Wishlist");

        recylerView = view.findViewById(R.id.recylerView1);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if (sessionManager.isLoggedIn()){
//            getWishlist();
//
//        }
    }

    private void getWishlist() {
        id_konsumen = sessionManager.getLoginDetail().get(SessionManager.ID_KONSUMEN);

        apiInterface.wishlistDetail(id_konsumen).enqueue(new Callback<WishlistDetailResponse>() {
            @Override
            public void onResponse(Call<WishlistDetailResponse> call, Response<WishlistDetailResponse> response) {
                System.out.println("responya"+response);
                if (response.isSuccessful()){
                    if(response.body().getMaster().size()>0){
                        wishlistArrayList.addAll(response.body().getMaster());
                        System.out.println(response.body().getMaster().get(0).getNamaProduk());

                        LinearLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        recylerView.setLayoutManager(manager);
                        recylerView.setHasFixedSize(true);
                        wishlistAdapter = new WishlistAdapter(getActivity(), wishlistArrayList);
                        recylerView.setAdapter(wishlistAdapter);

                    }else {
                        Toast.makeText(getActivity(), "Data Wishlist Kosong", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WishlistDetailResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        wishlistArrayList.clear();
        getWishlist();

    }
}
