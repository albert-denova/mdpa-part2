package com.lasalle.second.part.week1.model;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PropertySearch {

    private String query;
    private float longitude;
    private float latitude;
    private boolean rent;
    private boolean sell;
    private List<Property> results;

    public PropertySearch() {
        this.results = new ArrayList<>();
    }

    public PropertySearch(String query, boolean rent, boolean sell) {
        this.query = query;
        this.rent = rent;
        this.sell = sell;
        this.results = new ArrayList<>();
    }

    public PropertySearch(Float longitude, Float latitude, boolean rent, boolean sell) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.rent = rent;
        this.sell = sell;
        this.results = new ArrayList<>();
    }

    public boolean hasSameQuery(PropertySearch search) {
        final boolean sameQuery = search.getQuery().equals(this.query);
        final boolean sameCoordinates = (search.getLatitude().equals(this.latitude)) &&
                (search.getLongitude().equals(this.longitude));
        final boolean bothAreRent = search.isRent() == this.rent;
        final boolean bothAreSell = search.isSell() == this.sell;

        return (sameQuery || sameCoordinates) && bothAreRent && bothAreSell;
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
}
