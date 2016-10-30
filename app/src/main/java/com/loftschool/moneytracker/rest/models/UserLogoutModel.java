package com.loftschool.moneytracker.rest.models;


import com.google.gson.annotations.SerializedName;

public class UserLogoutModel {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
