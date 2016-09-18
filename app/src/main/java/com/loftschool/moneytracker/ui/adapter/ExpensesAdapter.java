package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.models.ExpensesModel;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>{

    private List<ExpensesModel> expensesList;

    public ExpensesAdapter(List<ExpensesModel> expensesList){
        this.expensesList = expensesList;

    }

    @Override
    public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpensesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpensesHolder holder, int position) {
        ExpensesModel expense = expensesList.get(position);
        holder.name.setText(expense.getName());
        holder.prise.setText(expense.getPrice());

    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    class ExpensesHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView prise;

        public ExpensesHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.expanse_item_expense_name);
            prise = (TextView) itemView.findViewById(R.id.expanse_item_expense_price);

        }
    }
}
