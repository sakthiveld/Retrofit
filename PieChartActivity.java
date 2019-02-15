package com.example.sakthivel.retrofitapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        PieChart pieChart = findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(3000,Easing.EasingOption.EaseInOutCubic);

        ArrayList<PieEntry> percentage = new ArrayList();
        percentage.add(new PieEntry(300, "Tamil"));
        percentage.add(new PieEntry(200, "Telugu"));
        percentage.add(new PieEntry(100, "Hindi"));
        percentage.add(new PieEntry(100, "Malayalam"));
        percentage.add(new PieEntry(150, "English"));
        percentage.add(new PieEntry(350, "Chinese"));
        percentage.add(new PieEntry(100, "Spanish"));
        percentage.add(new PieEntry(200, "Arabic"));
        percentage.add(new PieEntry(300, "Bengali"));
        percentage.add(new PieEntry(200, "French"));

        PieDataSet dataSet = new PieDataSet(percentage, "Percentage");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(new int[] { R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10}, this);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);
    }
}
