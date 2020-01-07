package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keranjang implements Parcelable {

    public static final String TAG ="id_keranjang";

    public static final String TAG_PRODUK ="id_produk";

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
    @SerializedName("id_keranjang")
    @Expose
    private String idKeranjang;
    @SerializedName("id_konsumen")
    @Expose
    private String idKonsumen;
    @SerializedName("jumlah_harga")
    @Expose
    private String jumlahHarga;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("catatan_opsional")
    @Expose
    private String catatan_opsional;

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

    public String getIdKeranjang() {
        return idKeranjang;
    }

    public void setIdKeranjang(String idKeranjang) {
        this.idKeranjang = idKeranjang;
    }

    public String getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(String idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getJumlahHarga() {
        return jumlahHarga;
    }

    public void setJumlahHarga(String jumlahHarga) {
        this.jumlahHarga = jumlahHarga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getCatatan_opsional() {
        return catatan_opsional;
    }

    public void setCatatan_opsional(String catatan_opsional) {
        this.catatan_opsional = catatan_opsional;
    }

    public Keranjang() {
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
        dest.writeString(this.idKeranjang);
        dest.writeString(this.idKonsumen);
        dest.writeString(this.jumlahHarga);
        dest.writeString(this.jumlah);
        dest.writeString(this.catatan_opsional);
    }

    protected Keranjang(Parcel in) {
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
        this.idKeranjang = in.readString();
        this.idKonsumen = in.readString();
        this.jumlahHarga = in.readString();
        this.jumlah = in.readString();
        this.catatan_opsional = in.readString();
    }

    public static final Creator<Keranjang> CREATOR = new Creator<Keranjang>() {
        @Override
        public Keranjang createFromParcel(Parcel source) {
            return new Keranjang(source);
        }

        @Override
        public Keranjang[] newArray(int size) {
            return new Keranjang[size];
        }
    };
}
