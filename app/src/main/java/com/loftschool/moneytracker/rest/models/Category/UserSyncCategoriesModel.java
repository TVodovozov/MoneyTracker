package com.loftschool.moneytracker.rest.models.Category;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserSyncCategoriesModel {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<CategoryModel> data = new ArrayList<>();

    @SerializedName("code")
    int code;

    public String getStatus() {
        return status;
    }

    public List<CategoryModel> getData() {
        return data;
    }
}
