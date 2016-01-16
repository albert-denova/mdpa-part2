package com.lasalle.second.part.week1.repositories;

import com.lasalle.second.part.week1.model.PropertySearch;

import org.json.JSONArray;

public interface PropertyRepo {

    public JSONArray searchProperties(PropertySearch search);

}
