package com.lasalle.second.part.week1.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by albert.denova on 23/12/15.
 */
public class JSonUtils {

    public static float getFloat(JSONObject jsonObject, String key) throws JSONException {
        Number number = (Number) jsonObject.get(key);
        return number.floatValue();
    }

}
