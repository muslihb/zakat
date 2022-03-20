package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelLogin {
    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    @SerializedName("pesan")
    private String pesan;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private String status;

    @SerializedName("kode")
    private int kode;
}
