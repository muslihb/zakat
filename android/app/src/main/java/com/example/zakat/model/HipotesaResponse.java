package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class HipotesaResponse {
    @SerializedName("kodehipotesa")
    private String kodehipotesa;

    @SerializedName("nama")
    private String nama;

    public String getKodehipotesa() {
        return kodehipotesa;
    }

    public void setKodehipotesa(String kodehipotesa) {
        this.kodehipotesa = kodehipotesa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
