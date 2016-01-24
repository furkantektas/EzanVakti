package com.ezanvakti.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.ezanvakti.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Furkan Tektas on 2/1/15.
 */
@Table(name = "Vakitler")
public class Vakit extends Model {

    @Column(name = "Ilce", index = true)
    public Ilce ilce;

    @Column(name = "MiladiTarihKisa", index = true)
    public String miladiShort;

    @Column(name = "Imsak")
    public Date imsak;

    @Column(name = "Gunes")
    public Date gunes;

    @Column(name = "Ogle")
    public Date ogle;

    @Column(name = "Ikindi")
    public Date ikindi;

    @Column(name = "Aksam")
    public Date aksam;

    @Column(name = "Yatsi")
    public Date yatsi;

    @Column(name = "KibleSaati")
    public Date kible;

    @Column(name = "GunesDogus")
    public Date gunDogumu;

    @Column(name = "GunesBatis")
    public Date gunBatimi;

    @Column(name = "AyinSekliURL")
    public String aySekliURL;

    @Column(name = "HicriTarihKisa")
    public String hicriShort;

    @Column(name = "HicriTarihUzun")
    public String hicriLong;

    @Column(name = "MiladiTarihUzun")
    public String miladiLong;



    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public Vakit(){
        super();
    }

    public Vakit(com.ezanvakti.rest.model.Vakit vakit, Ilce ilce){
        super();
        this.ilce = ilce;
        this.aySekliURL = vakit.getAySekliURL();
        this.hicriShort = vakit.getHicriShort();
        this.hicriLong = vakit.getHicriLong();
        this.miladiShort = vakit.getMiladiShort();
        this.miladiLong = vakit.getMiladiLong();
        try {
            this.imsak     = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getImsak());
            this.gunes     = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getGunes());
            this.ogle      = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getOgle());
            this.ikindi    = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getIkindi());
            this.aksam     = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getAksam());
            this.yatsi     = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getYatsi());
            this.kible     = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getKible());
            this.gunDogumu = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getGunDogumu());
            this.gunBatimi = DATE_TIME_FORMAT.parse(miladiShort + " " + vakit.getGunBatimi());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns vakits from imsak to kible in order.
     * @param pos
     * @return
     */
    public Date getVakit(int pos) {
        switch (pos) {
            case 0:
                return imsak;
            case 1:
                return gunes;
            case 2:
                return ogle;
            case 3:
                return ikindi;
            case 4:
                return aksam;
            case 5:
                return yatsi;
            case 6:
                return kible;
            default:
                return null;
        }
    }

    public static int getVakitStringResource(int pos) {
        switch (pos) {
            case 0:
                return R.string.imsak;
            case 1:
                return R.string.gunes;
            case 2:
                return R.string.ogle;
            case 3:
                return R.string.ikindi;
            case 4:
                return R.string.aksam;
            case 5:
                return R.string.yatsi;
            case 6:
                return R.string.kible;
            default:
                return 0;
        }
    }

    /**
     * Returns a long array containing vakits.
     * It's used to send vakits to wear.
     * @return vakits as long arr
     */
    public long[] getAsLongArray() {
        long[] vakits = new long[9];

        vakits[0] = this.imsak.getTime();
        vakits[1] = this.gunes.getTime();
        vakits[2] = this.ogle.getTime();
        vakits[3] = this.ikindi.getTime();
        vakits[4] = this.aksam.getTime();
        vakits[5] = this.yatsi.getTime();
        vakits[6] = this.kible.getTime();
        vakits[7] = this.gunDogumu.getTime();
        vakits[8] = this.gunBatimi.getTime();

        return vakits;
    }
}
