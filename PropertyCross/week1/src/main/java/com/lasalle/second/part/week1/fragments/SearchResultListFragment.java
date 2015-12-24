package com.lasalle.second.part.week1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.week1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultListFragment extends Fragment {


    public SearchResultListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_list, container, false);
    }

}
