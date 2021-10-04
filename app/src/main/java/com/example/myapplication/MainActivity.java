package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, contact;
    Button update, login, insert;
    DBHelper db;
    User u = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login Window");

        name = findViewById(R.id.editTextTextPersonName);
        contact = findViewById(R.id.editTextPhone);

        insert = findViewById(R.id.button2); //new
        update = findViewById(R.id.button4);
        login = findViewById(R.id.button3);

        db = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.name = name.getText().toString();
                u.phone = contact.getText().toString();

                Boolean checkinsertdata = db.insertuserdata(u.name.trim(),u.phone);
                if (checkinsertdata == true){
                    Toast.makeText(MainActivity.this, "Continue Login", Toast.LENGTH_LONG).show();
                    name.getText().clear();
                    contact.getText().clear();
                }
                else
                    Toast.makeText(MainActivity.this, "Retry Again", Toast.LENGTH_LONG).show();


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.name = name.getText().toString();
                u.phone = contact.getText().toString();

                Boolean checkupdatedata = db.updateuserdata(u.name,u.phone);
                if (checkupdatedata == true)
                    Toast.makeText(MainActivity.this, "Changed Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Retry Again", Toast.LENGTH_LONG).show();


            }
        });

        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.name = name.getText().toString();
                u.phone = contact.getText().toString();

                Boolean checkvaliddata = validate(u.name,u.phone);
                if (checkvaliddata == true){
                    Toast.makeText(MainActivity.this, "Welcome " + u.name, Toast.LENGTH_LONG).show();
                    Intent i = new Intent().setClassName("com.example.myapplication", "com.example.myapplication.MainActivity2");
                    i.putExtra("FROM", u.phone);
                    startActivity(i);
                }
                else
                    Toast.makeText(MainActivity.this, "Retry Again", Toast.LENGTH_LONG).show();


            }
        });*/
    }

    public void login(View v){

        EditText t = findViewById(R.id.editTextTextPersonName);
        t = findViewById(R.id.editTextPhone);

        u.name = name.getText().toString();
        u.phone = contact.getText().toString();

        if(validate(u.name,u.phone)){
        Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity4.class);
        i.putExtra("FROM",u.phone);
        startActivity(i);

        }

    }

    public boolean validate(String name, String phone) {
        boolean valid = true;
        Cursor cursor = db.getdata(name);

        /*String email = input_email.getText().toString();
        String password = input_password.getText().toString();*/

        if (name.isEmpty() || name.trim().length() < 4 || cursor.getCount() <= 0) {
            ((EditText)findViewById(R.id.editTextTextPersonName)).setError("Enter a valid username");
            valid = false;
        } else {
            ((EditText)findViewById(R.id.editTextTextPersonName)).setError(null);
        }

        if (phone.isEmpty() || phone.trim().length() > 10) {
            ((EditText)findViewById(R.id.editTextPhone)).setError("Equal to 10 numeric characters");
            valid = false;
        } else {
            ((EditText)findViewById(R.id.editTextPhone)).setError(null);
        }

        return valid;
    }
}