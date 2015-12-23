package com.lasalle.second.part.week1.services;

import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.repositories.PropertyRepo;

import java.util.List;

public class PropertyService {

    private PropertyRepo propertyRepo;

    public PropertyService(PropertyRepo propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    public List<Property> searchProperties(String searchQuery) {
        return this.propertyRepo.searchProperties(searchQuery);
    }
}
