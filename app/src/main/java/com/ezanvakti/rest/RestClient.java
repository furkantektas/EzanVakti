package com.ezanvakti.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Furkan Tektas on 1/31/15.
 */
public class RestClient {
    private static final String BASE_URL = "https://ezanvakti.herokuapp.com";

    private static volatile RestClient mInstance = null;
    private RestAdapter mAdapter = null;
    private APIService mApiService = null;

    private RestClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();
        mAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        mApiService = mAdapter.create(APIService.class);
    }

    public static RestClient getInstance() {
        if (mInstance == null) {
            synchronized (RestClient.class) {
                if(mInstance == null)
                    mInstance = new RestClient();
            }
        }
        return mInstance;
    }

    public static APIService getAPIService() {
        return getInstance().mApiService;
    }

}
