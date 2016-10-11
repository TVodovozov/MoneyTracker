package com.loftschool.moneytracker.ui.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.loftschool.moneytracker.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment (R.layout.settings_fragment)
public class SettingsFragment extends Fragment{

    @ViewById (R.id.list_of_settings)
    RecyclerView recyclerView;

    }