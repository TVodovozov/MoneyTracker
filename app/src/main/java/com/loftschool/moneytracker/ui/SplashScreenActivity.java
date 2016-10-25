package com.loftschool.moneytracker.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.loftschool.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash_activity)
public class SplashScreenActivity extends AppCompatActivity {

    @AfterViews
    void ready() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MoneyTrackerApplication.getAuthToken().equals("")) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity_.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity_.class));
                    finish();
                }
            }
        }, 3000);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


