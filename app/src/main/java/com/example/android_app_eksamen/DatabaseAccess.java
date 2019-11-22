package com.example.android_app_eksamen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAccess {
    private DatabaseHjelper minDbHjelper;
    private SQLiteDatabase minDb;

    private final Context mContext;

    private static final String TAG = "DatabaseAccess";

    public DatabaseAccess(Context mContext) {
        this.mContext = mContext;
    }

    public DatabaseAccess open() throws SQLException {
        minDbHjelper = new DatabaseHjelper(mContext);
        minDb = minDbHjelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if(minDbHjelper!=null){
            minDbHjelper.close();
        }
    }

    public void upgrade() throws SQLException {
        minDbHjelper = new DatabaseHjelper(mContext); //open
        minDb = minDbHjelper.getWritableDatabase();
        minDbHjelper.onUpgrade(minDb, 1, 0);
    }

    public void settInnPoststed(String poststed) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_POST_STED, poststed);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    public void settInnPostnr(String postnr) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_POST_NR, postnr);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    public void settInnArstall(String arstall) {
        if (!arstall.isEmpty()) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(DatabaseHjelper.COL_ARSTALL, arstall);
            minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
        }
    }

    public void settInnBryterTilstand(String tilstand) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_TOGGLE_STATE, tilstand);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    public String getArstall() {
        Cursor c = minDb.rawQuery("SELECT årstall FROM favoritt_sted WHERE årstall IS NOT NULL", null);
        String arstall = "";
        if (c.moveToFirst()){
            do {
                arstall = c.getString(c.getColumnIndex("årstall"));
                Log.d(TAG, "getArstall: " + arstall);
            } while(c.moveToNext());
        }
        c.close();
        return arstall;
    }

    public String getBryterTilstand() {
        Cursor c = minDb.rawQuery("SELECT toggle_state FROM favoritt_sted ", null);
        String tilstand = "";
        if (c.moveToFirst()){
            do {
                tilstand = c.getString(c.getColumnIndex("toggle_state"));
                Log.d(TAG, "getBryterTilstand: " + tilstand);
            } while(c.moveToNext());
        }
        c.close();
        return tilstand;
    }
}
