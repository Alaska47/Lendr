package com.akotnana.lendr.fragments;

/**
 * Created by anees on 3/3/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.akotnana.lendr.R;
import com.akotnana.lendr.cards.DashboardCard;
import com.akotnana.lendr.cards.ViewLoansCard;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class LoansFragment extends Fragment {

    private ArrayList<Card> cardsBlue = new ArrayList<>();
    private ArrayList<Card> cardsPurple = new ArrayList<>();
    private ArrayList<Card> cardsTotal = new ArrayList<>();

    boolean blueStatus = true;
    boolean purpleStatus = true;

    CardRecyclerView mRecyclerView;

    public static LoansFragment newInstance() {
        LoansFragment fragment = new LoansFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loans_fragment, container, false);
        addCard("Help for Electric Bill", "13 Investors", 240d, 500d, 0);
        addCard("Paycheck Loan", "Risk Rating: 746", 58d, 100d, 1);
        addCard("Groceries Loan", "4 Investors", 25d, 100d, 0);

        final CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cardsTotal);

        //Staggered grid view
        mRecyclerView = (CardRecyclerView) v.findViewById(R.id.card_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        final CheckBox blueCheck = (CheckBox) v.findViewById(R.id.blue_check);
        CheckBox purpleCheck = (CheckBox) v.findViewById(R.id.purple_check);
        blueCheck.setChecked(true);
        purpleCheck.setChecked(true);
        blueCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                blueStatus = b;
                updateView();
                mCardArrayAdapter.notifyDataSetChanged();
            }
        });

        purpleCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                purpleStatus = b;
                updateView();
                mCardArrayAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    public void updateView() {
        cardsTotal.clear();
        if(blueStatus)
            cardsTotal.addAll(cardsBlue);
        if(purpleStatus)
            cardsTotal.addAll(cardsPurple);
    }

    public void addCard(String title, String desc, double a, double b, int thing) {
        Card card = new ViewLoansCard(getActivity(), getContext(), title, thing, a, b, desc);
        card.setCardElevation(getResources().getDimension(R.dimen.carddemo_shadow_elevation));
        //card.setBackgroundColorResourceId(R.color.colorPrimary);
        if (thing == 0) {
            cardsBlue.add(card);
        } else {
            cardsPurple.add(card);
        }
        cardsTotal.add(card);
    }
}
