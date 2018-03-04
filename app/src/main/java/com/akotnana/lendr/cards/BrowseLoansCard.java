package com.akotnana.lendr.cards;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akotnana.lendr.R;

import it.gmariotti.cardslib.library.internal.Card;

import static android.view.View.GONE;

/**
 * Created by anees on 3/3/2018.
 */

public class BrowseLoansCard extends Card {

    String title;
    double amountFraction;
    double amountTotal;
    String investorDisc;
    String riskDisc;

    private final Context mContext;

    protected TextView mTitle;
    protected TextView investorDescription;
    protected TextView riskDescription;
    protected ProgressBar progressBar;
    protected TextView progressDetail;;

    protected Activity a;

    public BrowseLoansCard(Activity act, Context context, String title, String investor, double a, double b, String risk) {
        this(context, R.layout.browse_card_layout);
        this.title = title;
        this.investorDisc = investor;
        this.amountFraction = a;
        this.a = act;
        this.amountTotal = b;
        this.riskDisc = risk;
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    public BrowseLoansCard(Context context, int innerLayout) {
        super(context, innerLayout);
        this.mContext = context;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mTitle = (TextView) parent.findViewById(R.id.loan_title);
        mTitle.setText(this.title);
        investorDescription = (TextView) parent.findViewById(R.id.investors_info);
        investorDescription.setText(this.investorDisc);
        riskDescription = (TextView) parent.findViewById(R.id.risk_info);
        riskDescription.setText(this.riskDisc);
        progressDetail = (TextView) parent.findViewById(R.id.progress_info);
        progressBar = (ProgressBar) parent.findViewById(R.id.progressBar);
        progressDetail.setText("$" + String.valueOf(this.amountTotal));
        progressBar.setProgress((int) Math.round((float) this.amountFraction * 100 / this.amountTotal));
    }
}
