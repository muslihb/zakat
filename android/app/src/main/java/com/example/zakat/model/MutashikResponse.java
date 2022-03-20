package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class MutashikResponse {
    @SerializedName("idmutashik")
    private String idmutashik;

    @SerializedName("idamil")
    private String idamil;

    @SerializedName("jenis_zakat")
    private String jenis_zakat;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("namamutashik")
    private String namamutashik;

    @SerializedName("golonganmutashik")
    private String golonganmutashik;

    public String getIdmutashik() {
        return idmutashik;
    }

    public void setIdmutashik(String idmutashik) {
        this.idmutashik = idmutashik;
    }

    public String getIdamil() {
        return idamil;
    }

    public void setIdamil(String idamil) {
        this.idamil = idamil;
    }

    public String getJenis_zakat() {
        return jenis_zakat;
    }

    public void setJenis_zakat(String jenis_zakat) {
        this.jenis_zakat = jenis_zakat;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getNamamutashik() {
        return namamutashik;
    }

    public void setNamamutashik(String namamutashik) {
        this.namamutashik = namamutashik;
    }

    public String getGolonganmutashik() {
        return golonganmutashik;
    }

    public void setGolonganmutashik(String golonganmutashik) {
        this.golonganmutashik = golonganmutashik;
    }
}
