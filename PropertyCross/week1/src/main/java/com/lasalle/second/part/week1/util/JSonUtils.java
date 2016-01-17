package com.lasalle.second.part.week1.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSonUtils {

    public static float getFloat(JSONObject jsonObject, String key) throws JSONException {
        Number number = (Number) jsonObject.get(key);
        return number.floatValue();
    }

    public static JSONArray createArrayFromString(String jsonString, String nodeName) {
        JSONArray jsonArray = new JSONArray();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonArray = jsonObject.getJSONArray(nodeName);
        } catch (JSONException exc) {
            Log.e("JSonUtils", "Exception", exc);
        }

        return jsonArray;
    }

    public static JSONArray createArrayFromString(String jsonString) {
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException exc) {
            Log.e("JSonUtils", "Exception", exc);
            jsonArray = new JSONArray();
        }

        return jsonArray;
    }

}
