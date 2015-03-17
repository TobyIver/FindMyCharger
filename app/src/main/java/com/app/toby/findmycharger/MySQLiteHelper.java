package com.app.toby.findmycharger;

/**
 * Created by Toby on 12/2/2014.
 * using as refrence http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "chargelocationsdb";
    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "chargelocationstable";
    protected static final String dateCol = "date";
    protected static final String longCol = "longitude";
    protected static final String latCol = "latitude";
    private static final String DBTAG = "DatabaseManager" ;
    private static final String SQLTAG = "SQLHelper" ;
    public static final String Row_ID = "_id";

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + DB_TABLE + " ("+ Row_ID + " INTEGER, "  + dateCol +" TEXT, " + longCol +" TEXT, " + latCol + " TEXT);"  ;
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQLTAG, "Upgrade table - drop and recreate it");
        }

}

