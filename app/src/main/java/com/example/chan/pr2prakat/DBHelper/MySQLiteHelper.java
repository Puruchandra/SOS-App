package com.example.chan.pr2prakat.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "PrDb1.db";
    public final static String TABLE_NAME = "usertable";

    public final static String TABLE_NAME_CONTACTS = "CONTACTS";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);     //database name is PrDb and version is 2
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE usertable (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, password TEXT)");             //this will execute the string query "createTable" from UserTable

        db.execSQL("CREATE TABLE CONTACTS(CID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,CONTACT INTEGER,ID INTEGER, FOREIGN KEY(ID) REFERENCES usertable(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {  //dropping the table if version of db is changed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONTACTS);
        onCreate(sqLiteDatabase);
    }

}

