package com.example.android_app_eksamen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHjelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAVN        = "favorittsted.db";
    //
    public static final String TABLE_NAME           = "favoritt_sted";
    //
    public static final String COL_ID               = "favoritt_sted_id";
    public static final String COL_POST_STED        = "post_sted";
    public static final String COL_POST_NR          = "post_nr";
    public static final String COL_ARSTALL          = "årstall";

    public DatabaseHjelper(Context context) {
        super(context, DATABASE_NAVN, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(favoritt_sted_id integer PRIMARY KEY AUTOINCREMENT, post_sted text,post_nr integer,årstall text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int gammelVersjon, int nyVersjon) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
