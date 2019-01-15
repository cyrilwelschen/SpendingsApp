package com.example.cyrilwelschen.spendingsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cyril on 14.01.19.
 * Database Helper to handle entries
 */


public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "spendings.dp";
    public static final String TABLE_NAME = "spendings";
    public static final String COL1 = "ID";
    public static final String COL2 = "CATEGORY";
    public static final String COL3 = "DATETIME";
    public static final String COL4 = "AMOUNT";
    public static final String COL5 = "COMMENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Datetime will have to saved in "YYYY-MM-DD HH:MM:SS.SSS" format as text
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " CATEGORY TEXT, DATETIME TEXT, AMOUNT REAL, COMMENT TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String category, String datetime, float amount, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, category);
        values.put(COL3, datetime);
        values.put(COL4, amount);
        values.put(COL5, comment);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor queryWholeDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC", null);
    }
}
