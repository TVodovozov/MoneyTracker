package com.loftschool.moneytracker.ui;


import android.app.Application;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;
import com.facebook.stetho.Stetho;
import com.loftschool.moneytracker.utils.ConstantsManager;


public class MoneyTrackerApplication extends Application {

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Stetho.initializeWithDefaults(this);

        sharedPreferences = getSharedPreferences(ConstantsManager.SHARE_PREF_FILE_NAME, MODE_PRIVATE);
    }

    public static void saveAuthToken(String token) {
        sharedPreferences
                .edit()
                .putString(ConstantsManager.TOKEN_KEY, token)
                .apply();
    }

    public static String getAuthToken() {
        return sharedPreferences
                .getString(ConstantsManager.TOKEN_KEY, "");
    }

    public static void saveGoogleAuthToken(String token) {
        sharedPreferences
                .edit()
                .putString(ConstantsManager.GOOGLE_TOKEN_KEY, token)
                .apply();
    }

    public static String getGoogleAuthToken() {
        return sharedPreferences
                .getString(ConstantsManager.GOOGLE_TOKEN_KEY, "");

    }

    public static void saveEmail(String email) {

        sharedPreferences
                .edit()
                .putString(ConstantsManager.EMAIL_KEY, email)
                .apply();
    }

    public static String getEmail() {
        return sharedPreferences
                .getString(ConstantsManager.EMAIL_KEY, "");
    }

    public static void saveName(String name) {

        sharedPreferences
                .edit()
                .putString(ConstantsManager.NAME_KEY, name)
                .apply();
    }

    public static String getName() {
        return sharedPreferences
                .getString(ConstantsManager.NAME_KEY, "");
    }

    public static void savePicture(String picture) {

        sharedPreferences
                .edit()
                .putString(ConstantsManager.PICTURE_KEY, picture)
                .apply();
    }

    public static String getPicture() {
        return sharedPreferences
                .getString(ConstantsManager.PICTURE_KEY, "");
    }
}


