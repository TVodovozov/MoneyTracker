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

public class SettingsFragment extends Fragment{

    private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_settings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;

    }
}
