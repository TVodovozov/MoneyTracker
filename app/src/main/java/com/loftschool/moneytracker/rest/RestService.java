package com.loftschool.moneytracker.rest;


import android.support.annotation.NonNull;

import com.loftschool.moneytracker.rest.models.Category.AddCategoryModel;
import com.loftschool.moneytracker.rest.models.Category.UserSyncCategoriesModel;
import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.Expenses.UserSyncExpensesModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
import com.loftschool.moneytracker.rest.models.UserLoginModel;
import com.loftschool.moneytracker.rest.models.UserLogoutModel;
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

    public UserLogoutModel logoutUser() throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .logout()
                .execute()
                .body();
    }

    public CheckGoogleTokenModel checkGoogleToken(@NonNull String token) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .checkGoogleToken(token)
                .execute()
                .body();
    }

    public GoogleLoginUserModel googleLoginUser(@NonNull String token) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .getUser(token)
                .execute()
                .body();
    }

    public UserSyncCategoriesModel syncCategories(@NonNull String data,
                                                  @NonNull String token,
                                                  @NonNull String googleToken) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .syncCategories(data, token, googleToken)
                .execute()
                .body();

    }

    public UserSyncExpensesModel syncExpenses(@NonNull String data,
                                              @NonNull String token,
                                              @NonNull String googleToken) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .syncExpenses(data, token, googleToken)
                .execute()
                .body();

    }

    public UserSyncCategoriesModel getCategories(@NonNull String token,
                                                 @NonNull String googleToken) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .getCategories(token, googleToken)
                .execute()
                .body();

    }

    public AddCategoryModel addCategory(@NonNull String title,
                                        @NonNull String token,
                                        @NonNull String googleToken) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .addCategory(title, token, googleToken)
                .execute()
                .body();

    }
}
