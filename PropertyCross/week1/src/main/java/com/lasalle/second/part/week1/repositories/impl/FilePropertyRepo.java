package com.lasalle.second.part.week1.repositories.impl;

import android.content.Context;
import android.util.Log;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.model.AccessToken;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.repositories.PropertyRepo;
import com.lasalle.second.part.week1.util.JSonPropertyBuilder;
import com.lasalle.second.part.week1.util.JSonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilePropertyRepo implements PropertyRepo {

    private final String PROPERTIES_ARRAY_NODE_NAME = "datos";

    private Context context;

    public FilePropertyRepo(Context context) {
        this.context = context;
    }

    @Override
    public JSONArray searchProperties(PropertySearch search, AccessToken accessToken) {
        String propertiesString = new String("");

        if(search.isSell() && search.isRent())
        {
            propertiesString = readStringFromFile(R.raw.rent_property_list_all);
        }
        else if(search.isRent())
        {
            propertiesString = readStringFromFile(R.raw.rent_property_list_rent);
        }
        else if(search.isSell())
        {
            propertiesString = readStringFromFile(R.raw.rent_property_list_sell);
        }

        return JSonUtils.createArrayFromString(propertiesString, PROPERTIES_ARRAY_NODE_NAME);
    }

    protected String readStringFromFile(int file) {
        String fileContent = new String("");

        InputStream inputStream = context.getResources().openRawResource(file);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            fileContent = out.toString();
            reader.close();
        } catch (IOException exc) {
            Log.e(this.getClass().getName(), "Exception", exc);
        } finally {
            try {
                if(inputStream != null) inputStream.close();
            } catch(Exception exc2) {
                Log.e(this.getClass().getName(), "Exception", exc2);
            }
        }

        return fileContent;

    }


}
