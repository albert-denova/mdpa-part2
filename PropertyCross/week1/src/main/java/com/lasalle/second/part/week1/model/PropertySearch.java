package com.lasalle.second.part.week1.model;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PropertySearch {

    public enum SortCriteria {
        DEFAULT,
        PRICE,
        FOOTAGE,
        DISTANCE,
        PRICE_INVERSE,
        FOOTAGE_INVERSE,
        DISTANCE_INVERSE
    }

    private String query;
    private float longitude;
    private float latitude;
    private boolean rent;
    private boolean sell;
    private List<Property> results;
    private SortCriteria sortCriteria;

    public PropertySearch() {
        this.results = new ArrayList<>();
        this.sortCriteria = SortCriteria.DEFAULT;
    }

    public PropertySearch(String query, boolean rent, boolean sell) {
        this.query = query;
        this.rent = rent;
        this.sell = sell;
        this.results = new ArrayList<>();
        this.sortCriteria = SortCriteria.DEFAULT;
    }

    public PropertySearch(Float longitude, Float latitude, boolean rent, boolean sell) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.rent = rent;
        this.sell = sell;
        this.results = new ArrayList<>();
        this.sortCriteria = SortCriteria.DEFAULT;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public List<Property> getResults() {
        return results;
    }

    public void setResults(List<Property> results) {
        this.results = results;
    }

    public SortCriteria getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }


}
