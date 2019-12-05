package com.idw.project.notebookstation.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.Keranjang;

public class KeranjangCheckResponse {

    @SerializedName("master")
    @Expose
    private List<Keranjang> master = null;

    public List<Keranjang> getMaster() {
        return master;
    }

    public void setMaster(List<Keranjang> master) {
        this.master = master;
    }

}
