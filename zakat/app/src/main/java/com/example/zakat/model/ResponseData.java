package com.example.zakat.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

	@SerializedName("kode")
	private int kode;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("datalogin")
	private List<LoginResponse> datalogin;

	@SerializedName("datapremisfc")
	private List<PremisResponse> datapremisfc;

	@SerializedName("datakonklusifc")
	private List<KonklusiResponse> datakonklusifc;

	@SerializedName("datarequest")
	private List<RequestResponse> datarequest;

	@SerializedName("datareq")
	private List<RequestTersediaResponse> datareq;

	@SerializedName("datamutashik")
	private List<MutashikResponse> datamutashik;

	@SerializedName("datamamut")
	private List<MasterMutashikResponse> datamamut;

	@SerializedName("datahipotesacf")
	private List<HipotesaResponse> datahipotesacf;

	@SerializedName("dataevidencecf")
	private List<EvidenceResponse> dataevidencecf;

	@SerializedName("datapenilaian")
	private List<PenilaianResponse> datapenilaian;

	public List<PenilaianResponse> getDatapenilaian() {
		return datapenilaian;
	}

	public void setDatapenilaian(List<PenilaianResponse> datapenilaian) {
		this.datapenilaian = datapenilaian;
	}

	public List<EvidenceResponse> getDataevidencecf() {
		return dataevidencecf;
	}

	public void setDataevidencecf(List<EvidenceResponse> dataevidencecf) {
		this.dataevidencecf = dataevidencecf;
	}

	public List<HipotesaResponse> getDatahipotesacf() {
		return datahipotesacf;
	}

	public void setDatahipotesacf(List<HipotesaResponse> datahipotesacf) {
		this.datahipotesacf = datahipotesacf;
	}

	public List<MutashikResponse> getDatamutashik() {
		return datamutashik;
	}

	public void setDatamutashik(List<MutashikResponse> datamutashik) {
		this.datamutashik = datamutashik;
	}

	public List<MasterMutashikResponse> getDatamamut() {
		return datamamut;
	}

	public void setDatamamut(List<MasterMutashikResponse> datamamut) {
		this.datamamut = datamamut;
	}

	public List<RequestTersediaResponse> getDatareq() {
		return datareq;
	}

	public void setDatareq(List<RequestTersediaResponse> datareq) {
		this.datareq = datareq;
	}

	public List<RequestResponse> getDatarequest() {
		return datarequest;
	}

	public void setDatarequest(List<RequestResponse> datarequest) {
		this.datarequest = datarequest;
	}

	public List<KonklusiResponse> getDatakonklusifc() {
		return datakonklusifc;
	}

	public void setDatakonklusifc(List<KonklusiResponse> datakonklusifc) {
		this.datakonklusifc = datakonklusifc;
	}

	public List<PremisResponse> getDatapremisfc() {
		return datapremisfc;
	}

	public void setDatapremisfc(List<PremisResponse> datapremisfc) {
		this.datapremisfc = datapremisfc;
	}

	public int getKode() {
		return kode;
	}

	public void setKode(int kode) {
		this.kode = kode;
	}

	public String getPesan() {
		return pesan;
	}

	public void setPesan(String pesan) {
		this.pesan = pesan;
	}

	public List<LoginResponse> getDatalogin() {
		return datalogin;
	}

	public void setDatalogin(List<LoginResponse> datalogin) {
		this.datalogin = datalogin;
	}
}