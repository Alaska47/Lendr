package com.akotnana.lendr.cards;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akotnana.lendr.R;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by anees on 3/3/2018.
 */

public class DashboardCard extends Card {

    String title;
    String[] labels;
    float[] data;

    private LineChartView mChart = null;
    private final Context mContext;
    private Runnable mBaseAction;
    protected TextView mTitle;
    protected TextView mSecondaryTitle;


    public DashboardCard(Context context, String title, String[] labels, float[] data) {
        this(context, R.layout.dashboard_card_layout);
        this.title = title;
        this.labels = labels;
        this.data = data;
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

        //No Header

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements

        mChart = (LineChartView) parent.findViewById(R.id.card_chart);

        mTitle = (TextView) parent.findViewById(R.id.card_title);

        setupData(this.title, this.labels, this.data);
    }

    public void setupData(String title, String[] labels, float[] data) {
        LineSet dataset = new LineSet(labels, data);
        dataset.setColor(Color.parseColor("#3F51B5"))
                .setDotsColor(Color.parseColor("#3F51B5"))
                .setThickness(4);
        mChart.addData(dataset);
        mChart.setAxisColor(Color.BLACK);
        mChart.setLabelsColor(Color.BLACK);
        mChart.setAxisBorderValues(0, 10)
                .setYLabels(AxisRenderer.LabelPosition.NONE);
        mChart.show();
        mTitle.setText(title);
    }
}
