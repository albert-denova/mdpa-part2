package com.lasalle.second.part.week1.services;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.requests.TokenRequest;
import com.lasalle.second.part.week1.util.VolleyRequestHandler;

import org.json.JSONObject;

public class AuthService {

    private AccessToken accessToken;

    public void requestAccessToken() {
        TokenRequest tokenRequest = new TokenRequest(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    accessToken = new AccessToken(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(this.getClass().getName(), "Error: " + error.getMessage());
                }
            });
        VolleyRequestHandler.getInstance().addToRequestQueue(tokenRequest);
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public boolean isAccesTokenValid() {
        return accessToken.isValid();
    }
}
