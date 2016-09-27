package com.loftschool.moneytracker.ui;


import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loftschool.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EActivity(R.layout.add_expense_activity)

public class AddExpenseActivity extends AppCompatActivity {

    @ViewById(R.id.expense_prise)
    EditText prise;
    @ViewById(R.id.expense_desc)
    EditText desc;
    @ViewById(R.id.expense_cat)
    Spinner cat;
    @ViewById(R.id.expenses_date)
    EditText date;
    @ViewById(R.id.expenses_btn_cancel)
    Button cancel;
    @ViewById(R.id.expenses_btn_add)
    Button add;

    String string;
    SimpleDateFormat sdf;

    @Click(R.id.expenses_btn_cancel)
    void btnCancelIsClicked() {
        finish();
    }
    @Click(R.id.expenses_btn_add)
    void btnApplyIsClicked() {

        if (((prise.getText().length() > 0) & (date.getText().length()) > 0) & (string.equals(R.string.expenses_select_categories))) {
            add.setEnabled(true);
            Toast.makeText(getBaseContext(), R.string.expenses_toast_add, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.expenses_warning, Toast.LENGTH_SHORT).show();
        }
    }
    @AfterViews
    void catClicked() {
        string = String.valueOf(cat.getSelectedItem());
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
    }
    @TargetApi(Build.VERSION_CODES.N)
    @AfterViews
    void toDayDate(){
               sdf = new SimpleDateFormat("dd.MM.yyyy");
               String currentDateandTime = sdf.format(new Date());
               date.setText(currentDateandTime);
    }
}





