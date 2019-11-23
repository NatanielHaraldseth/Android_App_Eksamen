package com.example.android_app_eksamen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHjelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAVN        = "nytt_favorittsted.db";
    //
    public static final String TABLE_NAME           = "favoritt_sted";
    //
    public static final String COL_POST_STED        = "post_sted";
    public static final String COL_TOGGLE_STATE     = "toggle_state";
    public static final String COL_POST_NR          = "post_nr";
    public static final String COL_ARSTALL          = "årstall";

    public DatabaseHjelper(Context context) {
        super(context, DATABASE_NAVN, null, 1);
    }

    /**
     * Metode for og opprette lokal Sqlite DB
     **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(favoritt_sted_id integer PRIMARY KEY AUTOINCREMENT, post_sted text,post_nr integer,årstall text, toggle_state text)");
    }

    /**
     * Metode for og oppgradere lokal Sqlite DB.
     * Brukes i samarbeid med funksjon i DatabaseAccess
     *
     * @param db
     * @param gammelVersjon
     * @param nyVersjon*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int gammelVersjon, int nyVersjon) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
