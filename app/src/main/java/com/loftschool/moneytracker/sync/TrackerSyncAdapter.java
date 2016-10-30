package com.loftschool.moneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.rest.RestService;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.MoneyTrackerApplication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {


    private final static String LOG_TAG = TrackerSyncAdapter.class.getSimpleName();

    public TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    //тут написать запросы
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Syncing started");
        /*syncCategories();
        syncExpenses();*/
    }

   /* public void syncCategories() {
        RestService restService = new RestService(); //там все связывается и реализуется

        try {
            String googleToken = MoneyTrackerApplication.getGoogleAuthToken();
            String token = MoneyTrackerApplication.getAuthToken();
            List<CategoryModel> categories = new ArrayList<>();
            int t = CategoryEntity.selectAll("").size();
            for (int i = 1; i <= t; i++) {
                CategoryEntity categoryEntity = CategoryEntity.selectById(i);
                String title = categoryEntity.getName();
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(i);
                categoryModel.setTitle(title);
                categories.add(categoryModel);

            }
            String data = new Gson().toJson(categories);
            UserSyncCategoriesModel categoriesSyncModel = restService.syncCategories(data, token, googleToken);//Синхронный запрос
            Log.d("Syncing", "Sync categories: " + categoriesSyncModel.getStatus());
        } catch (IOException e) {
//Если запрос не выполнен – сообщаем пользователя об ошибке
            e.printStackTrace();
            //тут показывать низвесную ошибка
        }
    }


    public void syncExpenses() {
        RestService restService = new RestService(); //там все связывается и реализуется

        try {
            String googleToken = MoneyTrackerApplication.getGoogleAuthToken();
            String token = MoneyTrackerApplication.getAuthToken();
            List<ExpensesModel> expenses = new ArrayList<>();//создаем лист трат
            int t = ExpensesEntity.selectAll("").size();
            for (int i = 1; i <= t; i++) {
                ExpensesEntity expenseEntity = ExpensesEntity.selectById(i);
                String comment = expenseEntity.getName();
                String sum = expenseEntity.getSum();
                String date = expenseEntity.getDate();
                CategoryEntity categoryEntity = expenseEntity.getCategory();
                int categoryId = (int) (long) categoryEntity.getId();

                ExpensesModel expenseModel = new ExpensesModel();//создаем экземпляр модели
                expenseModel.setId(i);
                expenseModel.setComment(comment);
                expenseModel.setSum(sum);
                expenseModel.setTrDate(date);
                expenseModel.setCategoryId(categoryId);
                expenses.add(expenseModel);// заносим этот экземпляр в лист

            }
            String data = new Gson().toJson(expenses);//лист приводим к типу json
            UserSyncExpensesModel expensesSyncModel = restService.syncExpenses(data, token, googleToken);//Синхронный запрос
            Log.d("Syncing", "Sync expenses: " + expensesSyncModel.getStatus());
        } catch (IOException e) {
//Если запрос не выполнен – сообщаем пользователя об ошибке
            e.printStackTrace();
            //тут показывать низвесную ошибка
        }
    }*/


    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,
                true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL,
                true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name),
                context.getString(R.string.sync_account_type));

        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    public static void onAccountCreated(Account newAccount, Context context) {
        final int SYNC_INTERVAL = 60 * 60 * 24;
        final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount,
                context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount,
                context.getString(R.string.content_authority),
                Bundle.EMPTY,
                SYNC_INTERVAL);
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}

