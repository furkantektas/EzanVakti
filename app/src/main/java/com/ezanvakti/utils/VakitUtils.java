package com.ezanvakti.utils;

import com.ezanvakti.db.model.Vakit;

import java.util.Calendar;
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
    public static int getNextVakitIndex(Vakit v) {
        Date now = new Date();
        for(int i = 0; i < 6; ++i) {
            if(now.before(v.getVakit(i)))
                return i;
        }
        return 0;
    }

    /**
     * Returns the next vakit. After ishaa, it returns
     * the time of tomorrow's imsak/fajr
     * @param v
     * @return
     */
    public static Date getNextVakit(Vakit v) {
        Date now = new Date();
        // yatsi/isha has not passed
        if(now.before(v.yatsi)) {
            for (int i = 0; i < 6; ++i) {
                if (now.before(v.getVakit(i)))
                    return v.getVakit(i);
            }
        } else {
            Vakit tomorrow = DBUtils.getTomorrowsVakit(v);
            if(tomorrow != null)
                return tomorrow.imsak;
            else
                return null;
        }
        return null;
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
                return i;
        }
        return 5;
    }

    /**
     * Returns Date object of tomorrow's 00:00
     * @return
     */
    public static Date getNextDay() {
        return getNextDay(new Date());
    }

    /**
     * Returns Date object of the next day of d.
     * @return
     */
    public static Date getNextDay(Date d) {
        Calendar c = Calendar.getInstance();
        if(d != null)
            c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

}
