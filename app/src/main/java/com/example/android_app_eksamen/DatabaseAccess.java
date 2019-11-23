package com.example.android_app_eksamen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAccess {
    private DatabaseHjelper minDbHjelper;
    private SQLiteDatabase minDb;

    private final Context mContext;

    private static final String TAG = "DatabaseAccess";

    public DatabaseAccess(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Metode for og åpne tilgang til lokal Sqlite DB
     *
     * @return context/this*/
    public DatabaseAccess open() throws SQLException {
        minDbHjelper = new DatabaseHjelper(mContext);
        minDb = minDbHjelper.getWritableDatabase();
        return this;
    }

    /**
     * Metode for og lukke tilgangen til lokal Sqlite DB
     **/
    public void close() {
        if(minDbHjelper!=null){
            minDbHjelper.close();
        }
    }

    /**
     * Metode for og oppgradere versjon av lokal Sqlite DB
     **/
    public void upgrade() throws SQLException {
        minDbHjelper = new DatabaseHjelper(mContext); //open
        minDb = minDbHjelper.getWritableDatabase();
        minDbHjelper.onUpgrade(minDb, 1, 0);
    }

    /**
     * Metode for og sette inn poststed i lokal Sqlite DB
     *
     * @param poststed*/
    public void settInnPoststed(String poststed) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_POST_STED, poststed);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    /**
     * Metode for og sette inn postNr i lokal Sqlite DB
     *
     * @param postnr*/
    public void settInnPostnr(String postnr) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_POST_NR, postnr);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    /**
     * Metode for og sette inn Årstall i lokal Sqlite DB
     *
     * @param arstall*/
    public void settInnArstall(String arstall) {
        if (!arstall.isEmpty()) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(DatabaseHjelper.COL_ARSTALL, arstall);
            minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
        }
    }

    /**
     * Metode for og sette inn bryter tilstand(true/false) i lokal Sqlite DB
     *
     * @param tilstand*/
    public void settInnBryterTilstand(String tilstand) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHjelper.COL_TOGGLE_STATE, tilstand);
        minDb.insert(DatabaseHjelper.TABLE_NAME, null, initialValues);
    }

    /**
     * Metode for og hente ut årstall fra lokal Sqlite DB
     *
     * @return arstall*/
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

    /**
     * Metode for og hente bryter tilstand (true/false) fra lokal Sqlite DB
     *
     * @return tilstand*/
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
