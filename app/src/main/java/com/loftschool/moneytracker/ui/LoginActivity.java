package com.loftschool.moneytracker.ui;


import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.SignInButton;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.rest.NetworkStatusChecker;
import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
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
    private static final int REQUEST_CODE = 200;
    RestService restService = new RestService();

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
    @ViewById(R.id.sign_in_button)
    SignInButton signInButton;

    @Click(R.id.login_btn_canсel)
    void onLoginCancelClick() {
        finish();
    }

    @Click(R.id.login_btn_registration)
    void loginUser() {

        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            String login = enterLogin.getText().toString();
            String password = enterPassword.getText().toString();
            if (!TextUtils.isEmpty(login) & !TextUtils.isEmpty(password)) {
                if (login.length() >= MIN_LENGTH & password.length() >= MIN_LENGTH) {
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

    @Click(R.id.sign_in_button)
    void loginGoogleWithPlus() {
        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                    false, null, null, null, null);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Snackbar.make(loginLayout,
                    getString(R.string.registration_internet_error),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            logInWithGoogle(data);
        }
    }

    @Background
    public void logInWithGoogle(Intent data) {
        String token = null;
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        Account account = new Account(accountName, accountType);
        try {
            token = GoogleAuthUtil.getToken(this, account, ConstantsManager.SCOPES);

        } catch (UserRecoverableAuthException userAuthEx) {
            startActivityForResult(userAuthEx.getIntent(), REQUEST_CODE);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (GoogleAuthException fatalAuthEx) {
            fatalAuthEx.printStackTrace();
        }
        if (token != null) {
            MoneyTrackerApplication.saveGoogleAuthToken(token);
            navigateToMainScreen();
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