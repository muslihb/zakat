package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("nomortelepon")
	private String nomortelepon;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("username")
	private String username;

	@SerializedName("status")
	private String status;

	@SerializedName("iduser")
	private String iduser;

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNomortelepon(String nomortelepon){
		this.nomortelepon = nomortelepon;
	}

	public String getNomortelepon(){
		return nomortelepon;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}