package com.ezanvakti.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Furkan Tektas on 1/31/15.
 */
public class Sehir {

    @SerializedName("SehirAdi")
    private String name;

    @SerializedName("SehirAdiEn")
    private String nameEng;

    @SerializedName("SehirID")
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
