package com.ezanvakti.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Furkan Tektas on 1/31/15.
 */
public class Ulke {
    @SerializedName("UlkeAdi")
    private String name;

    @SerializedName("UlkeAdiEn")
    private String nameEng;

    @SerializedName("UlkeID")
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
