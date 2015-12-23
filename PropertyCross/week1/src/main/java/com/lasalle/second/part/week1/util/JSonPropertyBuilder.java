package com.lasalle.second.part.week1.util;

import com.lasalle.second.part.week1.model.Property;

import org.json.JSONException;
import org.json.JSONObject;

public class JSonPropertyBuilder {

    public static final String RENT_NODE_NAME = "alquiler";
    public static final String FAVOURITE_NODE_NAME = "favorito";
    public static final String PROPERTY_ID_NODE_NAME = "idPropiedad";
    public static final String LATITUDE_NODE_NAME = "latitud";
    public static final String LONGITUDE_NODE_NAME = "longitud";
    public static final String SQUARE_FOOTAGE_NODE_NAME = "metros";
    public static final String PRICE_NODE_NAME = "precio";
    public static final String NAME_NODE_NAME = "titulo";


    public static Property createPropertyFromSearchResultJson(JSONObject jsonObject) throws JSONException {
        Property property = new Property();

        property.setRent(jsonObject.getBoolean(RENT_NODE_NAME));
        property.setFavourite(jsonObject.getBoolean(FAVOURITE_NODE_NAME));
        property.setId(jsonObject.getString(PROPERTY_ID_NODE_NAME));
        property.setLatitude(JSonUtils.getFloat(jsonObject, LATITUDE_NODE_NAME));
        property.setLongitude(JSonUtils.getFloat(jsonObject, LONGITUDE_NODE_NAME));
        property.setSquareFootage(JSonUtils.getFloat(jsonObject, SQUARE_FOOTAGE_NODE_NAME));
        property.setPrice(JSonUtils.getFloat(jsonObject, PRICE_NODE_NAME));
        property.setName(jsonObject.getString(NAME_NODE_NAME));

        return property;
    }

}
