package com.lasalle.second.part.week1.model;

public class SearchHistory {

    private String query;
    private boolean rent;
    private boolean sell;
    private String rawResults;
    private int rentResults;
    private int sellResults;
    private long timestamp;


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRawResults() {
        return rawResults;
    }

    public void setRawResults(String rawResults) {
        this.rawResults = rawResults;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public int getRentResults() {
        return rentResults;
    }

    public void setRentResults(int rentResults) {
        this.rentResults = rentResults;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public int getSellResults() {
        return sellResults;
    }

    public void setSellResults(int sellResults) {
        this.sellResults = sellResults;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
