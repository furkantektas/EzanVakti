package com.ezanvakti.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Furkan Tektas on 2/1/15.
 */
@Table(name = "Ilceler")
public class Ilce extends Model {

    @Column(name = "IlceAdi")
    public String name;

    @Column(name = "IlceAdiEn")
    public String nameEng;

    @Column(name = "IlceID")
    public String id;

    public Ilce(){
        super();
    }
    public Ilce(com.ezanvakti.rest.model.Ilce ilce){
        super();
        this.id = ilce.getId();
        this.name = ilce.getName();
        this.nameEng = ilce.getNameEng();
    }

    public List<Vakit> items() {
        return getMany(Vakit.class, "Ilce");
    }
}
