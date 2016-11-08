package com.loftschool.moneytracker.backgroundTasks;


import android.util.Log;

import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.rest.models.Category.AddCategoryModel;
import com.loftschool.moneytracker.rest.models.Category.CategoryModel;
import com.loftschool.moneytracker.rest.models.Category.UserSyncCategoriesModel;
import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.Expenses.AddExpensesModel;
import com.loftschool.moneytracker.rest.models.Expenses.ExpenseModel;
import com.loftschool.moneytracker.rest.models.Expenses.UserSyncExpensesModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.MoneyTrackerApplication;
import com.loftschool.moneytracker.utils.ConstantsManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;


import java.io.IOException;
import java.util.List;

@EBean
public class CheckStatusBackground {

    private final static String LOG_TAG = CheckStatusBackground.class.getSimpleName();
    private String token = MoneyTrackerApplication.getGoogleAuthToken();
    private String googleToken = MoneyTrackerApplication.getGoogleAuthToken();
    public RestService restService = new RestService();

    @Background
    public void checkGoogleTokenStatus() {
        try {
            CheckGoogleTokenModel checkGoogleTokenModel = restService.checkGoogleToken(token);

            if (checkGoogleTokenModel.getStatus().equals(ConstantsManager.STATUS_SUCCEED)) {
                GoogleLoginUserModel googleLoginUserModel = restService.googleLoginUser(token);
                MoneyTrackerApplication.saveEmail(googleLoginUserModel.getEmail());
                MoneyTrackerApplication.saveName(googleLoginUserModel.getName());
                MoneyTrackerApplication.savePicture(googleLoginUserModel.getPicture());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //showError();
        }
    }

    @Background
    public void checkUserCategories() {
        try {
            UserSyncCategoriesModel categoriesSyncModel = restService.postCategories(token, googleToken);
            List<CategoryModel> categoryModels = categoriesSyncModel.getData();
            if (CategoryEntity.selectAll("").size() == 0) {
                for (CategoryModel quote : categoryModels) {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setName(quote.getTitle());
                    categoryEntity.save();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //showError();
        }
    }

    @Background
    public void checkUserExpenses() {
        String googleToken = MoneyTrackerApplication.getGoogleAuthToken();
        String token = MoneyTrackerApplication.getAuthToken();
        try {
            UserSyncExpensesModel expensesModel = restService.postExpenses(token, googleToken);
            List<ExpenseModel> expenseModels = expensesModel.getData();
            if (ExpensesEntity.selectAll("").size() == 0) {
                for (ExpenseModel quote : expenseModels) {
                    ExpensesEntity expenseEntity = new ExpensesEntity();
                    expenseEntity.setName(quote.getComment());
                    expenseEntity.setPrice(quote.getSum());
                    int query = (int) (long) quote.getCategoryId();
                    CategoryEntity categoryEntity = CategoryEntity.selectById(query);
                    expenseEntity.setCategory(categoryEntity);
                    expenseEntity.setDate(quote.getDate());
                    expenseEntity.save();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    public void addCategory(String category) {
        try {
            AddCategoryModel addCategoryModel =
                    restService.addCategory(category, token, googleToken);
        } catch (IOException e) {
            e.printStackTrace();
            //showError();
        }
    }

    @Background
    public void addExpenses(String sum, String comment, int categoryId, String date) {
        String googleToken = MoneyTrackerApplication.getGoogleAuthToken();
        String token = MoneyTrackerApplication.getAuthToken();
        try {
            AddExpensesModel addExpenseModel =
                    restService.addExpenses(sum, comment, categoryId, date, token, googleToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
