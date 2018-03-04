package com.akotnana.lendr.utils;

import com.robinhood.spark.SparkAdapter;

/**
 * Created by anees on 3/3/2018.
 */

public class DashboardSparkAdapter extends SparkAdapter {
    private float[] yData;

    public DashboardSparkAdapter(float[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}