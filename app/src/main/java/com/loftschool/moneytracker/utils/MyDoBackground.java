package com.loftschool.moneytracker.utils;


import android.util.Log;

import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
import com.loftschool.moneytracker.ui.MoneyTrackerApplication;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.IOException;

@EBean
public class MyDoBackground {

    private final static String LOG_TAG = MyDoBackground.class.getSimpleName();
    RestService restService = new RestService();

    @Background
    public void checkGoogleTokenStatus() {
        try {
            String token = MoneyTrackerApplication.getGoogleAuthToken();
            CheckGoogleTokenModel checkGoogleTokenModel = restService.checkGoogleToken(token);
            Log.d(LOG_TAG, "Status: " + checkGoogleTokenModel.getStatus());
            Log.d(LOG_TAG, "Token" + token);
            Log.d(LOG_TAG, "Отработал1");
            if (checkGoogleTokenModel.getStatus().equals(ConstantsManager.STATUS_SUCCEED)) {
                GoogleLoginUserModel googleLoginUserModel = restService.googleLoginUser(token);
                MoneyTrackerApplication.saveEmail(googleLoginUserModel.getEmail());
                MoneyTrackerApplication.saveName(googleLoginUserModel.getName());
                MoneyTrackerApplication.savePicture(googleLoginUserModel.getPicture());
                Log.d(LOG_TAG, "Отработал2");
            }
        } catch (IOException e) {
            e.printStackTrace();
            //showError();
        }
    }
}
