package com.loftschool.moneytracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    NetworkStatusChecker networkStatusChecker;


    @ViewById (R.id.registration_layout_root)
    RelativeLayout registrationLayout;
    @ViewById(R.id.enter_login)
    EditText login;
    @ViewById(R.id.text_login)
    TextView textLogin;
    @ViewById(R.id.enter_password)
    EditText password;
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
    void onRegistrationClick(){
            if (password.getText().toString().equals("") & login.getText().toString().equals("")
                    || password.getText().toString().equals("")
                    || login.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), R.string.registration_text_error, Toast.LENGTH_SHORT).show();
            } else if
                    (login.getText().length() < MIN_LENGTH) {
                Toast.makeText(getApplicationContext(), R.string.registration_text_error_login, Toast.LENGTH_SHORT).show();
            } else if
                    (password.getText().length() < MIN_LENGTH) {
                Toast.makeText(getApplicationContext(), R.string.registration_text_error_password, Toast.LENGTH_SHORT).show();
            } else  {
                    startActivity(new Intent(RegistrationActivity.this, MainActivity_.class));
                }
    }

    @Background
    void register() {
        RestService restService = new RestService();
        Call<UserRegistrationModel> request = null;
        try {
            request = restService.register("tvodovozov_1", "test_1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            UserRegistrationModel registrationModel = request.execute().body();
            Log.d(LOG_TAG, "Status: " + registrationModel.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
