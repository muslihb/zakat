package com.example.zakat.model;

import com.google.gson.annotations.SerializedName;

public class PremisResponse {

	@SerializedName("kodepremis")
	private String kodepremis;

	@SerializedName("nama")
	private String nama;

	@SerializedName("quest")
	private String quest;

	@SerializedName("ifyes")
	private String ifyes;

	@SerializedName("ifno")
	private String ifno;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKodepremis(String kodepremis){
		this.kodepremis = kodepremis;
	}

	public String getKodepremis(){
		return kodepremis;
	}

	public void setQuest(String quest){
		this.quest = quest;
	}

	public String getQuest(){
		return quest;
	}

	public void setIfyes(String ifyes){
		this.ifyes = ifyes;
	}

	public String getIfyes(){
		return ifyes;
	}

	public void setIfno(String ifno){
		this.ifno = ifno;
	}

	public String getIfno(){
		return ifno;
	}
}