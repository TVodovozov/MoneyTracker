package com.loftschool.moneytracker.ui;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.rest.registration.NetworkStatusChecker;
import com.loftschool.moneytracker.rest.registration.RestService;
import com.loftschool.moneytracker.rest.registration.UserRegistrationModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import retrofit2.Call;

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends AppCompatActivity {

    private final static String LOG_TAG = RegistrationActivity_.class.getSimpleName();
    private final static int MIN_LENGTH = 5;
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_LOGIN_BUSY_ALREADY = "Login busy already";

    @ViewById (R.id.registration_layout_root)
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
    void registration(View viewClick) {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(enterLogin.length() < MIN_LENGTH || enterPassword.length() < MIN_LENGTH) {
            Snackbar.make(viewClick, getString(R.string.registration_text_error_login), Snackbar.LENGTH_LONG).show();
            return;
        }
        registerUser(viewClick);
    }

    @Background
    void registerUser(View view) {

        String login = enterLogin.getText().toString();
        String password = enterPassword.getText().toString();

        RestService restService = new RestService();

        if(!NetworkStatusChecker.isNetworkAvailable(this)) {
            Snackbar.make(view, getString(R.string.registration_internet_error), Snackbar.LENGTH_SHORT).show();
        } else {
            Call<UserRegistrationModel> request = restService.register(login, password);
            try {
                UserRegistrationModel registrationModel = request.execute().body();
                Log.d(LOG_TAG, "Status: " + registrationModel.getStatus());
                if (registrationModel.getStatus().equals(STATUS_SUCCESS)){
                    MainActivity_.intent(this).start();
                } else if (registrationModel.getStatus().equals(STATUS_LOGIN_BUSY_ALREADY)){
                    Snackbar.make(view, getString(R.string.registration_server_status_busy_login_error), Snackbar.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

