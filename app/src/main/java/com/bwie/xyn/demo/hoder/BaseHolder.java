package com.bwie.xyn.demo.hoder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bwie.xyn.demo.bean.CategoryRoot;

/**
 * Created by Administrator on 2016/12/6.
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
    }
  public abstract void   setDataList(Context context,T t);



}
