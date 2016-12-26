package com.bwie.xyn.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/13.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "mycar.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cardata (id text,name text,price text,num text,img text)");
        db.execSQL("create table paydata (id text,name text,price text,num text,img text)");
        db.execSQL("create table senddata (id text,name text,price text,num text,img text)");
        db.execSQL("create table getdata (id text,name text,price text,num text,img text)");
        db.execSQL("create table contentdata (id text,name text,price text,num text,img text)");
        db.execSQL("create table  adress (id integer primary key autoincrement,pName text,pPhone text,pCity text,pAdress text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
