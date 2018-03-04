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

import com.akotnana.lendr.R;
import com.akotnana.lendr.cards.BrowseLoansCard;
import com.akotnana.lendr.cards.ViewLoansCard;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class BrowseLoansFragment extends Fragment {

    private ArrayList<Card> cards = new ArrayList<>();
    CardRecyclerView mRecyclerView;

    public static BrowseLoansFragment newInstance() {
        BrowseLoansFragment fragment = new BrowseLoansFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.browse_loan_fragment, container, false);

        final CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        mRecyclerView = (CardRecyclerView) v.findViewById(R.id.card_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        addCard("Help for Electric Bill", "13 Investors" , "Risk Rating: 746", 240d, 500d);
        addCard("Paycheck Loan","1 Investors", "Risk Rating: 746", 58d, 100d);
        addCard("Groceries Loan", "4 Investors" , "Risk Rating: 123", 25d, 100d);

        return v;
    }

    public void addCard(String title, String desc, String risk, double a, double b) {
        Card card = new BrowseLoansCard(getActivity(), getContext(), title, desc, a, b, risk);
        card.setCardElevation(getResources().getDimension(R.dimen.carddemo_shadow_elevation));
        cards.add(card);
    }
}
