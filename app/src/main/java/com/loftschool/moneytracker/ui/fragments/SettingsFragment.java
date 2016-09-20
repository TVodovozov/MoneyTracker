package com.loftschool.moneytracker.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.models.ExpensesModel;
import com.loftschool.moneytracker.models.SettingsModel;
import com.loftschool.moneytracker.ui.adapter.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment{

    private RecyclerView recyclerView;
    private SettingsAdapter settingsAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_settings);
        //settingsAdapter = new SettingsAdapter(getExpenses());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(settingsAdapter);



        return rootView;

    }

/*    private List<SettingsModel> getSettings(){
        List<SettingsModel> settings = new ArrayList<>();
        settings.add(new SettingsModel("Books"));

    }*/
}
