package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity2 extends AppCompatActivity {

    public User u = new User();
    public EditText message, to;
    public Button send;
    public DBHelper mdb;
    public SQLiteDatabase db;
    public MessageActivity mAdapter;
    MyRecyclerViewAdapter adapter;
    MessageAdapter messageAdapter;
    private ArrayList<MessageModel> animalNames = new ArrayList<>(), emps = new ArrayList<>();
    RecyclerView recyclerView;
    Boolean initial_key = true;
    String secret_Key,next_secretKey;
    DAOMessage dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        u.phone = i.getStringExtra("FROM");

        to = findViewById(R.id.editTextPhone2);
        message = findViewById(R.id.textView5);

        mdb = new DBHelper(this);
        db = mdb.getWritableDatabase();
        dao = new DAOMessage();
        emps.clear();
        /*RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageActivity(this, getAllItems());
        recyclerView.setAdapter(mAdapter);*/

        send = findViewById(R.id.button5);
        //mAdapter = new MessageActivity(this, getAllItems());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMessages();
            }
        });
    }

    private void addMessages() {
        if (message.getText().toString().trim().length() == 0) {
            return;
        }
        u.message = message.getText().toString();
        u.to_phone = to.getText().toString();
        //Cursor res2 = mdb.getmessage1(u.phone,u.to_phone);
        //if (res2 == null)
        secret_Key = u.to_phone;
        /*else{
            next_secretKey = AES.decrypt(res2.getString(2),secret_Key);
            secret_Key = next_secretKey;
        }*/
        String encryptedString = AES.encrypt(u.message, secret_Key) ;
        MessageModel mes = new MessageModel(u.phone,u.to_phone,encryptedString);
        dao.add(mes).addOnSuccessListener(suc ->
        {
            Toast.makeText(this, "Record is inserted", Toast.LENGTH_LONG).show();
            //Cursor res = mdb.getmessage(u.phone);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MessageModel");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren())
                    {
                        MessageModel emp = data.getValue(MessageModel.class);
                        //Boolean checkinsertmessage = mdb.sentusermessage(emp);
                        //if (checkinsertmessage == true)
                        //    Toast.makeText(MainActivity2.this, "Message Sent", Toast.LENGTH_LONG).show();
                        //else
                        //    Toast.makeText(MainActivity2.this, "Retry Again", Toast.LENGTH_LONG).show();
                        if (Objects.requireNonNull(data.child("from_contact").getValue()).toString().equals(u.phone))
                            emps.add(emp);

                        Cursor res = mdb.getmessage(u.phone);
                        recyclerView = findViewById(R.id.rvAnimals);
                        while(res.moveToNext()) {
                            animalNames.add(new MessageModel(res.getString(0), res.getString(1), res.getString(2)));
                        }
                        messageAdapter = new MessageAdapter(MainActivity2.this, emps);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                        //adapter.setClickListener(this);
                        recyclerView.setAdapter(messageAdapter);
                        messageAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            /*while(res.moveToNext()){
                animalNames.add(new MessageModel(res.getString(0),res.getString(1),res.getString(2)));
            }*/


        }).addOnFailureListener(er ->
        {
            Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_LONG).show();
        });
        //Boolean checkinsertmessage = mdb.sentusermessage(u.phone,u.to_phone,encryptedString);
        //if (checkinsertmessage == true){
            //Toast.makeText(MainActivity2.this, "Message Sent", Toast.LENGTH_LONG).show();

            /*Cursor res = mdb.getmessage(u.to_phone);
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append("FROM : "+res.getString(0)+"\n");
                buffer.append("TO : "+res.getString(1)+"\n");
                buffer.append("Message : "+res.getString(2)+"\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setCancelable(true);
            builder.setTitle("Sent Messages");
            builder.setMessage(buffer.toString());
            builder.show();*/

            //working type 2

            /*Cursor res = mdb.getmessage(u.to_phone);

            RecyclerView recyclerView = findViewById(R.id.rvAnimals);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ArrayList<String> animalNames = new ArrayList<>();

            while(res.moveToNext()){
                animalNames.add(res.getString(2));
            }

            adapter = new MyRecyclerViewAdapter(MainActivity2.this, animalNames);
            //adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);*/

            //woring type 3


            /*Cursor res = mdb.getmessage(u.phone);

            recyclerView = findViewById(R.id.rvAnimals);


            while(res.moveToNext()){
                animalNames.add(new MessageModel(res.getString(0),res.getString(1),res.getString(2)));
            }

            messageAdapter = new MessageAdapter(MainActivity2.this, animalNames);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //adapter.setClickListener(this);
            recyclerView.setAdapter(messageAdapter);

        }
        else
            Toast.makeText(MainActivity2.this, "Retry Again", Toast.LENGTH_LONG).show();*/

        /*Cursor res = mdb.getmessage(u.phone);
        recyclerView = findViewById(R.id.rvAnimals);
        while(res.moveToNext()) {
            animalNames.add(new MessageModel(res.getString(0), res.getString(1), res.getString(2)));
        }
        messageAdapter = new MessageAdapter(MainActivity2.this, emps);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
        //adapter.setClickListener(this);
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();*/

        //mAdapter.swapCursor(getAllItems());
        message.getText().clear();
        to.getText().clear();

    }

    /*public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/

    public static class AES {

        private static SecretKeySpec secretKey;
        private static byte[] key;

        public static void setKey(String myKey) {
            MessageDigest sha = null;
            try {
                key = myKey.getBytes("UTF-8");
                sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKey = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public static String encrypt(String strToEncrypt, String secret) {
            try {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
                }
                else
                    return strToEncrypt;
            } catch (Exception e) {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        public static String decrypt(String strToDecrypt, String secret) {
            try {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
                }
            } catch (Exception e) {
                System.out.println("Error while decrypting: " + e.toString());
            }
            return null;
        }
    }

    private Cursor getAllItems() {
        return db.query(
                "Messagelogs",
                null,
                null,
                null,
                null,
                null,
                "ASC"
        );
    }
}