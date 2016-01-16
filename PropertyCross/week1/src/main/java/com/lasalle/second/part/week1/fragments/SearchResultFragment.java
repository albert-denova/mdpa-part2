package com.lasalle.second.part.week1.fragments;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.adapters.SearchResultAdapter;
import com.lasalle.second.part.week1.adapters.SectionTabAdapter;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;
import com.lasalle.second.part.week1.util.PropertySearchBundleBuilder;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {

    private SectionTabAdapter sectionTabAdapter;
    private int lastSortCriteria;

    public SearchResultFragment() {
        sectionTabAdapter = null;
        lastSortCriteria = -1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchResultview = inflater.inflate(R.layout.fragment_search_result, container, false);

        setupToolbar(searchResultview);
        setupTabOptions(searchResultview);

        return searchResultview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_result_portrait_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        PropertySearch.SortCriteria sortCriteria = PropertySearch.SortCriteria.DEFAULT;
        boolean changedSort = false;
        boolean sameSortPressed = (lastSortCriteria == itemId);
        if(itemId == R.id.search_option_sort_distance) {
            changedSort = true;
            sortCriteria = sameSortPressed ? PropertySearch.SortCriteria.DISTANCE_INVERSE :
                    PropertySearch.SortCriteria.DISTANCE;
        }
        else if(itemId == R.id.search_option_sort_price) {
            changedSort = true;
            sortCriteria = sameSortPressed ? PropertySearch.SortCriteria.PRICE_INVERSE :
                    PropertySearch.SortCriteria.PRICE;
        }
        else if(itemId == R.id.search_option_sort_footage) {
            changedSort = true;
            sortCriteria = sameSortPressed ? PropertySearch.SortCriteria.FOOTAGE_INVERSE :
                    PropertySearch.SortCriteria.FOOTAGE;
        }

        // We will just store the itemId if the sort button was not the same, that way we allow
        // for one search or another without over-complicating things. It's not the best solution but
        // it works.
        lastSortCriteria = sameSortPressed ? -1 : itemId;

        if(changedSort && sectionTabAdapter != null) {
            //sectionTabAdapter.sort(sortCriteria);
            Intent intent = new Intent(SearchResultListFragment.ORDER_INTENT);
            intent.putExtra(SearchResultListFragment.ORDER_INTENT_CRITERIA, sortCriteria);
            getActivity().sendBroadcast(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setupToolbar(View searchResultview) {
        Toolbar toolbar = (Toolbar) searchResultview.findViewById(R.id.searchResultToolbar);
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
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

        sectionTabAdapter = new SectionTabAdapter(getChildFragmentManager(), entryArrayList);

        viewPager.setAdapter(sectionTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    protected SectionTabAdapter.Entry createTabEntry(PropertySearch baseSearch, boolean toRent, boolean toSell, String name)
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
