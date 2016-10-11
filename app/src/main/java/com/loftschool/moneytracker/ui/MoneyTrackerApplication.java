package com.loftschool.moneytracker.ui;


import android.app.Application;

import com.activeandroid.ActiveAndroid;


public class MoneyTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}

