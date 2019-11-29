package com.example.chan.pr2prakat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chan.pr2prakat.DBHelper.ContactTable;
import com.example.chan.pr2prakat.DBHelper.MySQLiteHelper;
import com.example.chan.pr2prakat.R;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class Home extends AppCompatActivity {
    TextView userNameTextView;
    SpeedDialView fabSpeedDial;
    Button logoutBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int ID_OF_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userNameTextView = findViewById(R.id.userNameTextView);

        init();

        String nameOfUser = preferences.getString("NameOfUser", null);
        ID_OF_USER = preferences.getInt("ID", 0);
        userNameTextView.append(" " + nameOfUser);

        listener();

    }



    private void listener() {



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("FLAG_LOGIN", false);
                editor.commit();

                Intent loginIntent = new Intent(Home.this, LoginActivity.class);

                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
            }
        });

        fabSpeedDial.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                int item_id = actionItem.getId();
                if (item_id == R.id.itemAddContact) {
                    addContactToDb();
                } else if (item_id == R.id.itemDeleteContact) {
                    Toast.makeText(Home.this, "Delete Clicked", Toast.LENGTH_SHORT).show();
                    deleteContactFromDb();
                } else if (item_id == R.id.itemShowContact) {
                    showContactFromDb();
                }

                return true; 
            }

            private void showContactFromDb() {
                Intent showContactIntent = new Intent(Home.this, DisplayContacts.class);
                startActivity(showContactIntent);
            }

            private void addContactToDb() {
                final MySQLiteHelper helper = new MySQLiteHelper(Home.this);
                final SQLiteDatabase db = helper.getWritableDatabase();
                final Dialog dialog = new Dialog(Home.this);
                dialog.setContentView(R.layout.dialog_add_contact);
                dialog.show();
                final EditText contactNameEdt = dialog.findViewById(R.id.contactNameEdt);
                final EditText contactNoEdt = dialog.findViewById(R.id.contactNoEdt);
                Button saveBtn = dialog.findViewById(R.id.saveBtn);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            String contactName = contactNameEdt.getText().toString().trim();
                            String contactNoSt = contactNoEdt.getText().toString();
                            if (!contactName.isEmpty()&&!contactNoSt.isEmpty()) {
                                if (contactNoSt.length()>9 && contactNoSt.length()<11){

                                    long contactNo =  Long.parseLong(contactNoSt);
                                    long res = ContactTable.addContacts(db,contactName, contactNo, ID_OF_USER);
                                    if (res > 0) {
                                        Toast.makeText(Home.this, "Contact Added " + res, Toast.LENGTH_SHORT).show();
                                        contactNameEdt.setText("");
                                        contactNoEdt.setText("");
                                    } else
                                        Toast.makeText(Home.this, "Cannot Add", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(Home.this, "Please enter a valid phone no", Toast.LENGTH_SHORT).show();

                            }
                            dialog.cancel();
                    }
                });


            }

            private void deleteContactFromDb() {


            }
        });

    }

    private void init() {
        logoutBtn = findViewById(R.id.logoutBtn);
        fabSpeedDial = findViewById(R.id.fabSpeedDial);
        fabSpeedDial.inflate(R.menu.fab_menu);
        preferences = getSharedPreferences("session", MODE_PRIVATE);
        editor = preferences.edit();
    }

}
