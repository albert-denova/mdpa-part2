package com.lasalle.second.part.week1.model;

import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by albert.denova on 22/12/15.
 */
public class Property {

    private String id;
    private String name;
    private boolean rent;
    private String cityName;
    private String postalCode;
    private String description;
    private String address;
    private String ownerMail;
    private float latitude;
    private float longitude;
    private float squareFootage;
    private float price;
    private String ownerPhoneNumber;
    private boolean favourite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerMail() {
        return ownerMail;
    }

    public void setOwnerMail(String ownerMail) {
        this.ownerMail = ownerMail;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(float squareFootage) {
        this.squareFootage = squareFootage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public static class PriceComparator implements Comparator<Property> {
        private boolean inverseOrder;

        public PriceComparator(boolean inverseOrder) {
            this.inverseOrder = inverseOrder;
        }

        @Override
        public int compare(Property lhs, Property rhs) {
            if(inverseOrder) {
                return new Float(rhs.getPrice() - lhs.getPrice()).intValue();
            }
            else {
                return new Float(lhs.getPrice() - rhs.getPrice()).intValue();
            }
        }
    }

    public static class FootageComparator implements Comparator<Property> {
        private boolean inverseOrder;

        public FootageComparator(boolean inverseOrder) {
            this.inverseOrder = inverseOrder;
        }

        @Override
        public int compare(Property lhs, Property rhs) {
            if(inverseOrder) {
                return new Float(rhs.getSquareFootage() - lhs.getSquareFootage()).intValue();
            }
            else {
                return new Float(lhs.getSquareFootage() - rhs.getSquareFootage()).intValue();
            }

        }
    }
}
