package com.lasalle.second.part.week1.repositories;

import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;

import java.util.List;

public interface PropertyRepo {

    public List<Property> searchProperties(PropertySearch search);

}
