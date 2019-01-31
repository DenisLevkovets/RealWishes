package com.example.user.realwishes.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 09.04.2017.
 */

public class ProfilesBD extends SQLiteOpenHelper {

    private static final String dbName = "ProfilesBD";
    private static final int dbVersion = 1;

    public static final String tableProfile = "PROFILE";
    public static final String profileAva = "AVA";



    public ProfilesBD(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requestCreateDb = "create table "+tableProfile+" ("+
                profileAva+" text)";

        db.execSQL(requestCreateDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}