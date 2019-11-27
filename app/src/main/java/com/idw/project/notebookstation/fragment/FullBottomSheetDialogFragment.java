package com.idw.project.notebookstation.fragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.adapter.ProdukAdapter;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.response.ProdukGetAllResponse;
import com.idw.project.notebookstation.response.ProdukSearchResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullBottomSheetDialogFragment extends BottomSheetDialogFragment  {

    private BottomSheetBehavior mBehavior;

    private ProdukAdapter produkAdapter;
    private ArrayList<Produk> produkArrayList = new ArrayList<>();

    private RecyclerView recyclerView;

    private ApiInterface apiInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.bottom_sheet_layout, null);

        recyclerView = view.findViewById(R.id.recyclerView);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        LinearLayout linearLayout = view.findViewById(R.id.root);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = getScreenHeight();
        linearLayout.setLayoutParams(params);

        getProduk();

        final EditText editTextSearch = view.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(textWatcher);


        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    private void getProduk() {
        apiInterface.produkGetAll().enqueue(new Callback<ProdukGetAllResponse>() {
            @Override
            public void onResponse(Call<ProdukGetAllResponse> call, Response<ProdukGetAllResponse> response) {
                System.out.println("responnya"+response);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if(response.body().getMaster().size()>0){
                            produkArrayList.addAll(response.body().getMaster());
                            System.out.println(response.body().getMaster().get(0).getNamaProduk());

                            LinearLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setHasFixedSize(true);
                            produkAdapter = new ProdukAdapter(getActivity(), produkArrayList);
                            recyclerView.setAdapter(produkAdapter);

                        }else {
                            Toast.makeText(getActivity(), "Data Produk Kosong", Toast.LENGTH_SHORT).show();
                        }
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

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) {
            search(s.toString().toLowerCase());
        }

    };

    private void search(String searchTerm){
        produkArrayList.clear();

        apiInterface.searchProduk(searchTerm).enqueue(new Callback<ProdukSearchResponse>() {
            @Override
            public void onResponse(Call<ProdukSearchResponse> call, Response<ProdukSearchResponse> response) {
                System.out.println("response dari"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        produkArrayList.addAll(response.body().getMaster());

                        LinearLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setHasFixedSize(true);
                        produkAdapter = new ProdukAdapter(getActivity(), produkArrayList);
                        recyclerView.setAdapter(produkAdapter);
                    }
                }else {
                    Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdukSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
