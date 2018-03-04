package com.akotnana.lendr.cards;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akotnana.lendr.R;
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

public class ViewLoansCard extends Card {

    String title;
    int color;
    double amountFraction;
    double amountTotal;
    String miniDesc;

    private final Context mContext;

    protected TextView mTitle;
    protected TextView miniDescription;
    protected ProgressBar progressBar;
    protected TextView progressDetail;
    protected ImageView star;
    protected ImageView star1;

    protected Activity a;

    public ViewLoansCard(Activity act, Context context, String title, int color, double a, double b, String c) {
        this(context, R.layout.view_loan_card_layout);
        this.title = title;
        this.color = color;
        this.amountFraction = a;
        this.a = act;
        this.amountTotal = b;
        this.miniDesc = c;
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    public ViewLoansCard(Context context, int innerLayout) {
        super(context, innerLayout);
        this.mContext = context;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mTitle = (TextView) parent.findViewById(R.id.loan_title);
        mTitle.setText(this.title);
        miniDescription = (TextView) parent.findViewById(R.id.card_mini_description);
        miniDescription.setText(this.miniDesc);
        star = (ImageView) parent.findViewById(R.id.star_color);
        star1 = (ImageView) parent.findViewById(R.id.star_color1);
        progressDetail = (TextView) parent.findViewById(R.id.progress_info);
        progressBar = (ProgressBar) parent.findViewById(R.id.progressBar);
        if(this.color == 0) {
            star.setVisibility(View.VISIBLE);
            star1.setVisibility(GONE);
            progressDetail.setText("$" + String.valueOf(this.amountTotal));
        } else {
            star1.setVisibility(View.VISIBLE);
            star.setVisibility(GONE);
            progressDetail.setText(String.valueOf((int) Math.round((float) this.amountFraction * 100 / this.amountTotal)) + "%");
        }
        progressBar.setProgress((int) Math.round((float) this.amountFraction * 100 / this.amountTotal));
    }
}
