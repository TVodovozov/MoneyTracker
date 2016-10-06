package com.loftschool.moneytracker.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.activeandroid.query.Select;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.ui.adapter.CategoriesAdapter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment (R.layout.categories_fragment)
@OptionsMenu (R.menu.menu_search)
public class CategoriesFragment extends Fragment {

    private static final int LOADER_ID = 0;
    private static final String SEARCH_QUERY_ID = "search_query_id";
    SearchView searchView;

    @ViewById(R.id.categories_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById (R.id.list_of_categories)
    RecyclerView recyclerView;
    @ViewById (R.id.categories_fab)
    FloatingActionButton fab;
    @OptionsMenuItem (R.id.search_action)
    MenuItem menuItem;

    @Click (R.id.categories_fab)
    void fabCategoriesOnClickListener (){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, R.string.category_snackbar_massage, Snackbar.LENGTH_LONG).show();
            }
        });
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

    @Background(delay = 600, id = SEARCH_QUERY_ID)
    public void queryCategory (String query){
        loadCategory(query);
    }

    private void loadCategory(final String query){
        getLoaderManager().restartLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<CategoryEntity>>() {
            @Override
            public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
                    @Override
                    public List<CategoryEntity> loadInBackground() {
                        return getSelectAll(query);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<CategoryEntity>> loader, List<CategoryEntity> data) {
                recyclerView.setAdapter(new CategoriesAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<CategoryEntity>> loader) {

            }
        });
    }
    private List<CategoryEntity> getSelectAll (String query){
        return new Select()
                .from(CategoryEntity.class)
                .where("Name LIKE ?", new String[]{'%' + query + '%'})
                .execute();
    }

}


