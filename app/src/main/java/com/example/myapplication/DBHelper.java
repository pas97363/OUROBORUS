package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Userdetails (name TEXT primary key, contact TEXT not null)");
        db.execSQL("create Table Messagelogs (contact TEXT not null, to_contact TEXT not null, message TEXT primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Userdetails");
        db.execSQL("drop Table if exists Messagelogs");
    }

    public Boolean insertuserdata (String name,String contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("contact", contact);
        long result = db.insert("Userdetails",null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Boolean updateuserdata (String name,String contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("contact", contact);
        Cursor cursor = db.rawQuery("select * from Userdetails where name = ?", new String[]{name});
        if(cursor.getCount()>0) {
            long result = db.update("Userdetails", cv, "name=?", new String[]{name});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Boolean deleteusermessage (String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Userdetails where name = ?", new String[]{name});
        if(cursor.getCount()>0) {
            long result = db.delete("Userdetails", "name=?", new String[]{name});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Boolean sentusermessage (MessageModel emp){
        String contact=emp.getFrom_contact(),to_contact= emp.getTo_contact(),message=emp.getMessage();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("contact", contact);
        cv.put("to_contact", to_contact);
        cv.put("message", message);
        long result = db.insert("Messagelogs",null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getuserdata (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Userdetails", null);
        return cursor;
    }

    public Cursor getdata (String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Userdetails where name = ?", new String[]{name});
        return cursor;
    }
    public Cursor getmessage (String contact){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Messagelogs where contact = ?", new String[]{contact});
        return cursor;
    }
    public Cursor getmessage0 (String to_contact){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Messagelogs where to_contact = ?", new String[]{to_contact});
        return cursor;
    }
    public Cursor getmessage1 (String contact, String to_contact){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Messagelogs where to_contact = ? and contact =?", new String[]{to_contact,contact});
        Cursor test2 = cursor;
        Cursor test0 =cursor;
        while(cursor.moveToNext()){
            test2 = cursor;
            if (test2.moveToNext() == test0.moveToLast())
                break;
        }
        return cursor;
    }
    public void deletetable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table Messagelogs");
    }
}
