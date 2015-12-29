package com.lasalle.second.part.week1.fragments;


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
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.services.ApplicationServiceFactory;
import com.lasalle.second.part.week1.services.PropertyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultListFragment extends Fragment {

    private PropertySearch currentSearch;
    private ListView listView;

    public SearchResultListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentSearch = new PropertySearch(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        PropertyService propertyService = ApplicationServiceFactory.getInstance(getContext()).getPropertyService();
        propertyService.searchProperties(currentSearch);

        listView = (ListView) fragmentView.findViewById(R.id.fragment_search_list);
        listView.setAdapter(new SearchResultAdapter(getActivity(), currentSearch.getResults()));

        return fragmentView;
    }

}
