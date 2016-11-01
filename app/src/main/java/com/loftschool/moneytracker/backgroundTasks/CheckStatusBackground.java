package com.loftschool.moneytracker.backgroundTasks;


import android.util.Log;

import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.rest.models.Category.AddCategoryModel;
import com.loftschool.moneytracker.rest.models.Category.CategoryModel;
import com.loftschool.moneytracker.rest.models.Category.UserSyncCategoriesModel;
import com.loftschool.moneytracker.rest.models.CheckGoogleTokenModel;
import com.loftschool.moneytracker.rest.models.GoogleLoginUserModel;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
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
    private RestService restService = new RestService();

    @Background
    public void checkGoogleTokenStatus() {
        try {
            CheckGoogleTokenModel checkGoogleTokenModel = restService.checkGoogleToken(token);
            Log.d(LOG_TAG, "Status: " + checkGoogleTokenModel.getStatus());
            Log.d(LOG_TAG, "Token" + token);
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
            UserSyncCategoriesModel categoriesSyncModel = restService.getCategories(token, googleToken);
            Log.d("Getcat", "Getc: " + categoriesSyncModel.getStatus());
            List<CategoryModel> categoryModels = categoriesSyncModel.getData();
            if (CategoryEntity.selectAll().size() == 0) {
                for (CategoryModel quote : categoryModels) {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setName(quote.getTitle());
                    categoryEntity.save();
                    Log.d("Getcat", "Title: " + categoryEntity.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //showError();
        }
    }

    @Background
    public void addCategory(String category) {
        try {
            AddCategoryModel addCategoryModel = restService.addCategory(category, token, googleToken);
            Log.d("Getcat", "Sync categories: " + addCategoryModel.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
            //showError();
        }
    }
}
