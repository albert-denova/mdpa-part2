package com.lasalle.second.part.week1.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.adapters.SearchResultAdapter;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;
import com.lasalle.second.part.week1.services.PropertyService;
import com.lasalle.second.part.week1.util.PropertySearchBundleBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultListFragment extends Fragment {

    public static final String ORDER_INTENT = "com.lasalle.second.part.SEARCH_RESULT_ORDER";
    public static final String ORDER_INTENT_CRITERIA = "sortCriteria";

    private PropertySearch currentSearch;
    private ListView listView;

    public SearchResultListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentSearch = PropertySearchBundleBuilder.createFromBundle(getArguments());
        registerOrderIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        List<Property> searchResults = doPropertySearch();

        listView = (ListView) fragmentView.findViewById(R.id.fragment_search_list);
        listView.setAdapter(new SearchResultAdapter(getActivity(), searchResults));

        return fragmentView;
    }

    protected List<Property> doPropertySearch() {
        PropertyService propertyService = ApplicationServiceFactory.getInstance(getContext()).getPropertyService();
        return propertyService.searchProperties(currentSearch);
    }

    protected void registerOrderIntent() {
        IntentFilter intentFilter = new IntentFilter(ORDER_INTENT);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                PropertySearch.SortCriteria sortCriteria = (PropertySearch.SortCriteria) intent.getSerializableExtra(ORDER_INTENT_CRITERIA);
                currentSearch.setSortCriteria(sortCriteria);

                List<Property> searchResults = doPropertySearch();
                SearchResultAdapter searchResultAdapter = (SearchResultAdapter) listView.getAdapter();
                searchResultAdapter.setPropertiesList(searchResults);
                searchResultAdapter.notifyDataSetChanged();
            }
        };

        getActivity().registerReceiver(broadcastReceiver, intentFilter);

    }

}
