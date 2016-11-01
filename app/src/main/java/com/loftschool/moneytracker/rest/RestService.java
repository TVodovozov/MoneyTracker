package com.loftschool.moneytracker.rest;


import android.support.annotation.NonNull;

import com.loftschool.moneytracker.rest.models.UserLoginModel;
import com.loftschool.moneytracker.rest.models.UserRegistrationModel;

import java.io.IOException;


public final class RestService {

    private static final String REGISTER_FLAG = "1";

    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(@NonNull String login,
                                          @NonNull String password) throws IOException {

        return restClient.getLoftSchoolAPI()
                .registerUser(login, password, REGISTER_FLAG)
                .execute()
                .body();
    }

    public UserLoginModel login(@NonNull String login,
                                @NonNull String password) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .login(login, password)
                .execute()
                .body();
    }


}
