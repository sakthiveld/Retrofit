package com.example.sakthivel.retrofitapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        BarChart mChart = findViewById(R.id.barchart);
        mChart.animateXY(5000,5000);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.getAxisRight().setEnabled(false);

        BarChartCustomRenderer barChartCustomRenderer = new BarChartCustomRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler());
        mChart.setRenderer(barChartCustomRenderer);

        XAxis xaxis = mChart.getXAxis();
        xaxis.setDrawGridLines(true);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularity(0.5f);
        xaxis.setGranularityEnabled(true);
        xaxis.setDrawLabels(true);
        xaxis.setDrawAxisLine(true);
        xaxis.setEnabled(true);

        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxisLeft.setGranularity(0.5f);
        yAxisLeft.setGranularityEnabled(true);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setEnabled(true);

        Legend legend = mChart.getLegend();
        legend.setEnabled(true);

        ArrayList<BarEntry> details = new ArrayList<BarEntry>();
        BarEntry entry;
        entry = new BarEntry(2010,-945);
        details.add(entry);
        entry = new BarEntry(2011,1540);
        details.add(entry);
        entry = new BarEntry(2012,1133);
        details.add(entry);
        entry = new BarEntry(2013,1240);
        details.add(entry);
        entry = new BarEntry(2014,1369);
        details.add(entry);
        entry = new BarEntry(2015,-1487);
        details.add(entry);
        entry = new BarEntry(2016,2501);
        details.add(entry);
        entry = new BarEntry(2017,1645);
        details.add(entry);
        entry = new BarEntry(2018,1578);
        details.add(entry);
        entry = new BarEntry(2019,3695);
        details.add(entry);

        List<IBarDataSet> dataSets = new ArrayList<>();

        BarDataSet barDataSet = new BarDataSet(details, "Details");
        barDataSet.setDrawValues(true);
        barDataSet.setColors(new int[] { R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10}, this);
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);
    }
}