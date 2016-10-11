package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder> {

    private List<ExpensesEntity> expenseList;

    public ExpensesAdapter(List<ExpensesEntity> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpensesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpensesHolder holder, int position) {
        ExpensesEntity expense = expenseList.get(position);
        holder.name.setText(expense.name);
        holder.price.setText(expense.price);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpensesHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView price;

        public ExpensesHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.expanse_item_expense_name);
            price = (TextView) itemView.findViewById(R.id.expanse_item_expense_price);
        }
    }
}
