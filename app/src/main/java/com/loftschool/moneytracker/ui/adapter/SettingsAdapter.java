package com.loftschool.moneytracker.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.models.ExpensesModel;
import com.loftschool.moneytracker.models.SettingsModel;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder>{

    private List<SettingsModel> settingsList;

    public SettingsAdapter(List<SettingsModel> settingsList){
        this.settingsList = settingsList;

    }

    @Override
    public SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_item, parent, false);
        return new SettingsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SettingsHolder holder, int position) {
        SettingsModel settings = settingsList.get(position);
        holder.name.setText(settings.getName());

    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    class SettingsHolder extends RecyclerView.ViewHolder{

        TextView name;

        public SettingsHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.settings_item_settings_name);

        }
    }
}
