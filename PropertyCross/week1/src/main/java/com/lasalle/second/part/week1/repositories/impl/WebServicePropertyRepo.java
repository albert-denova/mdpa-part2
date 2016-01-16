package com.lasalle.second.part.week1.repositories.impl;

import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.repositories.PropertyRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albert.denova on 11/01/16.
 */
public class WebServicePropertyRepo implements PropertyRepo {

    @Override
    public List<Property> searchProperties(PropertySearch search, AccessToken accessToken) {
        return new ArrayList<>();
    }

}
