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
import com.lasalle.second.part.week1.listeners.PropertyServiceListener;
import com.lasalle.second.part.week1.model.AccessToken;
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

    public static SearchResultListFragment newInstance(PropertySearch baseSearch, boolean toRent, boolean toSell) {
        PropertySearch tabSearch = baseSearch;
        tabSearch.setRent(toRent);
        tabSearch.setSell(toSell);

        Bundle fragmentArguments = new Bundle();
        PropertySearchBundleBuilder.addToBundle(tabSearch, fragmentArguments);

        SearchResultListFragment fragment = new SearchResultListFragment();
        fragment.setArguments(fragmentArguments);

        return fragment;
    }

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

        listView = (ListView) fragmentView.findViewById(R.id.fragment_search_list);
        listView.setAdapter(new SearchResultAdapter(getActivity(), new ArrayList<Property>()));

        doPropertySearch();

        return fragmentView;
    }

    protected void doPropertySearch() {
        ApplicationServiceFactory applicationServiceFactory = ApplicationServiceFactory.getInstance();

        PropertyService propertyService = applicationServiceFactory.getPropertyService();
        AccessToken accessToken = applicationServiceFactory.getAuthService().getAccessToken();

        propertyService.searchPropertiesWithoutCaching(currentSearch, accessToken,
                new PropertyServiceListener<List<Property>>() {
                    @Override
                    public void onDataLoaded(List<Property> data) {
                        SearchResultAdapter searchResultAdapter = (SearchResultAdapter) listView.getAdapter();
                        searchResultAdapter.setPropertiesList(data);
                        searchResultAdapter.notifyDataSetChanged();
                    }
                });
    }

    protected void registerOrderIntent() {
        IntentFilter intentFilter = new IntentFilter(ORDER_INTENT);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                PropertySearch.SortCriteria sortCriteria = (PropertySearch.SortCriteria) intent.getSerializableExtra(ORDER_INTENT_CRITERIA);
                currentSearch.setSortCriteria(sortCriteria);

                doPropertySearch();
            }
        };

        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

}
