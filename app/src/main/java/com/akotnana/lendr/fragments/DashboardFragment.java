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
import com.akotnana.lendr.cards.DashboardCard;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class DashboardFragment extends Fragment {

    private final float[] testValues = {3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f};
    private ArrayList<Card> cards = new ArrayList<>();

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_fragment, container, false);
        addCard("Investment Cash Inflow", testValues, true);
        addCard("Credit Rating over Time", testValues, false);
        addCard("Number of loans in progress", 10);

        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) v.findViewById(R.id.card_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        return v;
    }

    public void addCard(String title, float[] values, boolean dollar) {
        Card card = new DashboardCard(getContext(), title, values);
        ((DashboardCard) card).setDollarSign(dollar);
        card.setCardElevation(getResources().getDimension(R.dimen.carddemo_shadow_elevation));
        //card.setBackgroundColorResourceId(R.color.colorPrimary);
        cards.add(card);
    }
    public void addCard(String title, int simpleNum) {
        Card card = new DashboardCard(getContext(), title, simpleNum);
        card.setCardElevation(getResources().getDimension(R.dimen.carddemo_shadow_elevation));
        //card.setBackgroundColorResourceId(R.color.colorPrimary);
        cards.add(card);
    }
}
