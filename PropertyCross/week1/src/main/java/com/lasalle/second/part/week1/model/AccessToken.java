package com.lasalle.second.part.week1.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by albert.denova on 09/01/16.
 */
public class AccessToken {

    private String token;
    private String refreshToken;
    private Integer ttl;
    private boolean valid;

    public AccessToken()
    {
        valid = false;
    }

    public AccessToken(JSONObject jsonObject) {
        try {
            token = jsonObject.getString("access_token");
            refreshToken = jsonObject.getString("refresh_token");
            ttl = jsonObject.getInt("expires_in");
            valid = true;
        }
        catch (JSONException e) {
            Log.e(this.getClass().getName(), "Object could not be created from json", e);
        }
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
