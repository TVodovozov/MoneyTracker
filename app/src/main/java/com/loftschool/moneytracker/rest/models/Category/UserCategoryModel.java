package com.loftschool.moneytracker.rest.models.Category;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserCategoryModel {

    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private List<Integer> id = new ArrayList<>();

    @SerializedName("title")
    private List<String> title = new ArrayList<>();


    public String getStatus() {
        return status;
    }

    public List<String> getTitle() {
        return title;
    }
}
