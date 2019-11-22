package com.example.android_app_eksamen;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private DatabaseHjelper minDbHelper;
    private SQLiteDatabase minDb;

    private final Context mCtx;

    // der adapteret brukes
    public DatabaseAccess(Context ctx) {
        this.mCtx = ctx;
    }

    public DatabaseAccess open() throws SQLException {
        minDbHelper = new DatabaseHjelper(mCtx);
        minDb = minDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if(minDbHelper!=null){
            minDbHelper.close();
        }
    }
}
