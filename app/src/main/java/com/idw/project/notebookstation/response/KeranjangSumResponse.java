package com.idw.project.notebookstation.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.KeranjangSum;

public class KeranjangSumResponse {

    @SerializedName("master")
    @Expose
    private List<KeranjangSum> master = null;

    public List<KeranjangSum> getMaster() {
        return master;
    }

    public void setMaster(List<KeranjangSum> master) {
        this.master = master;
    }

}
