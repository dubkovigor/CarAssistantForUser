package com.example.carassistantforuserfragments.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService1 {
    private static NetworkService1 mInstance;
    private static final String BASE_URL = "https://maps.googleapis.com";
    private Retrofit mRetrofit;


    private NetworkService1() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static NetworkService1 getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService1();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}