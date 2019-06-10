package com.example.paweek.jjump.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "JumpGame.db";
    public static final Integer DATABASE_VERSION = 1;
    public static final String CREATE_TABLE_RESULTS =
            "CREATE TABLE " + ResultsTable.TABLE_NAME + " ("
                    + ResultsTable._ID + " INTEGER PRIMARY KEY,"
                    + ResultsTable.PLAYER + " TEXT,"
                    + ResultsTable.POINTS + " INTEGER)";
    public static final String DROP_TABLE_RESULTS = "DROP TABLE IF EXISTS " + ResultsTable.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_RESULTS);
        onCreate(db);
    }
}
