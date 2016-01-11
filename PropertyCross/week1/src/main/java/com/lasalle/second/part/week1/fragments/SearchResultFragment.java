package com.lasalle.second.part.week1.fragments;


import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.adapters.SectionTabAdapter;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;
import com.lasalle.second.part.week1.util.PropertySearchBundleBuilder;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {

    public SearchResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchResultview = inflater.inflate(R.layout.fragment_search_result, container, false);

        TabLayout tabLayout = (TabLayout) searchResultview.findViewById(R.id.search_results_frag_tabs);
        ViewPager viewPager = (ViewPager) searchResultview.findViewById(R.id.search_results_frag_content);

        PropertySearch lastSearch = ApplicationServiceFactory.getInstance(getContext()).getPropertyService().getLastSearch();

        ArrayList<SectionTabAdapter.Entry> entryArrayList = new ArrayList<>();
        SectionTabAdapter.Entry entryAll = createTabEntry(lastSearch, true, true, getString(R.string.results_tab_all));
        entryArrayList.add(entryAll);

        SectionTabAdapter.Entry entryRent = createTabEntry(lastSearch, true, false, getString(R.string.results_tab_rent));
        entryArrayList.add(entryRent);

        SectionTabAdapter.Entry entrySale = createTabEntry(lastSearch, false, true, getString(R.string.results_tab_sell));
        entryArrayList.add(entrySale);

        viewPager.setAdapter(new SectionTabAdapter(getChildFragmentManager(), entryArrayList));
        tabLayout.setupWithViewPager(viewPager);

        return searchResultview;
    }

    private SectionTabAdapter.Entry createTabEntry(PropertySearch baseSearch, boolean toRent, boolean toSell, String name)
    {
        PropertySearch tabSearch = baseSearch;
        tabSearch.setRent(toRent);
        tabSearch.setSell(toSell);

        Bundle fragmentArguments = new Bundle();
        PropertySearchBundleBuilder.addToBundle(tabSearch, fragmentArguments);

        Fragment fragment = new SearchResultListFragment();
        fragment.setArguments(fragmentArguments);

        return new SectionTabAdapter.Entry(fragment, name);
    }

}
