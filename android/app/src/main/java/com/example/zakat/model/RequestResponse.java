package com.example.zakat.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RequestResponse{

	@SerializedName("tgl_request")
	private String tglRequest;

	@SerializedName("tgl_pengambilan")
	private String tglPengambilan;

	@SerializedName("id_amil")
	private String idAmil;

	@SerializedName("jenis_zakat")
	private String jenisZakat;

	@SerializedName("jeniszakat")
	private String jeniszakat;

	@SerializedName("jml_zakat")
	private String jmlZakat;

	@SerializedName("id_muzakki")
	private String idMuzakki;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("tipe_zakat")
	private String tipeZakat;

	@SerializedName("nishob")
	private String nishob;

	@SerializedName("otp")
	private String otp;

	@SerializedName("id_request")
	private String idRequest;

	@SerializedName("namamuzakki")
	private String namamuzakki;

	@SerializedName("statuspengambilan")
	private String statuspengambilan;

	@SerializedName("total_zakat")
	private String totalZakat;

	@SerializedName("nomortelepon")
	private String nomortelepon;

	public String getTglRequest() {
		return tglRequest;
	}

	public void setTglRequest(String tglRequest) {
		this.tglRequest = tglRequest;
	}

	public String getTglPengambilan() {
		return tglPengambilan;
	}

	public void setTglPengambilan(String tglPengambilan) {
		this.tglPengambilan = tglPengambilan;
	}

	public String getIdAmil() {
		return idAmil;
	}

	public void setIdAmil(String idAmil) {
		this.idAmil = idAmil;
	}

	public String getJenisZakat() {
		return jenisZakat;
	}

	public void setJenisZakat(String jenisZakat) {
		this.jenisZakat = jenisZakat;
	}

	public String getIdMuzakki() {
		return idMuzakki;
	}

	public void setIdMuzakki(String idMuzakki) {
		this.idMuzakki = idMuzakki;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getTipeZakat() {
		return tipeZakat;
	}

	public void setTipeZakat(String tipeZakat) {
		this.tipeZakat = tipeZakat;
	}

	public String getNishob() {
		return nishob;
	}

	public void setNishob(String nishob) {
		this.nishob = nishob;
	}

	public String getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}

	public String getNamamuzakki() {
		return namamuzakki;
	}

	public void setNamamuzakki(String namamuzakki) {
		this.namamuzakki = namamuzakki;
	}

	public String getStatuspengambilan() {
		return statuspengambilan;
	}

	public void setStatuspengambilan(String statuspengambilan) {
		this.statuspengambilan = statuspengambilan;
	}

	public String getTotalZakat() {
		return totalZakat;
	}

	public void setTotalZakat(String totalZakat) {
		this.totalZakat = totalZakat;
	}

	public String getNomortelepon() {
		return nomortelepon;
	}

	public void setNomortelepon(String nomortelepon) {
		this.nomortelepon = nomortelepon;
	}

	public String getJeniszakat() {
		return jeniszakat;
	}

	public void setJeniszakat(String jeniszakat) {
		this.jeniszakat = jeniszakat;
	}

	public String getJmlZakat() {
		return jmlZakat;
	}

	public void setJmlZakat(String jmlZakat) {
		this.jmlZakat = jmlZakat;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
}