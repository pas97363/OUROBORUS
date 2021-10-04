package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity3 extends AppCompatActivity {

    User u = new User();
    public Button decrypt;
    public DBHelper mdb;
    public SQLiteDatabase db;
    MessageAdapter messageAdapter;
    MessageAdapter0 messageAdapter0;
    private ArrayList<MessageModel> animalNames = new ArrayList<>(), animalNames0 = new ArrayList<>(), emps = new ArrayList<>();
    RecyclerView recyclerView,recyclerView0;
    String secret_Key,next_secretKey;
    DAOMessage dao;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("Received Messages");
        Intent i2 = getIntent();
        u.phone = i2.getStringExtra("FROM");
        mdb = new DBHelper(this);
        dao = new DAOMessage();
        emps.clear();
        //auth = FirebaseAuth.getInstance();
        //user = auth.getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference();




        Cursor res = mdb.getmessage0(u.phone);

        /*recyclerView = findViewById(R.id.rvAnimals1);


        /*while(res.moveToNext()){
            animalNames.add(new MessageModel(res.getString(1),res.getString(0),res.getString(2)));
        }*/

        /*messageAdapter = new MessageAdapter(MainActivity3.this, emps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);*/

        decrypt = findViewById(R.id.button6);

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });
    }

    public void display() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MessageModel");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren())
                {

                    MessageModel emp = data.getValue(MessageModel.class);
                    //data.getValue();
                    /*Boolean checkinsertmessage = mdb.sentusermessage(emp);
                    if (checkinsertmessage == true)
                        Toast.makeText(MainActivity3.this, "Message Sent", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity3.this, "Retry Again", Toast.LENGTH_LONG).show();*/
                    //if (snapshot.getRef().child("MessageModel").getKey("to_contact"). == u.phone)
                    if (Objects.requireNonNull(data.child("to_contact").getValue()).toString().equals(u.phone))
                        emps.add(emp);
                    /*if (emp != null) {
                        animalNames.add(new MessageModel(emp.getTo_contact(), emp.getFrom_contact(), emp.getMessage()));
                    }*/
                    recyclerView = findViewById(R.id.rvAnimals1);


        /*while(res.moveToNext()){
            animalNames.add(new MessageModel(res.getString(1),res.getString(0),res.getString(2)));
        }*/

                    messageAdapter = new MessageAdapter(MainActivity3.this, emps);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity3.this));
                    recyclerView.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        Cursor res0 = mdb.getmessage0(u.phone);
        secret_Key = u.phone;


        /*Cursor res2 = mdb.getmessage1(res0.getString(0),u.phone);//
        if (res2 == null)
            secret_Key = u.phone;
        else{
            next_secretKey = MainActivity2.AES.decrypt(res2.getString(2),secret_Key);
            secret_Key = next_secretKey;
        }//*/

        /*reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    data.child("to_contact").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });*/


        //
        while(res0.moveToNext()){
            String decryptedString = AES.decrypt(res0.getString(2), secret_Key) ;
            animalNames0.add(new MessageModel(res0.getString(1),res0.getString(0),decryptedString));
        }
        recyclerView0 = findViewById(R.id.rvAnimals2);
        messageAdapter0 = new MessageAdapter0(MainActivity3.this, emps);
        recyclerView0.setLayoutManager(new LinearLayoutManager(this));
        //adapter.setClickListener(this);
        messageAdapter0.notifyDataSetChanged();
        recyclerView0.setAdapter(messageAdapter0);

    }

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
                else
                    return strToDecrypt;
            } catch (Exception e) {
                System.out.println("Error while decrypting: " + e.toString());
            }
            return null;
        }
    }


}