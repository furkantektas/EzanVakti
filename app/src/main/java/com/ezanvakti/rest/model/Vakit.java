package com.ezanvakti.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Furkan Tektas on 1/31/15.
 */
public class Vakit {
    @SerializedName("Imsak")
    private String imsak;

    @SerializedName("Gunes")
    private String gunes;

    @SerializedName("Ogle")
    private String ogle;

    @SerializedName("Ikindi")
    private String ikindi;

    @SerializedName("Aksam")
    private String aksam;

    @SerializedName("Yatsi")
    private String yatsi;

    @SerializedName("KibleSaati")
    private String kible;

    @SerializedName("GunesDogus")
    private String gunDogumu;

    @SerializedName("GunesBatis")
    private String gunBatimi;

    @SerializedName("AyinSekliURL")
    private String aySekliURL;

    @SerializedName("HicriTarihKisa")
    private String hicriShort;

    @SerializedName("HicriTarihUzun")
    private String hicriLong;

    @SerializedName("MiladiTarihKisa")
    private String miladiShort;

    @SerializedName("MiladiTarihUzun")
    private String miladiLong;

    public String getImsak() {
        return imsak;
    }

    public void setImsak(String imsak) {
        this.imsak = imsak;
    }

    public String getGunes() {
        return gunes;
    }

    public void setGunes(String gunes) {
        this.gunes = gunes;
    }

    public String getOgle() {
        return ogle;
    }

    public void setOgle(String ogle) {
        this.ogle = ogle;
    }

    public String getIkindi() {
        return ikindi;
    }

    public void setIkindi(String ikindi) {
        this.ikindi = ikindi;
    }

    public String getAksam() {
        return aksam;
    }

    public void setAksam(String aksam) {
        this.aksam = aksam;
    }

    public String getYatsi() {
        return yatsi;
    }

    public void setYatsi(String yatsi) {
        this.yatsi = yatsi;
    }

    public String getKible() {
        return kible;
    }

    public void setKible(String kible) {
        this.kible = kible;
    }

    public String getGunDogumu() {
        return gunDogumu;
    }

    public void setGunDogumu(String gunDogumu) {
        this.gunDogumu = gunDogumu;
    }

    public String getGunBatimi() {
        return gunBatimi;
    }

    public void setGunBatimi(String gunBatimi) {
        this.gunBatimi = gunBatimi;
    }

    public String getAySekliURL() {
        return aySekliURL;
    }

    public void setAySekliURL(String aySekliURL) {
        this.aySekliURL = aySekliURL;
    }

    public String getHicriShort() {
        return hicriShort;
    }

    public void setHicriShort(String hicriShort) {
        this.hicriShort = hicriShort;
    }

    public String getHicriLong() {
        return hicriLong;
    }

    public void setHicriLong(String hicriLong) {
        this.hicriLong = hicriLong;
    }

    public String getMiladiShort() {
        return miladiShort;
    }

    public void setMiladiShort(String miladiShort) {
        this.miladiShort = miladiShort;
    }

    public String getMiladiLong() {
        return miladiLong;
    }

    public void setMiladiLong(String miladiLong) {
        this.miladiLong = miladiLong;
    }
}
