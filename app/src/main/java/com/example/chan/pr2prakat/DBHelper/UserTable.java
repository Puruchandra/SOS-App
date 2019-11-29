package com.example.chan.pr2prakat.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserTable {

    public final static String TABLE_NAME = "usertable";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "name";
    public final static String COL_3 = "username";
    public final static String COL_4 = "password";


    public static Cursor checkUser(SQLiteDatabase db,String uname, String pass) {

        String[] cols = {COL_1,COL_2};
        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = { uname, pass };
        Cursor cursor = db.query(TABLE_NAME, cols, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();

        db.close();

        if (count > 0)
            return cursor;

        else
            return cursor;
    }



    public static long addUser(SQLiteDatabase db,String name, String username, String password) {

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("username", username);
        cv.put("password", password);
        long res = db.insert(TABLE_NAME, null, cv);
        db.close();

        return res;
    }

}
