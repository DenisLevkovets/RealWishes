package com.example.user.realwishes.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 18.05.2017.
 */

public class ClockssBD extends SQLiteOpenHelper {

    private static final String dbName = "ClockssBD";
    private static final int dbVersion = 1;

    public static final String tableClock = "CLOCK";
    public static final String clockWish = "WISH";
    public static final String clockStartOrNot = "STARTORNOT";
    public static final String clockProgressAs = "PROGRESSAS";
    public static final String clockCountAs = "COUNTAS";



    public ClockssBD(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requestCreateDb = "create table "+tableClock+" ("+
                clockWish+" text ," +
                clockStartOrNot+" integer ," +
                clockProgressAs+" integer ," +
                clockCountAs+" integer)";

        db.execSQL(requestCreateDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
