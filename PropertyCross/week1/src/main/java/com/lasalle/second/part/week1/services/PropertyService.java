package com.lasalle.second.part.week1.services;

import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.repositories.PropertyRepo;

import java.util.ArrayList;
import java.util.List;

public class PropertyService {

    private PropertyRepo propertyRepo;
    private List<Property> lastSearchResult;

    public PropertyService(PropertyRepo propertyRepo) {
        this.propertyRepo = propertyRepo;
        this.lastSearchResult = new ArrayList<>();
    }

    public List<Property> searchProperties(String searchQuery) {
        lastSearchResult = this.propertyRepo.searchProperties(searchQuery);
        return lastSearchResult;
    }

    public List<Property> getLastSearchResult() {
        return lastSearchResult;
    }
}
