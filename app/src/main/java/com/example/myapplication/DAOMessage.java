package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOMessage {
    private DatabaseReference databaseReference;

    public DAOMessage()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(MessageModel.class.getSimpleName());
    }

    public Task<Void> add(MessageModel mes)
    {
        return databaseReference.push().setValue(mes);
    }

    public Query get()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        return db.getReference().child("MessageModel");
    }
}
