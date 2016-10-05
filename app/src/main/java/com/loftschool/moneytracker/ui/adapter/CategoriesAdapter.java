package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;

import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {

    private List<CategoryEntity> categories;

    public CategoriesAdapter(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    @Override
    public CategoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoriesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoriesHolder holder, int position) {
        CategoryEntity category = categories.get(position);
        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoriesHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public CategoriesHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.categories_item_categories_name);
        }
    }
}