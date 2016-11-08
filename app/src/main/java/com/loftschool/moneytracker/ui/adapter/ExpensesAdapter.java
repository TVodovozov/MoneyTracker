package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.ExpensesHolder> {

    private List<ExpensesEntity> expenseList;
    private ClickListener clickListener;

    public ExpensesAdapter(List<ExpensesEntity> expenseList,
                           ClickListener clickListener) {
        this.expenseList = expenseList;
        this.clickListener = clickListener;
    }

    @Override
    public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpensesHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(ExpensesHolder holder, int position) {
        ExpensesEntity expense = expenseList.get(position);
        holder.name.setText(expense.name);
        holder.price.setText(expense.price);
        holder.date.setText(expense.date);
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
        if (expense.getCategory() != null) {
            holder.category.setText(expense.getCategory().getName());
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
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
        removeExpenses(position);
        notifyItemRemoved(position);
    }

    private void removeExpenses(int position) {
        if (expenseList.get(position) != null) {
            expenseList.get(position).delete();
            expenseList.remove(position);
        }
    }

    class ExpensesHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ClickListener clickListener;
        public TextView name;
        public TextView price;
        View selectedOverlay;
        TextView date;
        TextView category;

        public ExpensesHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.expanse_item_expense_name);
            price = (TextView) itemView.findViewById(R.id.expanse_item_expense_price);
            date = (TextView) itemView.findViewById(R.id.expanse_item_expense_date);
            category = (TextView) itemView.findViewById(R.id.expanse_item_expense_category);
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

