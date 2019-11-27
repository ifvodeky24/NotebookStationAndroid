package com.idw.project.notebookstation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeranjangCount {

    @SerializedName("number")
    @Expose
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
