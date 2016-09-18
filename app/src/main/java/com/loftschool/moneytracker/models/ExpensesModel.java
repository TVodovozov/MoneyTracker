package com.loftschool.moneytracker.models;


public class ExpensesModel {

    private String name;
    private String price;

    public ExpensesModel(String name, String price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
