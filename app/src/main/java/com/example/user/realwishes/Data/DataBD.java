package com.example.user.realwishes.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 24.05.2017.
 */

public class DataBD extends SQLiteOpenHelper {

    private static final String dbName = "DataBD";
    private static final int dbVersion = 1;

    public static final String tableDate = "DATA";
    public static final String dateH = "HOUR";
    public static final String dateM = "MINUTE";


    public DataBD(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requestCreateDb = "create table "+tableDate+" ("+
                dateH+" integer ," +
                dateM+" integer )";

        db.execSQL(requestCreateDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}