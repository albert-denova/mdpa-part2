package com.lasalle.second.part.week1.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.adapters.SectionTabAdapter;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {
    protected final String TAB_ALL = "all";
    protected final String TAB_RENT = "all";
    protected final String TAB_SALE = "BUY";

    public SearchResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchResultview = inflater.inflate(R.layout.fragment_search_result, container, false);


        TabLayout tabLayout = (TabLayout) searchResultview.findViewById(R.id.search_results_frag_tabs);
        ViewPager viewPager = (ViewPager) searchResultview.findViewById(R.id.search_results_frag_content);

        ArrayList<SectionTabAdapter.Entry> entryArrayList = new ArrayList<>();
        SectionTabAdapter.Entry entry = new SectionTabAdapter.Entry(new SearchResultListFragment(), getString(R.string.results_tab_all));
        entryArrayList.add(entry);

        viewPager.setAdapter(new SectionTabAdapter(getChildFragmentManager(), entryArrayList));
        tabLayout.setupWithViewPager(viewPager);

        return searchResultview;
    }

}
