package com.lasalle.second.part.week1.requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.lasalle.second.part.week1.util.VolleyRequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by albert.denova on 09/01/16.
 */
public class TokenRequest extends Request<JSONObject> {

    private static final String END_POINT = "oauth/token";
    private Response.Listener<JSONObject> responseListener;


    public TokenRequest(Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, VolleyRequestHandler.API_BASE_URL + END_POINT, errorListener);
        this.responseListener = responseListener;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        responseListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", VolleyRequestHandler.API_ID);
        params.put("client_secret", VolleyRequestHandler.API_SECRET);
        params.put("username", "albertn@salleurl.edu");
        params.put("password", "123456");
        return params;
    }

}
