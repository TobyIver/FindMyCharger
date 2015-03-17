package com.app.toby.findmycharger;

/**
 * Created by Toby on 12/5/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataSource {

    // Database fields
    public static SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private static String[] allColumns = { MySQLiteHelper.Row_ID,
            MySQLiteHelper.dateCol, MySQLiteHelper.longCol, MySQLiteHelper.latCol };

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }



    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static Data createData(String date, String log ,String lat) {
        ContentValues values = new ContentValues();
        Random rn = new Random();
        long id = rn.nextLong();
        values.put(MySQLiteHelper.Row_ID, id);
        values.put(MySQLiteHelper.dateCol, date);
        values.put(MySQLiteHelper.longCol, log);
        values.put(MySQLiteHelper.latCol, lat);

        database.insert(MySQLiteHelper.DB_TABLE,null,values);



        Cursor cursor = database.query(MySQLiteHelper.DB_TABLE,
                allColumns, MySQLiteHelper.Row_ID + " = " + id, null,
                null, null, null);




        cursor.moveToFirst();
        Data newData = cursorToData(cursor);
        cursor.close();

        return newData;
    }

    public void deleteComment(Data comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.DB_TABLE, MySQLiteHelper.Row_ID
                + " = " + id, null);
    }

    public List<Data> getAllData() {
        List<Data> comments = new ArrayList<Data>();


            Cursor cursor = database.query(MySQLiteHelper.DB_TABLE,
                    allColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Data comment = cursorToData(cursor);
                comments.add(comment);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return comments;

    }

    private static Data cursorToData(Cursor cursor) {
        Data data = new Data();
        data.setId(cursor.getLong(0));
        data.setDate(cursor.getString(1));
        data.setLong(cursor.getString(2));
        data.setLat(cursor.getString(3));
        return data;
    }

}