package com.loftschool.moneytracker.storege.entities;

import android.support.v4.media.MediaMetadataCompat;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "categories")
public class CategoryEntity extends Model {

    @Column(name = "name")
    public String name;

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity (){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpensesEntity> expenses(){
        return getMany(ExpensesEntity.class, "category");
    }

    public static List<CategoryEntity> selectAll(){
        return new Select()
                .from(CategoryEntity.class)
                .execute();
    }
}
