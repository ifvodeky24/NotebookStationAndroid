package com.idw.project.notebookstation.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.notebookstation.model.PembayaranKhusus;

public class BayarKhususResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private PembayaranKhusus data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PembayaranKhusus getData() {
        return data;
    }

    public void setData(PembayaranKhusus data) {
        this.data = data;
    }

}