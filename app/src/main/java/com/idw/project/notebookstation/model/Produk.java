package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produk implements Parcelable {

    @SerializedName("id_produk")
    @Expose
    private Integer idProduk;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("merk_produk")
    @Expose
    private String merkProduk;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("kondisi")
    @Expose
    private String kondisi;
    @SerializedName("stok")
    @Expose
    private Integer stok;
    @SerializedName("foto_1")
    @Expose
    private String foto1;
    @SerializedName("foto_2")
    @Expose
    private String foto2;
    @SerializedName("foto_3")
    @Expose
    private String foto3;
    @SerializedName("foto_4")
    @Expose
    private String foto4;


    public Integer getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(Integer idProduk) {
        this.idProduk = idProduk;
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

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
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

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
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

    public String getFoto4() {
        return foto4;
    }

    public void setFoto4(String foto4) {
        this.foto4 = foto4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idProduk);
        dest.writeString(this.namaProduk);
        dest.writeString(this.merkProduk);
        dest.writeValue(this.harga);
        dest.writeString(this.deskripsi);
        dest.writeString(this.kondisi);
        dest.writeValue(this.stok);
        dest.writeString(this.foto1);
        dest.writeString(this.foto2);
        dest.writeString(this.foto3);
        dest.writeString(this.foto4);
    }

    public Produk() {
    }

    protected Produk(Parcel in) {
        this.idProduk = (Integer) in.readValue(Integer.class.getClassLoader());
        this.namaProduk = in.readString();
        this.merkProduk = in.readString();
        this.harga = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deskripsi = in.readString();
        this.kondisi = in.readString();
        this.stok = (Integer) in.readValue(Integer.class.getClassLoader());
        this.foto1 = in.readString();
        this.foto2 = in.readString();
        this.foto3 = in.readString();
        this.foto4 = in.readString();
    }

    public static final Parcelable.Creator<Produk> CREATOR = new Parcelable.Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel source) {
            return new Produk(source);
        }

        @Override
        public Produk[] newArray(int size) {
            return new Produk[size];
        }
    };
}
