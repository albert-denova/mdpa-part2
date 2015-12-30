package com.lasalle.second.part.week1.fragments;


import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.adapters.SectionTabAdapter;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {

    public SearchResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchResultview = inflater.inflate(R.layout.fragment_search_result, container, false);

        setupTabOptions(searchResultview);

        Toolbar toolbar = (Toolbar) searchResultview.findViewById(R.id.searchResultToolbar);
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);

        return searchResultview;
    }

    protected void setupTabOptions(View searchResultview) {
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
    }

    private SectionTabAdapter.Entry createTabEntry(PropertySearch baseSearch, boolean toRent, boolean toSell, String name)
    {
        PropertySearch tabSearch = baseSearch;
        tabSearch.setRent(toRent);
        tabSearch.setSell(toSell);

        Bundle fragmentArguments = new Bundle();
        tabSearch.addToBundle(fragmentArguments);

        Fragment fragment = new SearchResultListFragment();
        fragment.setArguments(fragmentArguments);

        return new SectionTabAdapter.Entry(fragment, name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
