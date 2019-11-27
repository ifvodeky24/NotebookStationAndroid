package com.idw.project.notebookstation.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.model.Wishlist;

public class WishlistDetailResponse {

    @SerializedName("master")
    @Expose
    private List<Wishlist> master = null;

    public List<Wishlist> getMaster() {
        return master;
    }

    public void setMaster(List<Wishlist> master) {
        this.master = master;
    }

}
