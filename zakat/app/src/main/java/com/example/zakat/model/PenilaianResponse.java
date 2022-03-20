package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class PenilaianResponse {
    @SerializedName("tbpenilaian..id_nilai_amil")
    private String tbpenilaianid_nilai_amil;

    @SerializedName("tbpenilaian.id_request")
    private String 	tbpenilaianid_request;

    @SerializedName("tbpenilaian.nilai_cf")
    private String tbpenilaiannilai_cf;

    @SerializedName("id_request")
    private String 	id_request;

    @SerializedName("nilai_cf")
    private String nilai_cf;

    public String getId_request() {
        return id_request;
    }

    public void setId_request(String id_request) {
        this.id_request = id_request;
    }

    public String getNilai_cf() {
        return nilai_cf;
    }

    public void setNilai_cf(String nilai_cf) {
        this.nilai_cf = nilai_cf;
    }

    @SerializedName("tbmaster.nama")
    private String tbmasternama;

    public String getTbpenilaianid_nilai_amil() {
        return tbpenilaianid_nilai_amil;
    }

    public void setTbpenilaianid_nilai_amil(String tbpenilaianid_nilai_amil) {
        this.tbpenilaianid_nilai_amil = tbpenilaianid_nilai_amil;
    }

    public String getTbpenilaianid_request() {
        return tbpenilaianid_request;
    }

    public void setTbpenilaianid_request(String tbpenilaianid_request) {
        this.tbpenilaianid_request = tbpenilaianid_request;
    }

    public String getTbpenilaiannilai_cf() {
        return tbpenilaiannilai_cf;
    }

    public void setTbpenilaiannilai_cf(String tbpenilaiannilai_cf) {
        this.tbpenilaiannilai_cf = tbpenilaiannilai_cf;
    }

    public String getTbmasternama() {
        return tbmasternama;
    }

    public void setTbmasternama(String tbmasternama) {
        this.tbmasternama = tbmasternama;
    }
}
