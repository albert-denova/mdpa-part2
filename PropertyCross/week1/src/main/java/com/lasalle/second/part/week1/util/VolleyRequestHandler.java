package com.lasalle.second.part.week1.util;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lasalle.second.part.week1.PropertyCrossApplication;

/**
 * Created by albert.denova on 09/01/16.
 */
public class VolleyRequestHandler {
    public static final String TAG = VolleyRequestHandler.class.getSimpleName();

    public static final String API_BASE_URL = "https://push.opentrends.net:8100/mdpa/api/";
    public static final String API_ID = "android";
    public static final String API_SECRET = "SomeRandomCharsAndNumbers";

    private static VolleyRequestHandler instance;
    private RequestQueue requestQueue;

    protected VolleyRequestHandler() {
    }

    public static VolleyRequestHandler getInstance() {
        if(instance == null)
        {
            instance = new VolleyRequestHandler();
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PropertyCrossApplication.getContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }



}
