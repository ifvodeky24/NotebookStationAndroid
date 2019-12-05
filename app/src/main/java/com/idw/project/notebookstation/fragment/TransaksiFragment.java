package com.idw.project.notebookstation.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.MenungguKonfirmasiActivity;
import com.idw.project.notebookstation.activity.MenungguPembayaranActivity;
import com.idw.project.notebookstation.activity.PesananDiprosesActivity;
import com.idw.project.notebookstation.activity.SampaiTujuanActivity;
import com.idw.project.notebookstation.activity.SedangDikirimActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransaksiFragment extends Fragment {
    private LinearLayout ll_menunggu_pembayaran, ll_menunggu_konfirmasi, ll_pesanan_diproses, ll_sedang_dikirim, ll_sampai_tujuan;


    public TransaksiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        ll_menunggu_pembayaran = view.findViewById(R.id.ll_menunggu_pembayaran);
        ll_menunggu_konfirmasi = view.findViewById(R.id.ll_menunggu_konfirmasi);
        ll_pesanan_diproses = view.findViewById(R.id.ll_pesanan_diproses);
        ll_sedang_dikirim = view.findViewById(R.id.ll_sedang_dikirim);
        ll_sampai_tujuan = view.findViewById(R.id.ll_sampai_tujuan);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_menunggu_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenungguPembayaranActivity.class);
                startActivity(intent);
            }
        });

        ll_menunggu_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenungguKonfirmasiActivity.class);
                startActivity(intent);

            }
        });

        ll_pesanan_diproses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PesananDiprosesActivity.class);
                startActivity(intent);
            }
        });

        ll_sedang_dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SedangDikirimActivity.class);
                startActivity(intent);
            }
        });

        ll_sampai_tujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SampaiTujuanActivity.class);
                startActivity(intent);
            }
        });


    }
}
