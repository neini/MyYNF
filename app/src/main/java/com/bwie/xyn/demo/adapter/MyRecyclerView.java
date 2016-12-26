package com.bwie.xyn.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.CategoryRoot;
import com.bwie.xyn.demo.bean.CategoryRoot.DataBean.CategoryBean;
import com.bwie.xyn.demo.hoder.BaseHolder;
import com.bwie.xyn.demo.hoder.FuHoder;
import com.bwie.xyn.demo.hoder.ShuHoder;
import com.bwie.xyn.demo.hoder.XiaoHoder;
import com.bwie.xyn.demo.utils.CommonUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class MyRecyclerView extends RecyclerView.Adapter<BaseHolder<CategoryRoot.DataBean.CategoryBean>>{
    public   static final int SHUTYPE=1;
    public   static final int FUTYPE=2;
    public   static final int XIAOTYPE=3;

    private  Context context;
    private  List<CategoryBean> list;

    public MyRecyclerView(List<CategoryRoot.DataBean.CategoryBean> list, Context context){
     this.list =list;
     this.context =context;

 }
    //返回viewhoder
    @Override
    public BaseHolder<CategoryRoot.DataBean.CategoryBean> onCreateViewHolder(ViewGroup parent, int viewType) {
     BaseHolder baseHolder=null;
        View view=null;
        switch (viewType){
            case  SHUTYPE:
                view= CommonUtils.inflate(R.layout.category_shuxing);
                baseHolder =new ShuHoder(view);
                break;
            case  XIAOTYPE:
                view= CommonUtils.inflate(R.layout.category_gongxiao);
                 baseHolder=new XiaoHoder(view);
                break;

            case  FUTYPE:
                view= CommonUtils.inflate(R.layout.category_fuzhi);
                baseHolder=new FuHoder(view);
                break;

        }

        return baseHolder;
    }
    // 和viewHoder关联后设置数据
    @Override
    public void onBindViewHolder(BaseHolder<CategoryRoot.DataBean.CategoryBean> holder, int position) {
        holder.setDataList(context,  list.get(position));
    }
  //获取当前条目类型
    @Override
    public int getItemViewType(int position) {
        int type=-1;
           switch (position){
               case  1:
                   type=SHUTYPE;
                   break;
               case 0:
                   type=XIAOTYPE;
                   break;

               case  2:
                   type=FUTYPE;
                   break;

           }
        return type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
