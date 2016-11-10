package com.loftschool.moneytracker.ui.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.backgroundTasks.CheckStatusBackground;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.ui.adapter.CategoriesAdapter;
import com.loftschool.moneytracker.ui.adapter.ClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment(R.layout.categories_fragment)
@OptionsMenu(R.menu.menu_search)
public class CategoriesFragment extends Fragment implements View.OnClickListener {

    private static final int LOADER_ID = 0;
    private static final String SEARCH_QUERY_ID = "search_query_id";
    private CategoriesAdapter categoriesAdapter;
    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    SearchView searchView;


    @Bean
    CheckStatusBackground checkStatusBackground;

    @ViewById(R.id.categories_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById(R.id.list_of_categories)
    RecyclerView recyclerView;
    @ViewById(R.id.categories_fab)
    FloatingActionButton fab;
    @ViewById(R.id.category_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @AfterViews
    void LinearLayoutManager() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(700);
        itemAnimator.setRemoveDuration(700);
        recyclerView.setItemAnimator(itemAnimator);

        refreshLayout.setColorSchemeColors(new int[] {getResources().getColor(R.color.colorAccent)});
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCategory("");
            }
        });
    }

    public void onClick(View v) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_window);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_window_title);
        final EditText editText = (EditText) dialog.findViewById(R.id.dialog_window_edit_text);
        Button okButton = (Button) dialog.findViewById(R.id.dialog_window_btn_ok);
        Button cancelButton = (Button) dialog.findViewById(R.id.dialog_window_btn_cancel);
        textView.setText(getString(R.string.add_new_category));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = editText.getText();
                if (!TextUtils.isEmpty(text)) {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    String name = editText.getText().toString();
                    categoryEntity.setName(name);
                    categoryEntity.save();
                    loadCategory("");
                    checkStatusBackground.addCategory(name);
                    dialog.dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Background(delay = 600, id = SEARCH_QUERY_ID)
    public void queryCategory(String query) {
        loadCategory(query);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.menu_category);
        loadCategory("");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadCategory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(SEARCH_QUERY_ID, true);
                queryCategory(newText);
                return false;
            }
        });
    }

    private void loadCategory(final String query) {
        getLoaderManager().restartLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<CategoryEntity>>() {
            @Override
            public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
                    @Override
                    public List<CategoryEntity> loadInBackground() {
                        return CategoryEntity.selectAll(query);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<CategoryEntity>> loader, List<CategoryEntity> data) {
                categoriesAdapter = new CategoriesAdapter(data, new ClickListener() {
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
                recyclerView.setAdapter(categoriesAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<CategoryEntity>> loader) {

            }
        });
    }

    private void toggleSelection(int position) {
        categoriesAdapter.toggleSelection(position);
        int count = categoriesAdapter.getSelectedItemCount();
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
                    categoriesAdapter.removeItems(categoriesAdapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            categoriesAdapter.clearSelection();
            actionMode = null;
        }
    }
}


