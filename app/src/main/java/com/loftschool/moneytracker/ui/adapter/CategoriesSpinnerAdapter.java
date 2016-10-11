package com.loftschool.moneytracker.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;

import java.util.List;


public class CategoriesSpinnerAdapter extends ArrayAdapter<CategoryEntity> implements SpinnerAdapter {

   private List<CategoryEntity> ListCategoryEntity;


    public CategoriesSpinnerAdapter(Context context, List<CategoryEntity> ListCategoryEntity) {
        super(context, 0, ListCategoryEntity);
        this.ListCategoryEntity = ListCategoryEntity;
    }

    public int getCount(){
        return ListCategoryEntity.size();
    }

    public long getItemId(int position) {
        return ListCategoryEntity.get(position).getId();
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                CategoryEntity categoryEntity = (CategoryEntity) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_name_text, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.spinner_text);
        name.setText(categoryEntity.name);

        return convertView;
    }

    @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
        CategoryEntity categoryEntity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_name_text, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.spinner_text);
        name.setText(categoryEntity.name);

        return convertView;
    }
}


