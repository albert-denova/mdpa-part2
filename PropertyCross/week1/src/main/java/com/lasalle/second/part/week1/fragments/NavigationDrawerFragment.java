package com.lasalle.second.part.week1.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.activities.UserLoginActivity;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment {

    private ArrayAdapter<String> itemsAdapter;
    private List<String> itemsList;
    private AccessTokenTracker accessTokenTracker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerListViewAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(AccessToken.getCurrentAccessToken() == null) {
            setupFacebookListener();
            initWithoutRegisteredUser();
        }
        else {
            initWithRegisteredUser();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    protected void registerListViewAdapter() {
        ListView listView = (ListView) getActivity().findViewById(R.id.navigation_drawer_list);

        itemsList = new ArrayList<>();
        itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemsList);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UserLoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    protected void initWithoutRegisteredUser() {
        itemsList.clear();
        itemsList.add("User account");
        itemsAdapter.notifyDataSetChanged();
    }

    protected void initWithRegisteredUser() {
        itemsList.clear();
        itemsList.add("Profile");
        itemsAdapter.notifyDataSetChanged();
    }

    protected void setupFacebookListener() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(AccessToken.getCurrentAccessToken() != null) {
                    initWithRegisteredUser();
                }
            }

        };
    }
}
