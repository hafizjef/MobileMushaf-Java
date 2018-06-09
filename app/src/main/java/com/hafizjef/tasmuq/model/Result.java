package com.hafizjef.tasmuq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("mushafId")
    @Expose
    private Integer mushafId;
    @SerializedName("negara")
    @Expose
    private String negara;
    @SerializedName("penerbit")
    @Expose
    private String penerbit;
    @SerializedName("versi")
    @Expose
    private String versi;
    @SerializedName("tahunPenerbitan")
    @Expose
    private String tahunPenerbitan;
    @SerializedName("tahunDisahkan")
    @Expose
    private String tahunDisahkan;
    @SerializedName("mukaSurat")
    @Expose
    private String mukaSurat;
    @SerializedName("namaFail")
    @Expose
    private String namaFail;
    @SerializedName("direktori")
    @Expose
    private String direktori;

    public Integer getMushafId() {
        return mushafId;
    }

    public void setMushafId(Integer mushafId) {
        this.mushafId = mushafId;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getVersi() {
        return versi;
    }

    public void setVersi(String versi) {
        this.versi = versi;
    }

    public String getTahunPenerbitan() {
        return tahunPenerbitan;
    }

    public void setTahunPenerbitan(String tahunPenerbitan) {
        this.tahunPenerbitan = tahunPenerbitan;
    }

    public String getTahunDisahkan() {
        return tahunDisahkan;
    }

    public void setTahunDisahkan(String tahunDisahkan) {
        this.tahunDisahkan = tahunDisahkan;
    }

    public String getMukaSurat() {
        return mukaSurat;
    }

    public void setMukaSurat(String mukaSurat) {
        this.mukaSurat = mukaSurat;
    }

    public String getNamaFail() {
        return namaFail;
    }

    public void setNamaFail(String namaFail) {
        this.namaFail = namaFail;
    }

    public String getDirektori() {
        return direktori;
    }

    public void setDirektori(String direktori) {
        this.direktori = direktori;
    }

}