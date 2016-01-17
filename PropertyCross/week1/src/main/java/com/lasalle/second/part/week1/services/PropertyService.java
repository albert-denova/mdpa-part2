package com.lasalle.second.part.week1.services;

import com.lasalle.second.part.week1.listeners.PropertyRepoListener;
import com.lasalle.second.part.week1.listeners.PropertyServiceListener;
import com.lasalle.second.part.week1.model.AccessToken;
import android.util.Log;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.model.SearchHistory;
import com.lasalle.second.part.week1.repositories.PropertyRepo;
import com.lasalle.second.part.week1.repositories.SearchHistoryRepo;
import com.lasalle.second.part.week1.util.JSonPropertyBuilder;
import com.lasalle.second.part.week1.util.JSonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyService {

    private PropertyRepo propertyRepo;
    private SearchHistoryRepo searchHistoryRepo;
    private PropertySearch lastSearch;

    public PropertyService(PropertyRepo propertyRepo, SearchHistoryRepo searchHistoryRepo) {
        this.propertyRepo = propertyRepo;
        this.searchHistoryRepo = searchHistoryRepo;
        this.lastSearch = new PropertySearch();
    }

    public void searchPropertiesCachingResult(PropertySearch currentSearch,
                                                AccessToken accessToken,
                                            PropertyServiceListener listener) {
        searchProperties(currentSearch, true, accessToken, listener);
    }

    public void searchPropertiesWithoutCaching(PropertySearch currentSearch,
                                                         AccessToken accessToken,
                                                         PropertyServiceListener listener) {
        searchProperties(currentSearch, false, accessToken, listener);
    }

    public PropertySearch getLastSearch() {
        return lastSearch;
    }

    protected void searchProperties(final PropertySearch currentSearch, final boolean cacheResults,
                                              AccessToken accessToken, final PropertyServiceListener listener) {
        JSONArray propertiesJsonArray = getCachedSearch(currentSearch);
        if(propertiesJsonArray.length() == 0) {
            this.propertyRepo.searchProperties(currentSearch, accessToken,
                    new PropertyRepoListener<JSONArray>() {
                        @Override
                        public void onDataLoaded(JSONArray data) {
                            createPropertyListFromSearchResult(currentSearch, data, cacheResults, listener);
                        }
                    });
        }
        else {
            createPropertyListFromSearchResult(currentSearch, propertiesJsonArray, cacheResults, listener);
        }
    }

    protected void createPropertyListFromSearchResult(PropertySearch currentSearch,
                                                      JSONArray propertiesJsonArray,
                                                      boolean cacheResults,
                                                      PropertyServiceListener propertyServiceListener) {

        final boolean includeRent = currentSearch.isRent();
        final boolean includeSell = currentSearch.isSell();
        JsonParseResults parseResults = parseFromJson(propertiesJsonArray, includeRent, includeSell);

        if(cacheResults) {
            final int totalRent = parseResults.getTotalRent();
            final int totalSell = parseResults.getTotalSell();
            cacheResults(currentSearch, propertiesJsonArray, totalRent, totalSell);
        }

        List<Property> resultList = parseResults.getPropertyList();
        sortProperties(resultList, currentSearch.getSortCriteria());
        currentSearch.setResults(resultList);
        lastSearch = currentSearch;

        propertyServiceListener.onDataLoaded(resultList);
    }

    protected JsonParseResults parseFromJson(JSONArray jsonArray, boolean includeRent, boolean includeSell) {
        List<Property> propertyList = new ArrayList<>();
        Integer totalRent = 0;
        Integer totalSell = 0;

        try {
            int jsonArraySize = jsonArray.length();

            for(int index = 0; index < jsonArraySize; ++index) {
                JSONObject childObject = jsonArray.getJSONObject(index);
                Property property = JSonPropertyBuilder.createPropertyFromSearchResultJson(childObject);

                if(property.isRent()) {
                    ++totalRent;
                    if(includeRent) {
                        propertyList.add(property);
                    }
                }
                else {
                    ++totalSell;
                    if(includeSell) {
                        propertyList.add(property);
                    }
                }
            }

        } catch (JSONException exc) {
            Log.e(this.getClass().getName(), "Exception", exc);
        }

        return new JsonParseResults(propertyList, totalRent, totalSell);
    }

    protected void sortProperties(List<Property> propertyList, PropertySearch.SortCriteria sortCriteria) {
        switch(sortCriteria) {
            case DISTANCE: {
                // TODO implement filter by distance
                Collections.shuffle(propertyList);
                break;
            }
            case DISTANCE_INVERSE: {
                //TODO implement filter by distance
                Collections.shuffle(propertyList);
                break;
            }
            case FOOTAGE: {
                Collections.sort(propertyList, new Property.FootageComparator(false));
                break;
            }
            case FOOTAGE_INVERSE: {
                Collections.sort(propertyList, new Property.FootageComparator(true));
                break;
            }
            case PRICE: {
                Collections.sort(propertyList, new Property.PriceComparator(false));
                break;
            }
            case PRICE_INVERSE: {
                Collections.sort(propertyList, new Property.PriceComparator(true));
                break;
            }
        }
    }

    protected void cacheResults(PropertySearch search, JSONArray resultsArray, int totalRent, int totalSell) {
        boolean cached = searchHistoryRepo.addSearchResult(search, totalRent, totalSell, resultsArray.toString());

        if(!cached) {
            Log.e(getClass().getName(), "Result was not cached properly");
        }
    }

    protected JSONArray getCachedSearch(PropertySearch search) {
        JSONArray cachedSearchResult = new JSONArray();

        SearchHistory searchHistory = searchHistoryRepo.getSearchHistoryFromSearch(search);

        if(searchHistory != null) {
            cachedSearchResult = JSonUtils.createArrayFromString(searchHistory.getRawResults());
        }

        return cachedSearchResult;
    }

    protected class JsonParseResults {
        private List<Property> propertyList;
        private int totalRent;
        private int totalSell;

        public JsonParseResults(List<Property> propertyList, int totalRent, int totalSell) {
            this.propertyList = propertyList;
            this.totalRent = totalRent;
            this.totalSell = totalSell;
        }

        public List<Property> getPropertyList() {
            return propertyList;
        }

        public int getTotalRent() {
            return totalRent;
        }

        public int getTotalSell() {
            return totalSell;
        }
    }
}
