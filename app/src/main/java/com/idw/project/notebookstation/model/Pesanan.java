package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pesanan implements Parcelable {

    @SerializedName("id_pesanan")
    @Expose
    private String idPesanan;
    @SerializedName("kode_pesanan")
    @Expose
    private String kodePesanan;
    @SerializedName("id_konsumen")
    @Expose
    private String idKonsumen;
    @SerializedName("tanggal_pesanan")
    @Expose
    private String tanggalPesanan;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_produk")
    @Expose
    private String idProduk;
    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;
    @SerializedName("merk_produk")
    @Expose
    private String merkProduk;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("foto_1")
    @Expose
    private String foto1;
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
    @SerializedName("total_tagihan")
    @Expose
    private String totalTagihan;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    public String getKodePesanan() {
        return kodePesanan;
    }

    public void setKodePesanan(String kodePesanan) {
        this.kodePesanan = kodePesanan;
    }

    public String getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(String idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(String tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
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

    public String getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(String totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
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


    public Pesanan() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPesanan);
        dest.writeString(this.kodePesanan);
        dest.writeString(this.idKonsumen);
        dest.writeString(this.tanggalPesanan);
        dest.writeString(this.status);
        dest.writeString(this.idProduk);
        dest.writeString(this.namaProduk);
        dest.writeString(this.merkProduk);
        dest.writeString(this.harga);
        dest.writeString(this.foto1);
        dest.writeString(this.catatan_opsional);
        dest.writeString(this.alamatLengkap);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.totalTagihan);
        dest.writeString(this.jumlah);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    protected Pesanan(Parcel in) {
        this.idPesanan = in.readString();
        this.kodePesanan = in.readString();
        this.idKonsumen = in.readString();
        this.tanggalPesanan = in.readString();
        this.status = in.readString();
        this.idProduk = in.readString();
        this.namaProduk = in.readString();
        this.merkProduk = in.readString();
        this.harga = in.readString();
        this.foto1 = in.readString();
        this.catatan_opsional = in.readString();
        this.alamatLengkap = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.totalTagihan = in.readString();
        this.jumlah = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<Pesanan> CREATOR = new Creator<Pesanan>() {
        @Override
        public Pesanan createFromParcel(Parcel source) {
            return new Pesanan(source);
        }

        @Override
        public Pesanan[] newArray(int size) {
            return new Pesanan[size];
        }
    };
}
