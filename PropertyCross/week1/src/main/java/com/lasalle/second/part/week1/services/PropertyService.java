package com.lasalle.second.part.week1.services;

import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.repositories.PropertyRepo;

import java.util.List;

public class PropertyService {

    private PropertyRepo propertyRepo;
    private PropertySearch lastSearch;

    public PropertyService(PropertyRepo propertyRepo) {
        this.propertyRepo = propertyRepo;
        this.lastSearch = new PropertySearch();
    }

    public List<Property> searchProperties(String searchQuery, AccessToken accessToken) {
        PropertySearch currentSearch = new PropertySearch(searchQuery, true, true);
        return searchProperties(currentSearch, accessToken);
    }

    public List<Property> searchProperties(PropertySearch currentSearch, AccessToken accessToken)
    {
        if(!lastSearch.hasSameQuery(currentSearch))
        {
            currentSearch.setResults(this.propertyRepo.searchProperties(currentSearch, accessToken));
            lastSearch = currentSearch;
        }

        return lastSearch.getResults();
    }

    public PropertySearch getLastSearch() {
        return lastSearch;
    }
}
