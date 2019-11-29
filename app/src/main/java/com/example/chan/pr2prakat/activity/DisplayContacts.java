package com.example.chan.pr2prakat.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chan.pr2prakat.DBHelper.ContactTable;
import com.example.chan.pr2prakat.DBHelper.MySQLiteHelper;
import com.example.chan.pr2prakat.R;
import com.example.chan.pr2prakat.adapter.ContactAdapter;
import com.example.chan.pr2prakat.adapter.ContactPojo;

import java.util.ArrayList;

public class DisplayContacts extends AppCompatActivity {

    ListView contactListView;
    ContactPojo contactPojo;
    ArrayList<ContactPojo> contactArrayList = new ArrayList<>();
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);

        init();
        int ID_User = preferences.getInt("ID", 0);

        MySQLiteHelper helper = new MySQLiteHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = ContactTable.fetchContact(db,ID_User);

        int columnCid = cursor.getColumnIndex("CID");
        int columnName = cursor.getColumnIndex("NAME");
        int columnContact = cursor.getColumnIndex("CONTACT");
        int columnID = cursor.getColumnIndex("ID");

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                contactPojo = new ContactPojo();
                contactPojo.setCID(cursor.getInt(columnCid));
                contactPojo.setContactNamePojo(cursor.getString(columnName));
                contactPojo.setContactNoPojo(cursor.getInt(columnContact));
                contactPojo.setID(cursor.getInt(columnID));

                contactArrayList.add(contactPojo);
                Log.d("CheckArray",""+contactPojo.toString());

            }
        } else
            Toast.makeText(this, "No Contacts", Toast.LENGTH_SHORT).show();


        ContactAdapter contactAdapter = new ContactAdapter(this, R.layout.contact_list_item, contactArrayList);
        contactListView.setAdapter(contactAdapter);
    }

    private void init() {
        contactListView = findViewById(R.id.contactsListView);
        preferences = getSharedPreferences("session",MODE_PRIVATE);

    }
}
