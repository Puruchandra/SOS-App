package com.example.chan.pr2prakat.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chan.pr2prakat.DBHelper.MySQLiteHelper;
import com.example.chan.pr2prakat.DBHelper.UserTable;
import com.example.chan.pr2prakat.R;

@SuppressWarnings("ALL")
public class RegistrationActivity extends AppCompatActivity {
    FloatingActionButton fabRegisterBtn;
    EditText editTextUserName, editTextName, editTextPassword;
    MySQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        init();


        listener();

    }

    private void init() {
        fabRegisterBtn = findViewById(R.id.fabRegisterBtn);
        editTextName = findViewById(R.id.editTextName);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        mySQLiteHelper = new MySQLiteHelper(this);
    }

    private void listener() {

        fabRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!name.equals("") && !username.equals("") && !password.equals("")) {
                    SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();
                    long res = UserTable.addUser(db,name,username,password);
                    if(res>0){
                        editTextName.setText("");
                        editTextPassword.setText("");
                        editTextUserName.setText("");
                        Snackbar.make(view, "Account Created! Go Back and Login", Snackbar.LENGTH_LONG).show();
                    }
                    else
                        Snackbar.make(view, "Error Creating Account", Snackbar.LENGTH_LONG).show();

                }
                else
                    Snackbar.make(view, "Fill All Details!", Snackbar.LENGTH_LONG).show();

            }
        });
    }

}
