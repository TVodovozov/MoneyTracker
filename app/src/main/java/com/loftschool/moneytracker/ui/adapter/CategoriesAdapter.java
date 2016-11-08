package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CategoriesAdapter extends SelectableAdapter<CategoriesAdapter.CategoriesHolder> {

    private List<CategoryEntity> categories;
    private ClickListener clickListener;
    private List<ExpensesEntity> expensesList;

    public CategoriesAdapter(List<CategoryEntity> categories,
                             ClickListener clickListener) {
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @Override
    public CategoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoriesHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CategoriesHolder holder, int position) {
        CategoryEntity category = categories.get(position);
        holder.name.setText(category.getName());
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void removeItems(List<Integer> positions) {
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                for (int i = 0; i <= positions.size(); i++) {
                    removeItem(positions.get(0));
                    positions.remove(0);
                }
            }
        }
    }

    private void removeItem(int position) {
        removeCategories(position);
        notifyItemRemoved(position);
    }

    private void removeCategories(int position) {
        if (categories.get(position) != null) {
            categories.get(position).delete();
            categories.remove(position);
        }
    }

    class CategoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ClickListener clickListener;
        TextView name;
        View selectedOverlay;

        public CategoriesHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.categories_item_categories_name);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return clickListener != null && clickListener.onItemLongClicked(getAdapterPosition());
        }
    }
}