package com.idw.project.notebookstation.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.Pesanan;

import java.util.List;

public class BatalResponse {
    @SerializedName("master")
    @Expose
    private List<Pesanan> master = null;

    public List<Pesanan> getMaster() {
        return master;
    }

    public void setMaster(List<Pesanan> master) {
        this.master = master;
    }
}
