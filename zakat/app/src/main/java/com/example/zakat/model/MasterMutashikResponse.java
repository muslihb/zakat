package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class MasterMutashikResponse {

    @SerializedName("tbmutashik.idmutashik")
    private String tbmutashikidmutashik;

    @SerializedName("tbmutashik.idamil")
    private String tbmutashikidamil;

    @SerializedName("tbmutashik.jenis_zakat")
    private String tbmutashikjenis_zakat;

    @SerializedName("tbmutashik.jumlah")
    private String tbmutashikjumlah;

    @SerializedName("tbmutashik.namamutashik")
    private String tbmutashiknamamutashik;

    @SerializedName("tbmutashik.golonganmutashik")
    private String tbmutashikgolonganmutashik;

    @SerializedName("golonganmutashik")
    private String golonganmutashik;

    @SerializedName("tbmutashik.tglmenerima")
    private String tbmutashiktglmenerima;

    public String getTbmutashiktglmenerima() {
        return tbmutashiktglmenerima;
    }

    public void setTbmutashiktglmenerima(String tbmutashiktglmenerima) {
        this.tbmutashiktglmenerima = tbmutashiktglmenerima;
    }

    public String getGolonganmutashik() {
        return golonganmutashik;
    }

    public void setGolonganmutashik(String golonganmutashik) {
        this.golonganmutashik = golonganmutashik;
    }

    @SerializedName("tbmaster.iduser")
    private String tbmasteriduser;

    @SerializedName("tbmaster.username")
    private String tbmasterusername;

    @SerializedName("tbmaster.nama")
    private String tbmasternama;

    @SerializedName("tbmaster.password")
    private String tbmasterpassword;

    @SerializedName("tbmaster.nomortelepon")
    private String tbmasternomortelepon;

    @SerializedName("tbmaster.alamat")
    private String tbmasteralamat;

    @SerializedName("tbmaster.status")
    private String tbmasterstatus;

    public String getTbmutashikidmutashik() {
        return tbmutashikidmutashik;
    }

    public void setTbmutashikidmutashik(String tbmutashikidmutashik) {
        this.tbmutashikidmutashik = tbmutashikidmutashik;
    }

    public String getTbmutashikidamil() {
        return tbmutashikidamil;
    }

    public void setTbmutashikidamil(String tbmutashikidamil) {
        this.tbmutashikidamil = tbmutashikidamil;
    }

    public String getTbmutashikjenis_zakat() {
        return tbmutashikjenis_zakat;
    }

    public void setTbmutashikjenis_zakat(String tbmutashikjenis_zakat) {
        this.tbmutashikjenis_zakat = tbmutashikjenis_zakat;
    }

    public String getTbmutashikjumlah() {
        return tbmutashikjumlah;
    }

    public void setTbmutashikjumlah(String tbmutashikjumlah) {
        this.tbmutashikjumlah = tbmutashikjumlah;
    }

    public String getTbmutashiknamamutashik() {
        return tbmutashiknamamutashik;
    }

    public void setTbmutashiknamamutashik(String tbmutashiknamamutashik) {
        this.tbmutashiknamamutashik = tbmutashiknamamutashik;
    }

    public String getTbmutashikgolonganmutashik() {
        return tbmutashikgolonganmutashik;
    }

    public void setTbmutashikgolonganmutashik(String tbmutashikgolonganmutashik) {
        this.tbmutashikgolonganmutashik = tbmutashikgolonganmutashik;
    }

    public String getTbmasteriduser() {
        return tbmasteriduser;
    }

    public void setTbmasteriduser(String tbmasteriduser) {
        this.tbmasteriduser = tbmasteriduser;
    }

    public String getTbmasterusername() {
        return tbmasterusername;
    }

    public void setTbmasterusername(String tbmasterusername) {
        this.tbmasterusername = tbmasterusername;
    }

    public String getTbmasternama() {
        return tbmasternama;
    }

    public void setTbmasternama(String tbmasternama) {
        this.tbmasternama = tbmasternama;
    }

    public String getTbmasterpassword() {
        return tbmasterpassword;
    }

    public void setTbmasterpassword(String tbmasterpassword) {
        this.tbmasterpassword = tbmasterpassword;
    }

    public String getTbmasternomortelepon() {
        return tbmasternomortelepon;
    }

    public void setTbmasternomortelepon(String tbmasternomortelepon) {
        this.tbmasternomortelepon = tbmasternomortelepon;
    }

    public String getTbmasteralamat() {
        return tbmasteralamat;
    }

    public void setTbmasteralamat(String tbmasteralamat) {
        this.tbmasteralamat = tbmasteralamat;
    }

    public String getTbmasterstatus() {
        return tbmasterstatus;
    }

    public void setTbmasterstatus(String tbmasterstatus) {
        this.tbmasterstatus = tbmasterstatus;
    }
}
