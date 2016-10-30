package com.loftschool.moneytracker.rest;

import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
import com.loftschool.moneytracker.rest.models.UserLoginModel;
import com.loftschool.moneytracker.rest.models.UserLogoutModel;
import com.loftschool.moneytracker.rest.models.UserRegistrationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface LoftSchoolAPI {

    @GET("/auth")
    Call<UserRegistrationModel> registerUser(@Query("login") String login,
                                             @Query("password") String password,
                                             @Query("register") String registrationFlag);

    @GET("/auth")
    Call<UserLoginModel> login(@Query("login") String login,
                               @Query("password") String password);

    @GET("/logout")
    Call<UserLogoutModel> logout();

    @GET("/gcheck")
    Call<CheckGoogleTokenModel> checkGoogleToken(@Query("google_token") String token);

    @GET("/gjson")
    Call<GoogleLoginUserModel> getUser(@Query("google_token") String token);
}
