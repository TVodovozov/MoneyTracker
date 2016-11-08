package com.loftschool.moneytracker.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.backgroundTasks.CheckStatusBackground;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;

@EActivity(R.layout.splash_activity)
public class SplashScreenActivity extends AppCompatActivity {

    @NonConfigurationInstance
    @Bean
    CheckStatusBackground taskBackground;

    @AfterViews
    void ready() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((MoneyTrackerApplication.getGoogleAuthToken().equals("")) && (MoneyTrackerApplication.getAuthToken().equals(""))) {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity_.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                } else {
                    taskBackground.checkGoogleTokenStatus();
                    taskBackground.checkUserCategories();
                    taskBackground.checkUserExpenses();
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity_.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                }
                SplashScreenActivity.this.finish();
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
