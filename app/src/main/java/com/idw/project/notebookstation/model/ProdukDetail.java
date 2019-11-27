package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdukDetail implements Parcelable {

    @SerializedName("id_produk")
    @Expose
    private String idProduk;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("merk_produk")
    @Expose
    private String merkProduk;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("kondisi")
    @Expose
    private String kondisi;
    @SerializedName("stok")
    @Expose
    private String stok;
    @SerializedName("foto_1")
    @Expose
    private String foto1;
    @SerializedName("foto_2")
    @Expose
    private String foto2;
    @SerializedName("foto_3")
    @Expose
    private String foto3;
    @SerializedName("nama_toko")
    @Expose
    private String namaToko;
    @SerializedName("logo_toko")
    @Expose
    private String logoToko;
    @SerializedName("nomor_hp")
    @Expose
    private String nomorHp;

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getMerkProduk() {
        return merkProduk;
    }

    public void setMerkProduk(String merkProduk) {
        this.merkProduk = merkProduk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getLogoToko() {
        return logoToko;
    }

    public void setLogoToko(String logoToko) {
        this.logoToko = logoToko;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(String nomorHp) {
        this.nomorHp = nomorHp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idProduk);
        dest.writeString(this.idUser);
        dest.writeString(this.namaProduk);
        dest.writeString(this.merkProduk);
        dest.writeString(this.harga);
        dest.writeString(this.deskripsi);
        dest.writeString(this.kondisi);
        dest.writeString(this.stok);
        dest.writeString(this.foto1);
        dest.writeString(this.foto2);
        dest.writeString(this.foto3);
        dest.writeString(this.namaToko);
        dest.writeString(this.logoToko);
        dest.writeString(this.nomorHp);
    }

    public ProdukDetail() {
    }

    protected ProdukDetail(Parcel in) {
        this.idProduk = in.readString();
        this.idUser = in.readString();
        this.namaProduk = in.readString();
        this.merkProduk = in.readString();
        this.harga = in.readString();
        this.deskripsi = in.readString();
        this.kondisi = in.readString();
        this.stok = in.readString();
        this.foto1 = in.readString();
        this.foto2 = in.readString();
        this.foto3 = in.readString();
        this.namaToko = in.readString();
        this.logoToko = in.readString();
        this.nomorHp = in.readString();
    }

    public static final Parcelable.Creator<ProdukDetail> CREATOR = new Parcelable.Creator<ProdukDetail>() {
        @Override
        public ProdukDetail createFromParcel(Parcel source) {
            return new ProdukDetail(source);
        }

        @Override
        public ProdukDetail[] newArray(int size) {
            return new ProdukDetail[size];
        }
    };
}
