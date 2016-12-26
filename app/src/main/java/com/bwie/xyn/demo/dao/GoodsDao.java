package com.bwie.xyn.demo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class GoodsDao {

    private final SQLiteDatabase db;

    public GoodsDao(Context context) {
        DbHelper helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    //添加的方法
    public boolean add(String tableName, GoodsInfo.DataBean.GoodsBean goods) {
        ContentValues values = new ContentValues();
        values.put("name", goods.getGoods_name());
        values.put("num", goods.getNum());
        values.put("price", goods.getShop_price());
        values.put("img", goods.getGoods_img());
        values.put("id", goods.getId());
        long cardata = db.insert(tableName, null, values);
        if (cardata > 0) {
            return true;
        }
        return false;
    }

    //查询的方法
    public List<GoodsInfo.DataBean.GoodsBean> select(String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        List<GoodsInfo.DataBean.GoodsBean> goodsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            GoodsInfo.DataBean.GoodsBean goodsBean = new GoodsInfo.DataBean.GoodsBean();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String num = cursor.getString(cursor.getColumnIndex("num"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            goodsBean.setNum(num);
            goodsBean.setId(id);
            goodsBean.setGoods_name(name);
            goodsBean.setGoods_img(img);
            goodsBean.setShop_price(price);
            goodsList.add(goodsBean);
        }
        cursor.close();
        return goodsList;
    }

    //删除的方法
    public boolean delete(String tableName, String id) {
        int cardata = db.delete(tableName, "id=?", new String[]{id});
        if (cardata > 0)
            return true;
        return false;
    }


}
