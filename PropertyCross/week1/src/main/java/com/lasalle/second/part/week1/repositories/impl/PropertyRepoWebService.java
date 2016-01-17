package com.lasalle.second.part.week1.repositories.impl;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lasalle.second.part.week1.listeners.PropertyRepoListener;
import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.repositories.PropertyRepo;
import com.lasalle.second.part.week1.util.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class PropertyRepoWebService implements PropertyRepo {

    protected static final String SEARCH_BASE_URL = "propiedad/buscar/";
    private final String PROPERTIES_ARRAY_NODE_NAME = "datos";
    private final String PAGE_NUMBER_NODE_NAME = "page";
    private final String ELEMENTS_PER_PAGE_NODE_NAME = "pageSize";
    private final String TOTAL_ELEMENTS_NODE_NAME = "total";

    private JSONArray searchResultArray;

    @Override
    public void searchProperties(final PropertySearch search, final AccessToken accessToken,
                                 final PropertyRepoListener<JSONArray> listener) {
        searchResultArray = new JSONArray();
        doSearchRequest(search, accessToken, listener, 1);
    }


    protected void doSearchRequest(final PropertySearch search, final AccessToken accessToken,
                                   final PropertyRepoListener<JSONArray> listener, final int currentPage) {
        final String completeUrl = VolleyRequestHandler.API_BASE_URL + SEARCH_BASE_URL + currentPage;
        //TODO: implement request parameters

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, completeUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final boolean hasMorePages = extractSearchResponseAndCheckIfMorePages(response);
                        if(hasMorePages) {
                            doSearchRequest(search, accessToken, listener, currentPage + 1);
                        }
                        else
                        {
                            listener.onDataLoaded(searchResultArray);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(getClass().getName(), "Exception", error);
                        listener.onDataLoaded(searchResultArray);
                    }
                });

        VolleyRequestHandler.getInstance().addToRequestQueue(jsonRequest);
    }

    protected boolean extractSearchResponseAndCheckIfMorePages(JSONObject response) {
        boolean hasMorePages = false;

        try {
            JSONArray propertiesArray = response.getJSONArray(PROPERTIES_ARRAY_NODE_NAME);
            final int propertiesSize = propertiesArray.length();
            for(int index = 0; index < propertiesSize; ++index) {
                searchResultArray.put(propertiesArray.getJSONObject(index));
            }

            final int totalElements = response.getInt(TOTAL_ELEMENTS_NODE_NAME);
            final int elementsPerPage = response.getInt(ELEMENTS_PER_PAGE_NODE_NAME);
            final int currentPage = response.getInt(PAGE_NUMBER_NODE_NAME);

            final int totalPages = (totalElements + elementsPerPage - 1)/elementsPerPage;
            hasMorePages = currentPage < totalPages;
        }
        catch (Exception e) {
            Log.d(getClass().getName(), "Exception", e);

        }

        return hasMorePages;
    }

}
