package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class KonklusiResponse {

    @SerializedName("kodekonklusi")
    private String kodekonklusi;

    @SerializedName("nama")
    private String nama;

    public String getKodekonklusi() {
        return kodekonklusi;
    }

    public void setKodekonklusi(String kodekonklusi) {
        this.kodekonklusi = kodekonklusi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
