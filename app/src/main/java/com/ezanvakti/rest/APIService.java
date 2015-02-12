package com.ezanvakti.rest;

import com.ezanvakti.rest.model.Ilce;
import com.ezanvakti.rest.model.Sehir;
import com.ezanvakti.rest.model.Ulke;
import com.ezanvakti.rest.model.Vakit;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Furkan Tektas on 1/31/15.
 */
public interface APIService {
    @GET("/ulkeler")
    List<Ulke> getUlkeler();

    @GET("/sehirler")
    List<Sehir> getSehirler(@Query("ulke") String ulkeID);

    @GET("/ilceler")
    List<Ilce> getIlceler(@Query("sehir") String sehirID);

    @GET("/vakitler")
    List<Vakit> getVakitler(@Query("ilce") String ilceID);

    // Async requests
    @GET("/ulkeler")
    void getUlkeler(Callback<List<Ulke>> callback);

    @GET("/sehirler")
    void getSehirler(@Query("ulke") String ulkeID,Callback<List<Sehir>> callback);

    @GET("/ilceler")
    void getIlceler(@Query("sehir") String sehirID,Callback<List<Ilce>> callback);

    @GET("/vakitler")
    void getVakitler(@Query("ilce") String ilceID,Callback<List<Vakit>> callback);

}
