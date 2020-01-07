package com.idw.project.notebookstation.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.PesananKhusus;

public class PesananKhususDetailResponse {

    @SerializedName("master")
    @Expose
    private List<PesananKhusus> master = null;

    public List<PesananKhusus> getMaster() {
        return master;
    }

    public void setMaster(List<PesananKhusus> master) {
        this.master = master;
    }

}
