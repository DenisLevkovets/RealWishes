package com.example.user.realwishes.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 18.05.2017.
 */

public class MainBD extends SQLiteOpenHelper {

    private static final String dbName = "MainBD";
    private static final int dbVersion = 1;

    public static final String tableMain = "MAIN";
    public static final String mainWall = "WALL";




    public MainBD(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requestCreateDb = "create table "+tableMain+" ("+

                mainWall+" integer)";

        db.execSQL(requestCreateDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
