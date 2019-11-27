package com.idw.project.notebookstation.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.KeranjangCount;

public class KeranjangCountResponse {

    @SerializedName("master")
    @Expose
    private List<KeranjangCount> master = null;

    public List<KeranjangCount> getMaster() {
        return master;
    }

    public void setMaster(List<KeranjangCount> master) {
        this.master = master;
    }

}