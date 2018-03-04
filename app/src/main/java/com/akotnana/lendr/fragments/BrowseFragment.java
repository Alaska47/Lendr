package com.akotnana.lendr.fragments;

/**
 * Created by anees on 3/3/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akotnana.lendr.R;
import com.akotnana.lendr.utils.SubTabAdapter;
import com.marlonmafra.android.widget.SegmentedTab;

import java.util.ArrayList;
import java.util.List;

public class BrowseFragment extends Fragment {

    private SegmentedTab segmentedTab;
    private SubTabAdapter subTabAdapter;
    private ViewPager viewPager;

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.browse_fragment, container, false);
        segmentedTab = (SegmentedTab) v.findViewById(R.id.segmentedTab);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        fragmentList.add(BrowseLoansFragment.newInstance());
        titles.add("GLOBAL");
        fragmentList.add(BrowseLoansFragment.newInstance());
        titles.add("COMMUNITY");

        this.subTabAdapter = new SubTabAdapter(getChildFragmentManager(), getContext(), fragmentList);
        this.viewPager.setAdapter(this.subTabAdapter);
        this.segmentedTab.setupWithViewPager(this.viewPager);
        this.segmentedTab.setup(titles);

        return v;
    }
}
