package com.lasalle.second.part.week1.repositories;

import com.lasalle.second.part.week1.listeners.PropertyRepoListener;
import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.model.PropertySearch;

import org.json.JSONArray;

public interface PropertyRepo {

    public void searchProperties(PropertySearch search, AccessToken accessToken, PropertyRepoListener<JSONArray> listener);

}
