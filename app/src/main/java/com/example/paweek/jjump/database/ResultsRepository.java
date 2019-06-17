package com.example.paweek.jjump.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResultsRepository {

    private DbHelper dbHelper;

    public ResultsRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void saveResult(Result result) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ResultsTable.POINTS, result.getPoints());
        cv.put(ResultsTable.PLAYER, result.getPlayer());
        db.insert(ResultsTable.TABLE_NAME, null, cv);
        db.close();
    }

    public Result getBestResult() {
        Result result = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(ResultsTable.TABLE_NAME, new String[]{ResultsTable.PLAYER, "MAX(" + ResultsTable.POINTS + ")"}, null, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            result = new Result(c.getString(0), c.getInt(1));
        }
        c.close();
        db.close();
        return result;
    }
/*
    public ArrayList<Result> getTop25Results() {
        ArrayList<Result> results = new ArrayList<Result>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(ResultsTable.TABLE_NAME, new String[]{ResultsTable.PLAYER, ResultsTable.POINTS}, "", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            result = new Result(c.getString(0), c.getInt(1));
        }
        c.close();
        db.close();
        return result;
    } */
}
