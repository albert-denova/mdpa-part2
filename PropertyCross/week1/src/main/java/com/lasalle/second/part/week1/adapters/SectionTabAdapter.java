package com.lasalle.second.part.week1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SectionTabAdapter extends FragmentPagerAdapter {

    public static class Entry {
        private Fragment fragment;
        private String name;

        public Entry(Fragment fragment, String name) {
            this.fragment = fragment;
            this.name = name;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getName() {
            return name;
        }
    }

    private ArrayList<Entry> entryList;

    public SectionTabAdapter(FragmentManager fm, ArrayList<Entry> entryList) {
        super(fm);
        this.entryList = entryList;
    }

    @Override
    public Fragment getItem(int position) {
        return entryList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return entryList.get(position).getName();
    }
}
