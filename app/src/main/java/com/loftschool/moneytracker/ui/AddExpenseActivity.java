package com.loftschool.moneytracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.adapter.CategoriesSpinnerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.add_expense_activity)
public class AddExpenseActivity extends AppCompatActivity {

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

    String toDayDate;

    @AfterViews
    void load() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String toDayDate = sdf.format(new Date());
        date.setText(toDayDate);

        CategoriesSpinnerAdapter SpinnerAdapter = new CategoriesSpinnerAdapter (this, getDataList());
        Spinner spinner = (Spinner) findViewById(R.id.expense_cat);
        spinner.setAdapter(SpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            saveExpense();
            back();
        }
    }

    private void saveExpense() {
        ExpensesEntity expensesEntity = new ExpensesEntity();
        expensesEntity.setName(desc.getText().toString());
        expensesEntity.setPrice(prise.getText().toString());
        expensesEntity.setDate(toDayDate);
        CategoryEntity category = (CategoryEntity) spinnerCategories.getSelectedItem();
        expensesEntity.setCategory(category);
        expensesEntity.save();
        finish();
    }

    private List<CategoryEntity> getDataList() {
        return new Select().from(CategoryEntity.class).execute();
    }
}




