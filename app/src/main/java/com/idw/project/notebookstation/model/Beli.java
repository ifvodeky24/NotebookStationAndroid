package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Beli implements Parcelable {

    @SerializedName("id_pesanan")
    @Expose
    private Integer idPesanan;
    @SerializedName("kode_pesanan")
    @Expose
    private String kodePesanan;
    @SerializedName("id_produk")
    @Expose
    private String idProduk;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("id_detail_pesanan")
    @Expose
    private Integer idDetailPesanan;
    @SerializedName("catatan_opsional")
    @Expose
    private String catatan_opsional;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(Integer idPesanan) {
        this.idPesanan = idPesanan;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getIdDetailPesanan() {
        return idDetailPesanan;
    }

    public void setIdDetailPesanan(Integer idDetailPesanan) {
        this.idDetailPesanan = idDetailPesanan;
    }

    public String getKodePesanan() {
        return kodePesanan;
    }

    public void setKodePesanan(String kodePesanan) {
        this.kodePesanan = kodePesanan;
    }

    public String getCatatan_opsional() {
        return catatan_opsional;
    }

    public void setCatatan_opsional(String catatan_opsional) {
        this.catatan_opsional = catatan_opsional;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idPesanan);
        dest.writeString(this.kodePesanan);
        dest.writeString(this.idProduk);
        dest.writeValue(this.jumlah);
        dest.writeValue(this.idDetailPesanan);
        dest.writeString(this.catatan_opsional);
        dest.writeString(this.alamatLengkap);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Beli() {
    }

    protected Beli(Parcel in) {
        this.idPesanan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.kodePesanan = in.readString();
        this.idProduk = in.readString();
        this.jumlah = (Integer) in.readValue(Integer.class.getClassLoader());
        this.idDetailPesanan = (Integer) in.readValue(Integer.class.getClassLoader());
        this.catatan_opsional = in.readString();
        this.alamatLengkap = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<Beli> CREATOR = new Parcelable.Creator<Beli>() {
        @Override
        public Beli createFromParcel(Parcel source) {
            return new Beli(source);
        }

        @Override
        public Beli[] newArray(int size) {
            return new Beli[size];
        }
    };
}