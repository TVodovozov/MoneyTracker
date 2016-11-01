package com.loftschool.moneytracker.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.BuildConfig;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.rest.NetworkStatusChecker;
import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.rest.models.UserRegistrationModel;
import com.loftschool.moneytracker.utils.ConstantsManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;


@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends AppCompatActivity {

    private final static String LOG_TAG = RegistrationActivity_.class.getSimpleName();
    private final static int MIN_LENGTH = 5;

    @ViewById(R.id.registration_layout_root)
    RelativeLayout registrationLayout;
    @ViewById(R.id.enter_login)
    EditText enterLogin;
    @ViewById(R.id.text_login)
    TextView textLogin;
    @ViewById(R.id.enter_password)
    EditText enterPassword;
    @ViewById(R.id.text_password)
    TextView textPassword;
    @ViewById(R.id.registration_btn_registration)
    Button btnRegistration;
    @ViewById(R.id.registration_btn_canсel)
    Button btnCanсel;

    @Click(R.id.registration_btn_canсel)
    void onRegistrationCancelClick() {
        finish();
    }

    @Click(R.id.registration_btn_registration)
    void registerUser() {

        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            String login = enterLogin.getText().toString();
            String password = enterPassword.getText().toString();
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                if (login.length() >= MIN_LENGTH && password.length() >= MIN_LENGTH) {
                    register(login, password);
                } else {
                    Snackbar.make(registrationLayout,
                            getString(R.string.registration_text_error),
                            Snackbar.LENGTH_LONG).show();
                }
            } else {
                if (TextUtils.isEmpty(login)) showRegistrationErrorLogin();
                if (TextUtils.isEmpty(password)) showRegistrationErrorPassword();
            }
        } else {
            Snackbar.make(registrationLayout,
                    getString(R.string.registration_internet_error),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Background
    void register(String login, String password) {
        RestService restService = new RestService();
        try {
            UserRegistrationModel registrationModel = restService.register(login, password);
            Log.d(LOG_TAG, "Status: " + registrationModel.getStatus());
            if (registrationModel.getStatus().equals(ConstantsManager.STATUS_SUCCEED)) {
                navigateToMainScreen();
            } else if (registrationModel.getStatus().equals(ConstantsManager.STATUS_LOGIN_BUSY_ALREADY)) {
                showLoginBusy();
            }
        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @UiThread
    void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity_.class));
    }

    @UiThread
    void showLoginBusy() {
        Snackbar.make(registrationLayout,
                getString(R.string.registration_server_status_busy_login_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void showUnknownError() {
        Snackbar.make(registrationLayout,
                getString(R.string.registration_server_other_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void showRegistrationErrorLogin() {
        Snackbar.make(registrationLayout,
                getString(R.string.registration_text_error_login),
                Snackbar.LENGTH_LONG).show();
    }

    @UiThread
    void showRegistrationErrorPassword() {
        Snackbar.make(registrationLayout,
                getString(R.string.registration_text_error_password),
                Snackbar.LENGTH_LONG).show();
    }
}
