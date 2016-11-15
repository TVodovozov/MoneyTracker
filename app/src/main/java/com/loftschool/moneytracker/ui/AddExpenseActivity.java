package com.loftschool.moneytracker.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.backgroundTasks.CheckStatusBackground;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.adapter.CategoriesAdapter;
import com.loftschool.moneytracker.ui.adapter.CategoriesSpinnerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TargetApi(Build.VERSION_CODES.M)
@EActivity(R.layout.add_expense_activity)
public class AddExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int categoryId;

    @ViewById(R.id.expense_prise)
    EditText prise;
    @ViewById(R.id.expense_desc)
    EditText desc;
    @ViewById(R.id.expenses_date)
    EditText date;
    @ViewById(R.id.expense_cat)
    Spinner spinnerCategories;
    @ViewById(R.id.expenses_btn_cancel)
    Button btnCancel;
    @ViewById(R.id.expenses_btn_add)
    Button btnApply;

    @Bean
    CheckStatusBackground checkStatusBackground;

    @AfterViews
    void load() {
        showDate();
        showSpinner(spinnerCategories);
    }

    @OptionsItem(R.id.home)
    void back() {
        onBackPressed();
        finish();
    }

    @Click(R.id.expenses_btn_cancel)
    void btnCancelIsClicked() {
        back();
    }

    @Click(R.id.expenses_btn_add)
    void btnAddIsClicked() {
        if ((prise.getText().toString().equals("") & (desc.getText().toString().equals("")))) {
            Toast.makeText(getApplicationContext(), R.string.expenses_warning, Toast.LENGTH_SHORT).show();
        } else {
            btnApply.setEnabled(true);
            Toast.makeText(getBaseContext(), R.string.expenses_toast_add, Toast.LENGTH_SHORT).show();
            saveExpense(prise.getText().toString(), desc.getText().toString(), date.getText().toString());
            checkStatusBackground.addExpenses(prise.getText().toString(), desc.getText().toString(), categoryId, date.getText().toString());
            back();
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_fade_in, R.anim.exit_push_out);
    }

    public void showDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String toDayDate = sdf.format(new Date());
        date.setText(toDayDate, TextView.BufferType.EDITABLE);
    }

    public void showSpinner(Spinner spinnerCategories) {
        List<CategoryEntity> categoriesList = new ArrayList<CategoryEntity>();
        categoriesList.addAll(CategoryEntity.selectAll(""));
        CategoriesSpinnerAdapter categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this, categoriesList);
        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        categoriesSpinnerAdapter.notifyDataSetChanged();
        spinnerCategories.setAdapter(categoriesSpinnerAdapter);
        spinnerCategories.setOnItemSelectedListener(this);
    }

    private void saveExpense(String prise, String desc, String date) {
        ExpensesEntity expensesEntity = new ExpensesEntity();
        expensesEntity.setName(desc);
        expensesEntity.setPrice(prise);
        expensesEntity.setDate(date);
        CategoryEntity category = (CategoryEntity) spinnerCategories.getSelectedItem();
        expensesEntity.setCategory(category);
        expensesEntity.save();
    }
}
