package com.example.user.realwishes.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 09.04.2017.
 */

public class DreamBD extends SQLiteOpenHelper {

    private static final String dbName = "DreamBD";
    private static final int dbVersion = 2;

    public static final String tableDream = "DREAM";
    public static final String DreamIm = "IMAGINE";
    public static final String DreamDream = "DREAM";
    public static final String DreamScore = "SCORE";


    public DreamBD(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requestCreateDb = "create table "+tableDream+" ("+
                DreamDream+" text ," +
                DreamIm+" text ," +
                DreamScore+" integer )";

        db.execSQL(requestCreateDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}