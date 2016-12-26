package com.bwie.xyn.demo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bwie.xyn.demo.bean.AdressRoot;
import com.bwie.xyn.demo.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */
public class AdressDao {

    private final SQLiteDatabase db;
    List<AdressRoot> list = new ArrayList<>();

    public AdressDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //添加的方法
    public void add(AdressRoot adressRoot) {
        ContentValues values = new ContentValues();
        values.put("pName", adressRoot.getpName());
        values.put("pPhone", adressRoot.getpPhone());
        values.put("pCity", adressRoot.getpCity());
        values.put("pAdress", adressRoot.getpAdress());
        db.insert("adress", null, values);
    }

    // 查询所有的方法
    public List<AdressRoot> select() {
        Cursor query = db.query("adress", null, null, null, null, null, null);
        list.clear();
        AdressRoot adressRoot = null;
        while (query.moveToNext()) {
            String pName = query.getString(query.getColumnIndex("pName"));
            String pPhone = query.getString(query.getColumnIndex("pPhone"));
            int id = query.getInt(query.getColumnIndex("id"));
            String pCity = query.getString(query.getColumnIndex("pCity"));
            String pAdress = query.getString(query.getColumnIndex("pAdress"));
            adressRoot = new AdressRoot();
            adressRoot.setId(id);
            adressRoot.setpAdress(pAdress);
            adressRoot.setpCity(pCity);
            adressRoot.setpName(pName);
            adressRoot.setpPhone(pPhone);
            list.add(adressRoot);
        }
        query.close();
        return list;
    }

    // 更新的方法
    public void update(AdressRoot adressRoot,int id) {
        ContentValues values = new ContentValues();
        values.put("pName", adressRoot.getpName());
        Log.i("TAG", "update: ====="+adressRoot.getpName());
        values.put("pPhone", adressRoot.getpPhone());
        values.put("pCity", adressRoot.getpCity());
        values.put("pAdress", adressRoot.getpAdress());
        db.update("adress",values,"id = ?",new String[]{String.valueOf(id)});
        db.close();

    }

    // 按id查询的方法
    public AdressRoot select(int id) {
        Cursor query = db.query("adress", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        AdressRoot adressRoot = null;
        while (query.moveToNext()) {
            String pName = query.getString(query.getColumnIndex("pName"));
            String pPhone = query.getString(query.getColumnIndex("pPhone"));
            String pCity = query.getString(query.getColumnIndex("pCity"));
            String pAdress = query.getString(query.getColumnIndex("pAdress"));
            adressRoot = new AdressRoot();
            adressRoot.setId(id);
            adressRoot.setpAdress(pAdress);
            adressRoot.setpCity(pCity);
            adressRoot.setpName(pName);
            adressRoot.setpPhone(pPhone);
        }
        query.close();
        return adressRoot;
    }

}
