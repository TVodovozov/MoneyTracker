package com.loftschool.moneytracker.rest;

import com.loftschool.moneytracker.rest.models.Category.AddCategoryModel;
import com.loftschool.moneytracker.rest.models.Category.UserSyncCategoriesModel;
import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.Expenses.AddExpensesModel;
import com.loftschool.moneytracker.rest.models.Expenses.UserSyncExpensesModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
import com.loftschool.moneytracker.rest.models.UserLoginModel;
import com.loftschool.moneytracker.rest.models.UserLogoutModel;
import com.loftschool.moneytracker.rest.models.UserRegistrationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("/categories/add")
    Call<AddCategoryModel> addCategory(@Query("title") String title,
                                       @Query("auth_token") String token,
                                       @Query("google_token") String googleToken);

    @GET("transactions/add")
    Call<AddExpensesModel> addExpenses(@Query("sum") String sum,
                                       @Query("comment") String comment,
                                       @Query("category_id") int categoryId,
                                       @Query("tr_date") String date,
                                       @Query("auth_token") String token,
                                       @Query("google_token") String googleToken);


    @POST("/categories/synch")
    Call<UserSyncCategoriesModel> syncCategories(@Query("data") String data,
                                                 @Query("auth_token") String token,
                                                 @Query("google_token") String googleToken);


    @POST("/transactions/synch")
    Call<UserSyncExpensesModel> syncExpenses(@Query("data") String data,
                                             @Query("auth_token") String token,
                                             @Query("google_token") String googleToken);

    @POST("/categories/")
    Call<UserSyncCategoriesModel> postCategories(@Query("auth_token") String token,
                                                 @Query("google_token") String googleToken);

    @POST("/transactions/")
    Call<UserSyncExpensesModel> postExpenses(@Query("auth_token") String token,
                                             @Query("google_token") String googleToken);


}
