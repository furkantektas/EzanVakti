package com.ezanvakti.utils;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.ezanvakti.db.model.Ilce;
import com.ezanvakti.db.model.Vakit;
import com.ezanvakti.rest.RestClient;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Furkan Tektas on 2/1/15.
 */
public class DBUtils {
    /**
     * Fetches vakits from API for specified ilce/city and saves them to DB.
     * @param ilce ilce/city to get vakits from API.
     */
    public static void fetchAndSaveVakits(com.ezanvakti.rest.model.Ilce ilce, final ProcessStatusListener processListener) {
        final Ilce ilceDB = new Ilce(ilce);
        ilceDB.save();
        RestClient.getAPIService().getVakitler(ilce.getId(),
                new Callback<List<com.ezanvakti.rest.model.Vakit>>() {
                    @Override
                    public void success(List<com.ezanvakti.rest.model.Vakit> vakitler, Response response) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for(com.ezanvakti.rest.model.Vakit v : vakitler) {
                                Vakit vakit = new Vakit(v,ilceDB);
                                vakit.save();
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        }
                        finally {
                            ActiveAndroid.endTransaction();
                        }

                        processListener.onSuccess();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        processListener.onFailure();
                    }
                });
    }
    public static Vakit getVakitOfDays(String date) {
        List<Vakit> vakitList = new Select().from(Vakit.class).where("MiladiTarihKisa = ?", date).limit(1).execute();
        if(vakitList != null && vakitList.size() > 0)
            return vakitList.get(0);
        return null;
    }

    public static Vakit getVakitOfDays(Date d) {
        String today = Vakit.DATE_FORMAT.format(d);
        return getVakitOfDays(today);
    }

    public static Vakit getTodaysVakit() {
        return getVakitOfDays(new Date());
    }

    public static Vakit getTomorrowsVakit() {
        return getVakitOfDays(VakitUtils.getNextDay());
    }

    public static Vakit getTomorrowsVakit(Vakit v) {
        Date date = null;
        try {
            if(v != null)
                date = Vakit.DATE_FORMAT.parse(v.miladiShort);
        } catch (ParseException e) {
            return null;
        }
        if(date == null)
            date = new Date();
        date = VakitUtils.getNextDay(date);
        return getVakitOfDays(date);
    }

    public static interface ProcessStatusListener {
        public void onSuccess();
        public void onFailure();
    }
}
