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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.AddExpenseActivity_;
import com.loftschool.moneytracker.ui.adapter.ExpensesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment (R.layout.expenses_fragment)
@OptionsMenu (R.menu.menu_search)
public class ExpensesFragment extends Fragment {

    private static final int LOADER_ID = 0;
    private static final String LOG_TAG = ExpensesFragment_.class.getSimpleName();
    final String SEARCH_QUERY_ID = "search_query_id";
    SearchView searchView;

    @ViewById(R.id.expanse_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById(R.id.list_of_expanses)
    RecyclerView recyclerView;
    @ViewById(R.id.expenses_fab)
    FloatingActionButton fab;
    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @AfterViews
    void LinearLayoutManager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Click(R.id.expenses_fab)
    void fabExpensesOnClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(SEARCH_QUERY_ID, true);
                queryExpenses(newText);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.menu_buy);
        loadExpenses("");
    }
    @Background (delay = 600, id = SEARCH_QUERY_ID)
    public void queryExpenses (String query){
        loadExpenses(query);
    }

    private void loadExpenses(final String query){
        getLoaderManager().restartLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<ExpensesEntity>>() {
            @Override
            public Loader<List<ExpensesEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ExpensesEntity>> loader = new AsyncTaskLoader<List<ExpensesEntity>>(getActivity()) {
                    @Override
                    public List<ExpensesEntity> loadInBackground() {
                        return ExpensesEntity.selectAll(query);
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
        });
    }
}
