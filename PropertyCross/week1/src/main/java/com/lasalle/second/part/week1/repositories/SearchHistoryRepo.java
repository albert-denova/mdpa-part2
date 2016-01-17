package com.lasalle.second.part.week1.repositories;

import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.model.SearchHistory;

/**
 * Created by albert.denova on 16/01/16.
 */
public interface SearchHistoryRepo {

   public boolean addSearchResult(PropertySearch propertySearch, int rentResults, int sellResults,
                                  String rawResults);


   public SearchHistory getSearchHistoryFromSearch(PropertySearch propertySearch);
}
