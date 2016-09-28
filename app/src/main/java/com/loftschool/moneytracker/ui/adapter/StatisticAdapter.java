package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.models.StatisticModel;

import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticHolder>{

    private List<StatisticModel> statisticList;

    public StatisticAdapter(List<StatisticModel> statisticList){
        this.statisticList = statisticList;

    }

    @Override
    public StatisticHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.statistic_item, parent, false);
        return new StatisticHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StatisticHolder holder, int position) {
        StatisticModel category = statisticList.get(position);
        //holder.name.setText(statistic.getName());

    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }

    class StatisticHolder extends RecyclerView.ViewHolder{

        TextView name;

        public StatisticHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.statistic_item_statistic_name);


        }
    }
}
