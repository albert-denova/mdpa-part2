package com.lasalle.second.part.week1.repositories.impl;

import android.content.Context;
import android.util.Log;

import com.lasalle.second.part.week1.R;
import com.lasalle.second.part.week1.model.Property;
import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.repositories.PropertyRepo;
import com.lasalle.second.part.week1.util.JSonPropertyBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FilePropertyRepo implements PropertyRepo {

    private final String PROPERTIES_ARRAY_NODE_NAME = "datos";

    private Context context;

    public FilePropertyRepo(Context context) {
        this.context = context;
    }

    @Override
    public List<Property> searchProperties(PropertySearch search) {
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


        List<Property> propertyList = readPropertiesFromJson(propertiesString);
        return propertyList;
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

    protected List<Property> readPropertiesFromJson(String jsonString) {
        List<Property> propertyList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(PROPERTIES_ARRAY_NODE_NAME);
            int jsonArraySize = jsonArray.length();

            for(int index = 0; index < jsonArraySize; ++index) {
                JSONObject childObject = jsonArray.getJSONObject(index);
                Property property = JSonPropertyBuilder.createPropertyFromSearchResultJson(childObject);
                propertyList.add(property);
            }

        } catch (JSONException exc) {
            Log.e(this.getClass().getName(), "Exception", exc);
        }

        return propertyList;
    }
}
