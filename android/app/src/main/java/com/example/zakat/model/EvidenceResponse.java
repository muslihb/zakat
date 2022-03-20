package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class EvidenceResponse {
    @SerializedName("kodeevidence")
    private String kodeevidence;

    @SerializedName("mb")
    private String mb;

    @SerializedName("md")
    private String md;

    @SerializedName("nama")
    private String nama;

    @SerializedName("kodehipotesa")
    private String kodehipotesa;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected = false;

    public String getKodeevidence() {
        return kodeevidence;
    }

    public void setKodeevidence(String kodeevidence) {
        this.kodeevidence = kodeevidence;
    }

    public String getMb() {
        return mb;
    }

    public void setMb(String mb) {
        this.mb = mb;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKodehipotesa() {
        return kodehipotesa;
    }

    public void setKodehipotesa(String kodehipotesa) {
        this.kodehipotesa = kodehipotesa;
    }
}
