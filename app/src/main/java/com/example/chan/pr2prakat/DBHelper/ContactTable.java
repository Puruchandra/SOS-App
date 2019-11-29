package com.example.chan.pr2prakat.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContactTable {

    private final static String TABLE_NAME = "CONTACTS";
    private final static String COL_1 = "CID";
    private final static String COL_2 = "NAME";
    private final static String COL_3 = "CONTACT";
    private final static String COL_4 = "ID";

    public static long addContacts(SQLiteDatabase db, String contactName, long contactNo, int ID) {
        ContentValues cv = new ContentValues();
        cv.put("NAME", contactName);
        cv.put("CONTACT", contactNo);
        cv.put("ID", ID);

        long res = db.insert(TABLE_NAME, null, cv);
        db.close();
        return res;

    }

    public static Cursor fetchContact(SQLiteDatabase db, int ID_user) {

        String cols[] = {"CID", "NAME", "CONTACT", "ID"};
        String selection = "ID = ?";
        String selectionArgs[] = {"" + ID_user};
        Cursor cursor = db.query(TABLE_NAME, cols, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        db.close();
        if (count > 0)
            return cursor;
        else
            return cursor;
    }

    public static int deleteContact(SQLiteDatabase db, int ID) {
        String[] whereArgs = {"" + ID};
        int res = db.delete(TABLE_NAME, "CID =?", whereArgs);
        db.close();
        return res;
    }

    public static int updateContact (SQLiteDatabase db, int CID, ContentValues cv){
        String[] whereArgs = { "" + CID };
        int res = db.update(TABLE_NAME,cv,"CID =?",whereArgs);
        db.close();
        return res;

    }
}
