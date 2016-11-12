package com.loftschool.moneytracker.ui;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.BuildConfig;

import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.rest.models.UserLogoutModel;
import com.loftschool.moneytracker.utils.ConstantsManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;

@EBean
public class LogoutUser extends AppCompatActivity {

    @Background
    void userLogout() {
        RestService restService = new RestService();
        try {
            UserLogoutModel userLogoutModel = restService.logoutUser();
            if (userLogoutModel.getStatus().equals(ConstantsManager.STATUS_SUCCEED)) {
                navigateToRegistrationScreen();
            }

        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }

        }
    }

    @UiThread
    void navigateToRegistrationScreen() {
        startActivity(new Intent(this, RegistrationActivity_.class));
    }

    @UiThread
    void showUnknownError() {
        /*Snackbar.make(loginLayout,
                getString(R.string.registration_server_other_error),
                Snackbar.LENGTH_SHORT).show();*/
    }
}