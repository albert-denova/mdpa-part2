package com.lasalle.second.part.week1.util;

import android.os.Bundle;

import com.lasalle.second.part.week1.model.PropertySearch;

/**
 * Created by albert.denova on 11/01/16.
 */
public class PropertySearchBundleBuilder {

    private static final String BUNDLE_KEY_QUERY = "query";
    private static final String BUNDLE_KEY_LONGITUDE = "longitude";
    private static final String BUNDLE_KEY_LATITUDE = "latitude";
    private static final String BUNDLE_KEY_RENT = "rent";
    private static final String BUNDLE_KEY_SELL = "sell";

    public static void addToBundle(PropertySearch propertySearch, Bundle bundle) {
        bundle.putString(BUNDLE_KEY_QUERY, propertySearch.getQuery());
        bundle.putFloat(BUNDLE_KEY_LATITUDE, propertySearch.getLatitude());
        bundle.putFloat(BUNDLE_KEY_LONGITUDE, propertySearch.getLongitude());
        bundle.putBoolean(BUNDLE_KEY_RENT, propertySearch.isRent());
        bundle.putBoolean(BUNDLE_KEY_SELL, propertySearch.isSell());
    }

    public static PropertySearch createFromBundle(Bundle bundle) {
        PropertySearch propertySearch = new PropertySearch();
        propertySearch.setQuery(bundle.getString(BUNDLE_KEY_QUERY));
        propertySearch.setLatitude(bundle.getFloat(BUNDLE_KEY_LATITUDE));
        propertySearch.setLongitude(bundle.getFloat(BUNDLE_KEY_LONGITUDE));
        propertySearch.setRent(bundle.getBoolean(BUNDLE_KEY_RENT));
        propertySearch.setSell(bundle.getBoolean(BUNDLE_KEY_SELL));

        return propertySearch;
    }

}
