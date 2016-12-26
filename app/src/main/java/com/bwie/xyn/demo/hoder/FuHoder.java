package com.bwie.xyn.demo.hoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bwie.xyn.demo.FuActivity;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.CategoryRoot;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class FuHoder extends BaseHolder<CategoryRoot.DataBean.CategoryBean> implements View.OnClickListener {
    private CategoryRoot.DataBean.CategoryBean categoryBeen;
    private final TextView tv_classify_hun;
    private final TextView tv_classify_gan;
    private final TextView tv_classify_you;
    private final TextView tv_classify_dou;
    private final TextView tv_classify_min;
    private final TextView tv_classify_zhong;
   private Context context;
    //找控件
    public FuHoder(View itemView) {
        super(itemView);
        tv_classify_hun = (TextView) itemView.findViewById(R.id.tv_classify_hun);
        tv_classify_gan = (TextView) itemView.findViewById(R.id.tv_classify_gan);
        tv_classify_you = (TextView) itemView.findViewById(R.id.tv_classify_you);
        tv_classify_dou = (TextView) itemView.findViewById(R.id.tv_classify_dou);
        tv_classify_min = (TextView) itemView.findViewById(R.id.tv_classify_min);
        tv_classify_zhong = (TextView) itemView.findViewById(R.id.tv_classify_zhong);
        tv_classify_hun.setOnClickListener(this);
        tv_classify_gan.setOnClickListener(this);
        tv_classify_you.setOnClickListener(this);
        tv_classify_dou.setOnClickListener(this);
        tv_classify_min.setOnClickListener(this);
        tv_classify_zhong.setOnClickListener(this);
    }

    @Override
    public void setDataList(Context context, CategoryRoot.DataBean.CategoryBean categoryBeen) {
        this.categoryBeen = categoryBeen;
        this.context=context;

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_classify_hun:
                 jumpActivty(FuActivity.class,categoryBeen,null,0);
                break;
            case R.id.tv_classify_gan:
                jumpActivty(FuActivity.class,categoryBeen,null,2);
                break;
            case R.id.tv_classify_you:
                jumpActivty(FuActivity.class,categoryBeen,null,3);
                break;
            case R.id.tv_classify_dou:
                jumpActivty(FuActivity.class,categoryBeen,null,4);
                break;
            case R.id.tv_classify_min:
                jumpActivty(FuActivity.class,categoryBeen,null,5);
                break;
            case R.id.tv_classify_zhong:
                jumpActivty(FuActivity.class,categoryBeen,null,1);
                break;

        }
    }
    public void jumpActivty(Class cla, CategoryRoot.DataBean.CategoryBean categoryBean, CategoryRoot.DataBean.CategoryBean.ChildrenBean child, int id){
        Intent intent=new Intent(context,cla);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",categoryBean);
        bundle.putSerializable("child" ,child);

        intent.putExtras(bundle);
      intent.putExtra("id",id);

        context.startActivity(intent);

    }
}
