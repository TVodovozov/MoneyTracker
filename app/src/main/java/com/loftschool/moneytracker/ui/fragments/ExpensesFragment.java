package com.loftschool.moneytracker.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;
import com.loftschool.moneytracker.ui.AddExpenseActivity_;
import com.loftschool.moneytracker.ui.adapter.ClickListener;
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

@EFragment(R.layout.expenses_fragment)
@OptionsMenu(R.menu.menu_search)
public class ExpensesFragment extends Fragment {

    private static final int LOADER_ID = 0;
    private static final String LOG_TAG = ExpensesFragment_.class.getSimpleName();
    private static final String SEARCH_QUERY_ID = "search_query_id";
    private ExpensesAdapter expensesAdapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    SearchView searchView;

    @ViewById(R.id.expanse_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById(R.id.list_of_expenses)
    RecyclerView recyclerView;
    @ViewById(R.id.expenses_fab)
    FloatingActionButton fab;
    @ViewById(R.id.expense_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @AfterViews
    void LinearLayoutManager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(700);
        itemAnimator.setRemoveDuration(700);
        recyclerView.setItemAnimator(itemAnimator);

        refreshLayout.setColorSchemeColors(new int[]{getResources().getColor(R.color.colorAccent)});
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadExpenses("");
            }
        });
    }

    @Click(R.id.expenses_fab)
    void fabExpensesOnClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CategoryEntity.selectAll("").size() != 0) {
                    AddExpenseActivity_.intent(getActivity()).start()
                            .withAnimation(R.anim.enter_pull_in, R.anim.exit_fade_out);
                } else {
                    Snackbar.make(rootLayout,
                            getString(R.string.expenses_toast_error),
                            Snackbar.LENGTH_SHORT).show();
                }
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
                loadExpenses(query);
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

    @Background(delay = 600, id = SEARCH_QUERY_ID)
    public void queryExpenses(String query) {
        loadExpenses(query);
    }

    private void loadExpenses(final String query) {
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
                expensesAdapter = new ExpensesAdapter(data, new ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }
                        toggleSelection(position);
                        return true;
                    }
                });
                recyclerView.setAdapter(expensesAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ExpensesEntity>> loader) {

            }
        });
    }

    private void toggleSelection(int position) {
        expensesAdapter.toggleSelection(position);
        int count = expensesAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    expensesAdapter.removeItems(expensesAdapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            expensesAdapter.clearSelection();
            actionMode = null;
        }
    }
}
