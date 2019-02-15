package com.example.sakthivel.retrofitapp;

import android.graphics.Canvas;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarChartCustomRenderer extends BarChartRenderer {

    public BarChartCustomRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        //mValuePaint.setColor(color);
        c.save();
        c.rotate(-45f, x, y);
        //Log.d("here", formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler) );
        c.drawText(formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler), x, y, mValuePaint);
        c.restore();
    }
}