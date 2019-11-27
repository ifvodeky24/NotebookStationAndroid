package com.idw.project.notebookstation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistCheck {

    @SerializedName("id_produk")
    @Expose
    private String idProduk;
    @SerializedName("id_konsumen")
    @Expose
    private String idKonsumen;
    @SerializedName("id_wishlist")
    @Expose
    private String idWishlist;

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(String idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getIdWishlist() {
        return idWishlist;
    }

    public void setIdWishlist(String idWishlist) {
        this.idWishlist = idWishlist;
    }

}
