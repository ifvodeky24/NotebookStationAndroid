package com.idw.project.notebookstation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PembayaranKhusus {

    @SerializedName("id_pesanan_khusus")
    @Expose
    private String idPesananKhusus;
    @SerializedName("bukti_transfer")
    @Expose
    private String buktiTransfer;
    @SerializedName("jumlah_transfer")
    @Expose
    private String jumlahTransfer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_pembayaran_khusus")
    @Expose
    private Integer idPembayaranKhusus;

    public String getIdPesananKhusus() {
        return idPesananKhusus;
    }

    public void setIdPesananKhusus(String idPesananKhusus) {
        this.idPesananKhusus = idPesananKhusus;
    }

    public String getBuktiTransfer() {
        return buktiTransfer;
    }

    public void setBuktiTransfer(String buktiTransfer) {
        this.buktiTransfer = buktiTransfer;
    }

    public String getJumlahTransfer() {
        return jumlahTransfer;
    }

    public void setJumlahTransfer(String jumlahTransfer) {
        this.jumlahTransfer = jumlahTransfer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdPembayaranKhusus() {
        return idPembayaranKhusus;
    }

    public void setIdPembayaranKhusus(Integer idPembayaranKhusus) {
        this.idPembayaranKhusus = idPembayaranKhusus;
    }

}
