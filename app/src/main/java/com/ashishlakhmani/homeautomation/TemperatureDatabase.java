package com.ashishlakhmani.homeautomation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TemperatureDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "TemperatureStore";
    private static final String TABLE_NAME = "details";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_DATE_TIME = "date_time";

    public TemperatureDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_TEMPERATURE + " TEXT," + COLUMN_HUMIDITY + " TEXT," +
                COLUMN_DATE_TIME + " TEXT" + ")";


        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add new row to database
    public void addUserDetails(TemperatureDetails temperatureDetails) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEMPERATURE, temperatureDetails.getTemperature());
        cv.put(COLUMN_HUMIDITY, temperatureDetails.getHumidity());
        cv.put(COLUMN_DATE_TIME, temperatureDetails.getDateTime());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }


    //get MessageObject list from local database
    public List<TemperatureDetails> getTemperatureDetails() {
        List<TemperatureDetails> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            TemperatureDetails temperatureDetails = new TemperatureDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            list.add(temperatureDetails);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.close();
    }
}
