package com.idw.project.notebookstation.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.adapter.ProdukAdapter;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.response.ProdukGetAllResponse;
import com.idw.project.notebookstation.response.ProdukSearchResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    private RecyclerView recylerView;
    private ProdukAdapter produkAdapter;
    private ArrayList<Produk> produkArrayList = new ArrayList<>();
    private SessionManager sessionManager;
    private SearchView searchView;
    private ApiInterface apiInterface;

    SliderLayout sliderLayout;



    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
        getActivity().setTitle("Senapelan Computer");

        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
        sliderLayout.setScrollTimeInSec(6);

        setSliderViews();

        recylerView = view.findViewById(R.id.recylerView);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        setHasOptionsMenu(true);


        return view;
    }

    private void setSliderViews() {
        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.acerslider);
                    break;

                case 1:
                    sliderView.setImageDrawable(R.drawable.slider2);
                    break;

                case 2:
                    sliderView.setImageDrawable(R.drawable.slider3);
                    break;

                case 3:
                    sliderView.setImageDrawable(R.drawable.slider3);
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }
            });

            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (sessionManager.isLoggedIn()){
            MenuItem menuItem = menu.findItem(R.id.chat);
            menuItem.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chat:
                Toast.makeText(getActivity(), "Klik Chat", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search:
                if (getFragmentManager() != null) {
                    new FullBottomSheetDialogFragment().show(getFragmentManager(), "search_dialog");
                }
                Toast.makeText(getActivity(), "Klik Chat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProduk();
    }

    private void getProduk() {
        apiInterface.produkGetAll().enqueue(new Callback<ProdukGetAllResponse>() {
            @Override
            public void onResponse(Call<ProdukGetAllResponse> call, Response<ProdukGetAllResponse> response) {
                System.out.println("responya"+response);
                if (response.isSuccessful()){
                    if(response.body().getMaster().size()>0){
                        produkArrayList.addAll(response.body().getMaster());
                        System.out.println(response.body().getMaster().get(0).getNamaProduk());

                        LinearLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        recylerView.setLayoutManager(manager);
                        recylerView.setHasFixedSize(true);
                        produkAdapter = new ProdukAdapter(getActivity(), produkArrayList);
                        recylerView.setAdapter(produkAdapter);

                    }else {
                        Toast.makeText(getActivity(), "Data Produk Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdukGetAllResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

}
