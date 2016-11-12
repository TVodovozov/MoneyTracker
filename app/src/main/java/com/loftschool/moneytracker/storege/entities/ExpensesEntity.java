package com.loftschool.moneytracker.storege.entities;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "expenses")
public class ExpensesEntity extends Model {

    @Column(name = "price")
    public String price;
    @Column(name = "name")
    public String name;
    @Column(name = "date")
    public String date;
    @Column(name = "category")
    public CategoryEntity category;


    public ExpensesEntity() {
        super();
    }

    public String getSum() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public static List<ExpensesEntity> selectAll(String query) {
        return new Select()
                .from(ExpensesEntity.class)
                .where("name LIKE?", new String[]{'%' + query + '%'})
                .execute();
    }

    public static ExpensesEntity selectById(int query) {
        return new Select()
                .from(ExpensesEntity.class)
                .where("id LIKE?", query)
                .executeSingle();
    }

    public static ExpensesEntity selectByCategory(CategoryEntity category) {
        return new Select()
                .from(ExpensesEntity.class)
                .where("category LIKE?", category)
                .executeSingle();
    }
}