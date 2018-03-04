package com.akotnana.lendr.cards;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akotnana.lendr.R;
import com.akotnana.lendr.utils.DashboardSparkAdapter;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

import java.text.DecimalFormat;
import java.util.Random;

import it.gmariotti.cardslib.library.internal.Card;

import static android.view.View.GONE;

/**
 * Created by anees on 3/3/2018.
 */

public class DashboardCard extends Card {

    String title;
    float[] data = null;
    int simpleNum;

    boolean dollarSign = false;

    private LineChartView mChart = null;
    private final Context mContext;
    private Runnable mBaseAction;
    protected TextView mTitle;
    protected TextView simpleNumText;


    public DashboardCard(Context context, String title, float[] data) {
        this(context, R.layout.dashboard_card_layout);
        this.title = title;
        this.data = data;
    }
    public DashboardCard(Context context, String title, int simpleNum) {
        this(context, R.layout.dashboard_card_layout);
        this.title = title;
        this.simpleNum = simpleNum;
    }

    public void setDollarSign(boolean hello) {
        this.dollarSign = hello;
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    public DashboardCard(Context context, int innerLayout) {
        super(context, innerLayout);
        this.mContext = context;
        init();
    }

    /**
     * Init
     */
    private void init(){
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        final RandomizedAdapter adapter = new RandomizedAdapter();
        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.card_title);
        simpleNumText = (TextView) parent.findViewById(R.id.simple_num);
        final SparkView sparkView = (SparkView) parent.findViewById(R.id.card_chart);
        final TextView scrubInfoTextView = (TextView) parent.findViewById(R.id.scrub_info_textview);
        mTitle.setText(this.title);
        if(data == null) {
            sparkView.setVisibility(GONE);
            scrubInfoTextView.setVisibility(GONE);
            simpleNumText.setVisibility(View.VISIBLE);
            simpleNumText.setText(String.valueOf(this.simpleNum));
        } else {
            sparkView.setVisibility(View.VISIBLE);
            scrubInfoTextView.setVisibility(View.VISIBLE);
            parent.findViewById(R.id.chart_layout).setVisibility(View.VISIBLE);
            simpleNumText.setVisibility(GONE);
            //sparkView.setAdapter(new DashboardSparkAdapter(data));
            sparkView.setScrubListener(new SparkView.OnScrubListener() {
                @Override
                public void onScrubbed(Object value) {
                    if (value == null) {
                        if(dollarSign)
                            scrubInfoTextView.setText("$" + String.valueOf(adapter.getY(adapter.getCount()-1)));
                        else
                            scrubInfoTextView.setText(String.valueOf(adapter.getY(adapter.getCount()-1)));
                    } else {
                        if(dollarSign)
                            scrubInfoTextView.setText("$" + String.valueOf((float) value));
                        else
                            scrubInfoTextView.setText(String.valueOf((float) value));
                    }
                }
            });
            sparkView.setAdapter(adapter);
            if(dollarSign)
                scrubInfoTextView.setText("$" + String.valueOf(adapter.getY(adapter.getCount()-1)));
            else
                scrubInfoTextView.setText(String.valueOf(adapter.getY(adapter.getCount()-1)));
        }
    }

    public static class RandomizedAdapter extends SparkAdapter {
        private final float[] yData;
        private final Random random;

        public RandomizedAdapter() {
            random = new Random();
            yData = new float[50];
            randomize();
        }

        public void randomize() {
            for (int i = 0, count = yData.length; i < count; i++) {
                float f = random.nextFloat() * 100;
                DecimalFormat df = new DecimalFormat("###.##");
                yData[i] = Float.parseFloat(df.format(f));
            }
            notifyDataSetChanged();
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
}
