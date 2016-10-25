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
import com.loftschool.moneytracker.rest.models.UserLoginModel;
import com.loftschool.moneytracker.rest.models.UserRegistrationModel;
import com.loftschool.moneytracker.utils.ConstantsManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {

    private final static String LOG_TAG = LoginActivity_.class.getSimpleName();
    private final static int MIN_LENGTH = 5;

    @ViewById(R.id.login_layout_root)
    RelativeLayout loginLayout;
    @ViewById(R.id.login_enter_login)
    EditText enterLogin;
    @ViewById(R.id.login_text_login)
    TextView textLogin;
    @ViewById(R.id.login_enter_password)
    EditText enterPassword;
    @ViewById(R.id.login_text_password)
    TextView textPassword;
    @ViewById(R.id.login_btn_registration)
    Button btnRegistration;
    @ViewById(R.id.login_btn_canсel)
    Button btnCanсel;
    @ViewById(R.id.auth_btn_new_registration)
    Button newRegistration;

    @Click(R.id.login_btn_canсel)
    void onLoginCancelClick() {
        finish();
    }

    @Click(R.id.login_btn_registration)
    void loginUser() {

        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            String login = enterLogin.getText().toString();
            String password = enterPassword.getText().toString();
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                if (login.length() >= MIN_LENGTH && password.length() >= MIN_LENGTH) {
                    userLogin(login, password);
                } else {
                    Snackbar.make(loginLayout,
                            getString(R.string.registration_text_error),
                            Snackbar.LENGTH_LONG).show();
                }
            } else {
                if (TextUtils.isEmpty(login))
                    showRegistrationErrorLogin();
                if (TextUtils.isEmpty(password))
                    showRegistrationErrorPassword();
            }
        } else {
            Snackbar.make(loginLayout,
                    getString(R.string.registration_internet_error),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.auth_btn_new_registration)
    void newRegistrationUser() {
        navigateToRegistrationScreen();
    }

    @Background
    void userLogin(String login, String password) {
        RestService restService = new RestService();
        try {
            UserLoginModel userLoginModel = restService.login(login, password);
            Log.d(LOG_TAG, "Status: " + userLoginModel.getStatus());
            if (userLoginModel.getStatus().equals(ConstantsManager.STATUS_SUCCEED)) {
                MoneyTrackerApplication.saveAuthToken(userLoginModel.getAuthToken());
                navigateToMainScreen();
            } else {
                if (userLoginModel.getStatus().equals(ConstantsManager.STATUS_WRONG_LOGIN))
                    showLoginWrong();
                if (userLoginModel.getStatus().equals(ConstantsManager.STATUS_WRONG_PASSWORD))
                    showPasswordWrong();
                if (userLoginModel.getStatus().equals(ConstantsManager.STATUS_ERROR))
                    showError();
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
    void navigateToRegistrationScreen() {
        startActivity(new Intent(this, RegistrationActivity_.class));
    }

    @UiThread
    void showLoginWrong() {
        Snackbar.make(loginLayout,
                getString(R.string.auth_server_status_wrong_login_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void showPasswordWrong() {
        Snackbar.make(loginLayout,
                getString(R.string.auth_server_status_wrong_password_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void showError() {
        Snackbar.make(loginLayout,
                getString(R.string.auth_server_status_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void showUnknownError() {
        Snackbar.make(loginLayout,
                getString(R.string.registration_server_other_error),
                Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void showRegistrationErrorLogin() {
        Snackbar.make(loginLayout,
                getString(R.string.registration_text_error_login),
                Snackbar.LENGTH_LONG).show();
    }

    @UiThread
    void showRegistrationErrorPassword() {
        Snackbar.make(loginLayout,
                getString(R.string.registration_text_error_password),
                Snackbar.LENGTH_LONG).show();
    }
}