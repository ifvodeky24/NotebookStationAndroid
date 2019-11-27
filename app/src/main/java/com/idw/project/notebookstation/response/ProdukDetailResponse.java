package com.idw.project.notebookstation.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.ProdukDetail;

import java.util.List;

public class ProdukDetailResponse {

    @SerializedName("master")
    @Expose
    private List<ProdukDetail> master = null;

    public List<ProdukDetail> getMaster() {
        return master;
    }

    public void setMaster(List<ProdukDetail> master) {
        this.master = master;
    }

}