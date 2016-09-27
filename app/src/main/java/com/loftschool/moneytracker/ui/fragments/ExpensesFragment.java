package com.loftschool.moneytracker.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.models.ExpensesModel;
import com.loftschool.moneytracker.ui.AddExpenseActivity_;
import com.loftschool.moneytracker.ui.adapter.ExpensesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpensesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExpensesAdapter expensesAdapter;
    private FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expenses_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_expanses);
        expensesAdapter = new ExpensesAdapter(getExpenses());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(expensesAdapter);


        fab = (FloatingActionButton) rootView.findViewById(R.id.expenses_fab);
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
                startActivity(intent);
            }
        });
    }

    private List<ExpensesModel> getExpenses() {
        List<ExpensesModel> expenses = new ArrayList<>();
        expenses.add(new ExpensesModel("Books", "123"));
        expenses.add(new ExpensesModel("Cafe", "100"));
        expenses.add(new ExpensesModel("Cinema", "125"));
        expenses.add(new ExpensesModel("Theatre", "123"));
        expenses.add(new ExpensesModel("Food", "1378"));
        expenses.add(new ExpensesModel("Mall", "165"));
        expenses.add(new ExpensesModel("Books", "1776"));
        expenses.add(new ExpensesModel("Cafe", "135"));
        expenses.add(new ExpensesModel("Cinema", "723"));
        expenses.add(new ExpensesModel("Theatre", "8723"));
        expenses.add(new ExpensesModel("Food", "3459"));
        expenses.add(new ExpensesModel("Mall", "765"));
        expenses.add(new ExpensesModel("Books", "123"));
        expenses.add(new ExpensesModel("Cafe", "123"));
        expenses.add(new ExpensesModel("Cinema", "123"));
        expenses.add(new ExpensesModel("Theatre", "123"));
        expenses.add(new ExpensesModel("Food", "123"));
        expenses.add(new ExpensesModel("Mall", "123"));
        return expenses;
    }
}
