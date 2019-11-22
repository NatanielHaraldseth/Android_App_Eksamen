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
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_ARSTALL, arstall);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    public String getArstall() {
        Cursor c = minDb.rawQuery("SELECT årstall FROM favoritt_sted ", null);
        String arstall = "";
        if (c.moveToFirst()){
            do {
                // Passing values
                arstall = c.getString(c.getColumnIndex("årstall"));
                // Do something Here with values
                Log.d(TAG, "selectData: " + arstall);
            } while(c.moveToNext());
        }
        c.close();
        return arstall;
    }
}
