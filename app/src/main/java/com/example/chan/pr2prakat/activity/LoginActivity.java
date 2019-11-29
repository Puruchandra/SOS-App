package com.example.chan.pr2prakat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chan.pr2prakat.DBHelper.MySQLiteHelper;
import com.example.chan.pr2prakat.DBHelper.UserTable;
import com.example.chan.pr2prakat.R;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;
    EditText editLogin, editPassword;
    MySQLiteHelper mySQLiteHelper;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();  //initialize all the components
        editor.clear();
        if (isUserLogin()) {
            Intent homeIntent = new Intent(LoginActivity.this, Home.class);
            startActivity(homeIntent);
            finish();

        }

        listener();

    }

    private boolean isUserLogin() {

        return preferences.getBoolean("FLAG_LOGIN", false);   //defValue is false so that user need to log in atleaset Once
    }



    private void listener() {
        //handling click events on login btn and register btn

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredUserName = editLogin.getText().toString().trim();
                String enteredPassword = editPassword.getText().toString().trim();
                SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();
                Cursor cursor = UserTable.checkUser(db,enteredUserName, enteredPassword);//calling the checkUser() of MySQLiteHelper for user validation
                int count = cursor.getCount();
                String nameOfUser = null;
                int ID = 0;
                if (count>0) {
                    storeSession(); //store user session so that user don't need to login everytime

                    while (cursor.moveToNext()){
                        ID = cursor.getInt(0);
                        nameOfUser = cursor.getString(1);

                        Log.d("checkres", "onClick: "+nameOfUser);
                    }

                    Intent homeIntent = new Intent(LoginActivity.this, Home.class);
                    editor.putInt("ID",ID);
                    editor.putString("NameOfUser",nameOfUser);
                    editor.commit();
                    startActivity(homeIntent);
                    finish();

                } else
                    Snackbar.make(view, "Wrong Username or Password", Snackbar.LENGTH_LONG).show();
            }

            private void storeSession() {
                //once the authentication is verified, we store the session with the help of SP in session file
                editor.putBoolean("FLAG_LOGIN", true);
                editor.commit();
            }


        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user clicks on register button and we redirect him/her on Registration activity
                Intent regIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(regIntent);
            }
        });
    }

    private void init() {
        //initialize everything
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        mySQLiteHelper = new MySQLiteHelper(this);
        preferences = getSharedPreferences("session",MODE_PRIVATE);
        editor=preferences.edit();
    }
}
