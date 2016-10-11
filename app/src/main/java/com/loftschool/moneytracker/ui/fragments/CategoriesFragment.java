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
import android.view.View;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.ui.adapter.CategoriesAdapter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment (R.layout.categories_fragment)
public class CategoriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CategoryEntity>> {

    private final int LOADER_ID = 1;

    @ViewById(R.id.categories_fragment_root_layout)
    CoordinatorLayout rootLayout;
    @ViewById (R.id.list_of_categories)
    RecyclerView recyclerView;
    @ViewById (R.id.categories_fab)
    FloatingActionButton fab;

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.menu_category);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

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
    public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
            @Override
            public List<CategoryEntity> loadInBackground() {
                return CategoryEntity.selectAll();
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
}


