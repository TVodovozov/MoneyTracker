package com.loftschool.moneytracker.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.ui.adapter.StatisticAdapter;

public class StatisticFragment extends Fragment{

    private RecyclerView recyclerView;
    private StatisticAdapter StatisticAdapter;
    private static final String LOG_TAG = StatisticFragment.class.getSimpleName();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.statistic_fragment, container, false);

        //recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_statistic);
        //StatisticAdapter = new StatisticAdapter(getStatistic());
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(StatisticAdapter);



        return rootView;

    }
    /*   private List<StatisticModel> getStatistic(){
        List<StatisticModel> statistic = new ArrayList<>();
        statistic.add(new StatisticModel("Развлечения"));
        statistic.add(new StatisticModel("Мои покупки"));
        statistic.add(new StatisticModel("Образование"));
        statistic.add(new StatisticModel("Иное"));

        return statistic;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LOG_TAG, "onAttach() Fragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate() Fragment");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(LOG_TAG, "onViewCreated() Fragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "onActivityCreated() Fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() Fragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume() Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause() Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop() Fragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "onDestroyView() Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy() Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "onDetach() Fragment");
    }
}

