package com.idw.project.notebookstation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeranjangSum {

    @SerializedName("SUM(jumlah_harga)")
    @Expose
    private String sUMJumlahHarga;

    public String getSUMJumlahHarga() {
        return sUMJumlahHarga;
    }

    public void setSUMJumlahHarga(String sUMJumlahHarga) {
        this.sUMJumlahHarga = sUMJumlahHarga;
    }

}
