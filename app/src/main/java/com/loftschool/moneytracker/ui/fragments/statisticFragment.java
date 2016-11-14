package com.loftschool.moneytracker.ui.fragments;

import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.storege.entities.CategoryEntity;
import com.loftschool.moneytracker.storege.entities.ExpensesEntity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.statistic_fragment)
public class StatisticFragment extends Fragment {

    @ViewById(R.id.statistic_chart)
    PieChart pieChart;

    int a;
    float count;
    List<PieEntry> pieChartList = new ArrayList<>();

    @AfterViews
    void loadChart() {

        CategoryEntity.selectAll("");
        for (int i = 1; i <= CategoryEntity.selectAll("").size(); i++) {
            a = 0;
            count = 0;
            CategoryEntity category = CategoryEntity.selectById(i);
            if (category != null) {
                for (int j = 1; j <= ExpensesEntity.selectAll("").size(); j++) {
                    ExpensesEntity expense = ExpensesEntity.selectById(j);
                    if (expense != null && expense.getCategory() == category) {
                        int tableSize = ExpensesEntity.selectAll("").size();
                        float table = (float) tableSize;
                        a = a + 1;
                        float u = (float) a;
                        count = ((u * 100) / table);
                    }
                }
                if (count != 0) {

                    pieChartList.add(new PieEntry(count, category.getName()));
                }
            }
        }

        PieDataSet dataSet = new PieDataSet(pieChartList, getString(R.string.statistic_text));
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int i : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(i);
        }
        for (int i : ColorTemplate.JOYFUL_COLORS) {
            colors.add(i);
        }
        for (int i : ColorTemplate.COLORFUL_COLORS) {
            colors.add(i);
        }

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(R.color.colorAccent);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        pieChart.animateY(3000);
    }
}