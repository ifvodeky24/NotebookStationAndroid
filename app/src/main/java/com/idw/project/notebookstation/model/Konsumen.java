package com.idw.project.notebookstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Konsumen implements Parcelable {

    @SerializedName("id_konsumen")
    @Expose
    private Integer idKonsumen;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nama_lengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("nomor_hp")
    @Expose
    private Integer nomorHp;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("foto")
    @Expose
    private String foto;

    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(Integer nomorHp) {
        this.nomorHp = nomorHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idKonsumen);
        dest.writeString(this.username);
        dest.writeString(this.namaLengkap);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeValue(this.nomorHp);
        dest.writeString(this.alamat);
        dest.writeString(this.foto);
    }

    public Konsumen() {
    }

    protected Konsumen(Parcel in) {
        this.idKonsumen = (Integer) in.readValue(Integer.class.getClassLoader());
        this.username = in.readString();
        this.namaLengkap = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.nomorHp = (Integer) in.readValue(Integer.class.getClassLoader());
        this.alamat = in.readString();
        this.foto = in.readString();
    }

    public static final Parcelable.Creator<Konsumen> CREATOR = new Parcelable.Creator<Konsumen>() {
        @Override
        public Konsumen createFromParcel(Parcel source) {
            return new Konsumen(source);
        }

        @Override
        public Konsumen[] newArray(int size) {
            return new Konsumen[size];
        }
    };
}