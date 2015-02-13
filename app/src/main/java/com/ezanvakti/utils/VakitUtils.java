package com.ezanvakti.utils;

import com.ezanvakti.db.model.Vakit;

import java.util.Date;

/**
 * Created by Furkan Tektas on 2/2/15.
 */
public class VakitUtils {
    /**
     * Returns the index of next vakit
     * @param v
     * @return index of next vakit, 0 after yatsi/ishaa.
     */
    public static int getNextVakit(Vakit v) {
        Date now = new Date();
        for(int i = 0; i < 6; ++i) {
            if(now.before(v.getVakit(i)))
                return i;
        }
        return 0;
    }

    /**
     * Returns the index of current vakit
     * @param v
     * @return index of vakit
     */
    public static int getCurrentVakit(Vakit v) {
        Date now = new Date();
        for(int i = 5; i >= 0; --i) {
            if(!now.before(v.getVakit(i)))
                return i+1;
        }
        return 5;
    }
}
