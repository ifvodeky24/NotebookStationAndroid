package com.idw.project.notebookstation.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.WishlistCheck;

public class WishlistCheckResponse {

    @SerializedName("master")
    @Expose
    private List<WishlistCheck> master = null;

    public List<WishlistCheck> getMaster() {
        return master;
    }

    public void setMaster(List<WishlistCheck> master) {
        this.master = master;
    }

}
