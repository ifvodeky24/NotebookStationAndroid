package com.idw.project.notebookstation.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.Konsumen;

public class KonsumenDetailResponse {

    @SerializedName("master")
    @Expose
    private List<Konsumen> master = null;

    public List<Konsumen> getMaster() {
        return master;
    }

    public void setMaster(List<Konsumen> master) {
        this.master = master;
    }

}