package com.loftschool.moneytracker.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.AddExpenseActivity_;
import com.loftschool.moneytracker.ui.adapter.ExpensesAdapter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment (R.layout.expenses_fragment)
public class ExpensesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ExpensesEntity>> {

    private final int LOADER_ID = 1;

    @ViewById (R.id.expanse_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById (R.id.list_of_expanses)
    RecyclerView recyclerView;
    @ViewById (R.id.expenses_fab)
    FloatingActionButton fab;



    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        getActivity().setTitle(R.string.menu_buy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Click (R.id.expenses_fab)
            void fabExpensesOnClickListener () {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<ExpensesEntity>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<ExpensesEntity>> loader = new AsyncTaskLoader<List<ExpensesEntity>>(getActivity()) {
            @Override
            public List<ExpensesEntity> loadInBackground() {
                return ExpensesEntity.selectAll();
            }
        };
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ExpensesEntity>> loader, List<ExpensesEntity> data) {
        recyclerView.setAdapter(new ExpensesAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<ExpensesEntity>> loader) {

    }
}
