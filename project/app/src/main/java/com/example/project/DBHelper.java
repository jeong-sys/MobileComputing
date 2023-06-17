package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "tripManager";
    private static DBHelper instance;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRIP_RECORD_TABLE = "CREATE TABLE trip_records ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "total_cost INTEGER )";

        String CREATE_TRIP_ITEMS_TABLE = "CREATE TABLE trip_items ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "trip_record_id INTEGER, " +
                "location TEXT, " +
                "image_uri TEXT, " +
                "cost INTEGER, " +
                "note TEXT, " +
                "FOREIGN KEY(trip_record_id) REFERENCES trip_records(id) )";

        db.execSQL(CREATE_TRIP_RECORD_TABLE);
        db.execSQL(CREATE_TRIP_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS trip_records");
        db.execSQL("DROP TABLE IF EXISTS trip_items");
        this.onCreate(db);
    }

    public long insertTripRecord(TripRecord tripRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", tripRecord.getTitle());
        values.put("start_date", tripRecord.getStartDate());
        values.put("end_date", tripRecord.getEndDate());
        values.put("total_cost", tripRecord.getTotalCost());

        long id = db.insert("trip_records", null, values);
        db.close();

        return id;
    }

    public long insertTripItem(TripItem tripItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("trip_record_id", tripItem.getTripRecordId());
        values.put("location", tripItem.getLocation());
        values.put("image_uri", tripItem.getImageUri());
        values.put("cost", tripItem.getCost());
        values.put("note", tripItem.getNote());

        long id = db.insert("trip_items", null, values);
        db.close();

        return id;
    }

    public List<TripRecord> getAllTripRecords() {
        List<TripRecord> tripRecords = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM trip_records", null);

        if (cursor.moveToFirst()) {
            do {
                TripRecord tripRecord = new TripRecord();
                tripRecord.setId(cursor.getInt(0));
                tripRecord.setTitle(cursor.getString(1));
                tripRecord.setStartDate(cursor.getString(2));
                tripRecord.setEndDate(cursor.getString(3));
                tripRecord.setTotalCost(cursor.getInt(4));

                tripRecords.add(tripRecord);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tripRecords;
    }

    public List<TripItem> getTripItemsByRecordId(int recordId) {
        List<TripItem> tripItems = new ArrayList<>();

        String query = "SELECT * FROM trip_items WHERE trip_record_id = " + recordId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TripItem tripItem = null;
        if (cursor.moveToFirst()) {
            do {
                tripItem = new TripItem();
                tripItem.setId(Integer.parseInt(cursor.getString(0)));
                tripItem.setTripRecordId(cursor.getInt(1));
                tripItem.setLocation(cursor.getString(2));
                tripItem.setImageUri(cursor.getString(3));
                tripItem.setCost(cursor.getInt(4));
                tripItem.setNote(cursor.getString(5));

                tripItems.add(tripItem);
            } while (cursor.moveToNext());
        }

        return tripItems;
    }

    public TripRecord getTripRecordById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("trip_records", new String[] { "id", "title", "start_date", "end_date", "total_cost" },
                "id=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            TripRecord tripRecord = new TripRecord();
            tripRecord.setId(Integer.parseInt(cursor.getString(0)));
            tripRecord.setTitle(cursor.getString(1));
            tripRecord.setStartDate(cursor.getString(2));
            tripRecord.setEndDate(cursor.getString(3));
            tripRecord.setTotalCost(Integer.parseInt(cursor.getString(4)));
            cursor.close();
            return tripRecord;
        } else {
            cursor.close();
            return null;
        }
    }
    public void deleteTripRecord(int tripRecordId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("trip_items", "trip_record_id = ?",
                new String[]{String.valueOf(tripRecordId)});

        db.delete("trip_records", "id = ?",
                new String[]{String.valueOf(tripRecordId)});

        db.close();
    }
}