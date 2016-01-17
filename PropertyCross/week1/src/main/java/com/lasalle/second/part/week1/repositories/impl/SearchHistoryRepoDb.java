package com.lasalle.second.part.week1.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.lasalle.second.part.week1.model.PropertySearch;
import com.lasalle.second.part.week1.model.SearchHistory;
import com.lasalle.second.part.week1.repositories.SearchHistoryRepo;
import com.lasalle.second.part.week1.util.DatabaseHandler;
import com.lasalle.second.part.week1.util.DatabaseUtils;

public class SearchHistoryRepoDb implements SearchHistoryRepo {

    private static final  String TABLE_NAME = "searchHistory";

    private static final  String DB_QUERY = "query";
    private static final  String DB_RENT = "rent";
    private static final  String DB_SELL = "sell";
    private static final  String DB_RESULTS = "rawResults";
    private static final  String DB_NUM_RENT_RESULTS = "rentResults";
    private static final  String DB_NUM_SELL_RESULTS = "sellResults";
    private static final  String DB_TIMESTAMP = "timestamp";

    private static final String[] TABLE_COLUMNS = new String[]{DB_QUERY, DB_RENT, DB_SELL, DB_RESULTS,
            DB_NUM_RENT_RESULTS, DB_NUM_SELL_RESULTS, DB_TIMESTAMP};

    private Context context;

    public SearchHistoryRepoDb(Context context) {
        this.context = context;
    }

    @Override
    public boolean addSearchResult(PropertySearch propertySearch, int rentResults, int sellResults,
                                String rawResults) {
        boolean success = true;

        ContentValues contentValues = createContentValuesFromSearch(propertySearch, rentResults,
                sellResults, rawResults);

        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        long insertId = database.insert(TABLE_NAME, "null", contentValues);

        success = (insertId != -1);

        return success;
    }

    @Override
    public SearchHistory getSearchHistoryFromSearch(PropertySearch propertySearch) {
        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        Cursor cursor =  database.query(
                TABLE_NAME,
                TABLE_COLUMNS,
                DB_RENT + "=?",
                new String[]{getQueryFromSearch(propertySearch)},
                null, //groupBy
                null, //having
                "timestamp DESC",
                "1");

        return getSearchHistoryFromCursor(cursor);
    }

    protected ContentValues createContentValuesFromSearch(PropertySearch propertySearch, int rentResults,
                                                        int sellResults, String rawResults) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_QUERY, getQueryFromSearch(propertySearch));
        contentValues.put(DB_RENT, propertySearch.isRent());
        contentValues.put(DB_SELL, propertySearch.isSell());
        contentValues.put(DB_RESULTS, rawResults);
        contentValues.put(DB_NUM_RENT_RESULTS, rentResults);
        contentValues.put(DB_NUM_SELL_RESULTS, sellResults);

        final long currentTimestamp = System.currentTimeMillis() / 1000L;
        contentValues.put(DB_TIMESTAMP, currentTimestamp);

        return contentValues;
    }

    protected String getQueryFromSearch(PropertySearch propertySearch) {
        String query = propertySearch.getQuery();

        if(query.isEmpty()) {
            query = propertySearch.getLatitude().toString() + "," + propertySearch.getLongitude().toString();
        }

        return query;
    }

    protected SearchHistory getSearchHistoryFromCursor(Cursor cursor) {
        SearchHistory searchHistory = null;

        if(cursor.getCount() != 0 && cursor.moveToFirst()) {
            searchHistory = new SearchHistory();
            searchHistory.setQuery(cursor.getString(cursor.getColumnIndex(DB_QUERY)));
            searchHistory.setRent(DatabaseUtils.getBooleanValue(cursor, DB_RENT));
            searchHistory.setSell(DatabaseUtils.getBooleanValue(cursor, DB_SELL));
            searchHistory.setRawResults(cursor.getString(cursor.getColumnIndex(DB_RESULTS)));
            searchHistory.setRentResults(cursor.getInt(cursor.getColumnIndex(DB_NUM_RENT_RESULTS)));
            searchHistory.setSellResults(cursor.getInt(cursor.getColumnIndex(DB_NUM_SELL_RESULTS)));
            searchHistory.setTimestamp(cursor.getLong(cursor.getColumnIndex(DB_TIMESTAMP)));
        }

        cursor.close();

        return searchHistory;
    }

}
