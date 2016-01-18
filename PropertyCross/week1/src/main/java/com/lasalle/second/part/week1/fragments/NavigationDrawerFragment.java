package com.lasalle.second.part.week1.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.activities.UserLoginActivity;

public class NavigationDrawerFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ListView listView = (ListView) getActivity().findViewById(R.id.navigation_drawer_list);

        initWithoutRegisteredUser(listView);
        super.onCreate(savedInstanceState);
    }

    protected void initWithoutRegisteredUser(ListView listView) {
        String[] items = new String[] {"User account"};

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UserLoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
