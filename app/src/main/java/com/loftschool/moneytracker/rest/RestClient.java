package com.loftschool.moneytracker.rest;


import com.loftschool.moneytracker.rest.registration.RegisterUserAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru";
    private RegisterUserAPI registerUserAPI;

    public RestClient(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registerUserAPI = retrofit.create(RegisterUserAPI.class);
    }

    public RegisterUserAPI getRegisterUserAPI() {
        return registerUserAPI;
    }
}
