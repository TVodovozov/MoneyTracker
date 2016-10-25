package com.loftschool.moneytracker.ui;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.loftschool.moneytracker.utils.ConstantsManager;


public class MoneyTrackerApplication extends Application {

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        sharedPreferences = getSharedPreferences(ConstantsManager.SHARE_PREF_FILE_NAME, MODE_PRIVATE);
    }

    public static void saveAuthToken(String token) {
        sharedPreferences
                .edit()
                .putString(ConstantsManager.TOKEN_KEY, token)
                .apply();
    }

    public static String getAuthToken() {
        return sharedPreferences.getString(ConstantsManager.TOKEN_KEY, "");
    }
}

