package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PesananKhusus implements Parcelable {

    @SerializedName("id_pesanan_khusus")
    @Expose
    private String idPesananKhusus;
    @SerializedName("kode_pesanan")
    @Expose
    private String kodePesanan;
    @SerializedName("tanggal_pesanan")
    @Expose
    private String tanggalPesanan;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("total_tagihan")
    @Expose
    private String totalTagihan;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("catatan_opsional")
    @Expose
    private String catatanOpsional;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("id_produk")
    @Expose
    private String idProduk;
    @SerializedName("nama_lengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nomor_hp")
    @Expose
    private String nomorHp;

    public String getIdPesananKhusus() {
        return idPesananKhusus;
    }

    public void setIdPesananKhusus(String idPesananKhusus) {
        this.idPesananKhusus = idPesananKhusus;
    }

    public String getKodePesanan() {
        return kodePesanan;
    }

    public void setKodePesanan(String kodePesanan) {
        this.kodePesanan = kodePesanan;
    }

    public String getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(String tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(String totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCatatanOpsional() {
        return catatanOpsional;
    }

    public void setCatatanOpsional(String catatanOpsional) {
        this.catatanOpsional = catatanOpsional;
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

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        dest.writeString(this.idPesananKhusus);
        dest.writeString(this.kodePesanan);
        dest.writeString(this.tanggalPesanan);
        dest.writeString(this.jumlah);
        dest.writeString(this.totalTagihan);
        dest.writeString(this.alamatLengkap);
        dest.writeString(this.status);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.catatanOpsional);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.idProduk);
        dest.writeString(this.namaLengkap);
        dest.writeString(this.email);
        dest.writeString(this.nomorHp);
    }

    public PesananKhusus() {
    }

    protected PesananKhusus(Parcel in) {
        this.idPesananKhusus = in.readString();
        this.kodePesanan = in.readString();
        this.tanggalPesanan = in.readString();
        this.jumlah = in.readString();
        this.totalTagihan = in.readString();
        this.alamatLengkap = in.readString();
        this.status = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.catatanOpsional = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.idProduk = in.readString();
        this.namaLengkap = in.readString();
        this.email = in.readString();
        this.nomorHp = in.readString();
    }

    public static final Parcelable.Creator<PesananKhusus> CREATOR = new Parcelable.Creator<PesananKhusus>() {
        @Override
        public PesananKhusus createFromParcel(Parcel source) {
            return new PesananKhusus(source);
        }

        @Override
        public PesananKhusus[] newArray(int size) {
            return new PesananKhusus[size];
        }
    };
}
