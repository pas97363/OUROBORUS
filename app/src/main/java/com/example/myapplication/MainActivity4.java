package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity4 extends AppCompatActivity {
    User u = new User();
    DBHelper mdb = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setTitle("Option Window");
        Intent i = getIntent();
        u.phone = i.getStringExtra("FROM");
        //mdb.deletetable();//
    }

    public void sending(View view) {
        Intent i0 = new Intent(this, MainActivity2.class);
        i0.putExtra("FROM",u.phone);
        startActivity(i0);
    }

    public void receiving(View view) {
        Intent i1 = new Intent(this, MainActivity3.class);
        i1.putExtra("FROM",u.phone);
        startActivity(i1);
    }
}