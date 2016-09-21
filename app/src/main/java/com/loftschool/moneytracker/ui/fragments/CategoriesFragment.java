package com.loftschool.moneytracker.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.models.CategoriesModel;
import com.loftschool.moneytracker.ui.adapter.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment{

    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private FloatingActionButton fab;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.categories_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_categories);
        categoriesAdapter = new CategoriesAdapter(getCategories());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoriesAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.categories_fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Snackbar.make(view, "Это снекбар внутри CategoriesFragment" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return rootView;

    }

    private List<CategoriesModel> getCategories(){
        List<CategoriesModel> category = new ArrayList<>();
        category.add(new CategoriesModel("Развлечения"));
        category.add(new CategoriesModel("Мои покупки"));
        category.add(new CategoriesModel("Образование"));
        category.add(new CategoriesModel("Иное"));

        return category;


    }
}
