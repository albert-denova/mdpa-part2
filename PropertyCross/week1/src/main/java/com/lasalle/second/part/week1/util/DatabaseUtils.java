package com.lasalle.second.part.week1.util;

import android.database.Cursor;

/**
 * Created by albert.denova on 16/01/16.
 */
public class DatabaseUtils {

    public static boolean getBooleanValue(Cursor cursor, String columnName) {
        boolean value = false;

        final int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex != -1) {
            final int rowValue = cursor.getInt(columnIndex);
            value = (rowValue != 0);
        }

        return value;
    }

}
