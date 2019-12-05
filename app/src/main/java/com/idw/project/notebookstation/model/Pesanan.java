package com.idw.project.notebookstation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pesanan {

    @SerializedName("id_pesanan")
    @Expose
    private String idPesanan;
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

    public String getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
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

}
