package com.loftschool.moneytracker.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.loftschool.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash_activity)
public class SplashScreenActivity  extends AppCompatActivity{

    @AfterViews
    void ready() {
        doInBackground();
    }

    @Background(delay=3000)
    void doInBackground() {
        RegistrationActivity_.intent(this).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

