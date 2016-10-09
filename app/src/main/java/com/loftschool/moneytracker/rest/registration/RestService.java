package com.loftschool.moneytracker.rest.registration;


import android.support.annotation.NonNull;

import com.loftschool.moneytracker.rest.RestClient;

import java.io.IOException;

import retrofit2.Call;

public final class RestService {

    private static final String REGISTER_FLAG = "1";

    private RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public Call<UserRegistrationModel> register(@NonNull String login,
                                                @NonNull String password) {

        return restClient.getRegisterUserAPI()
                .registerUser(login, password, REGISTER_FLAG);
    }


}
