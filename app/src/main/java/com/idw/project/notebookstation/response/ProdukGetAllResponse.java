package com.idw.project.notebookstation.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.Produk;

import java.util.List;

public class ProdukGetAllResponse {

    @SerializedName("master")
    @Expose
    private List<Produk> master = null;

    public List<Produk> getMaster() {
        return master;
    }

    public void setMaster(List<Produk> master) {
        this.master = master;
    }

}

