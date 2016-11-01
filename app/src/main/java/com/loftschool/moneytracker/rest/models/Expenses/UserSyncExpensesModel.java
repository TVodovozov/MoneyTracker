package com.loftschool.moneytracker.rest.models.Expenses;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserSyncExpensesModel {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<ExpenseModel> data = new ArrayList<>();

    @SerializedName("code")
    int code;

    public String getStatus() {
        return status;
    }

    public List<ExpenseModel> getData() {
        return data;
    }
}
