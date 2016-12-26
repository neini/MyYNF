package com.bwie.xyn.demo.hoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bwie.xyn.demo.FuActivity;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.CategoryRoot;

/**
 * Created by Administrator on 2016/12/6.
 */
public class XiaoHoder extends  BaseHolder<CategoryRoot.DataBean.CategoryBean> implements View.OnClickListener {
    private CategoryRoot.DataBean.CategoryBean categoryBeen;
    private final ImageView img_classify_hydrating;
    private final ImageView img_classify_soothing;
    private final ImageView img_classify_control_oil;
    private final ImageView img_classify_whitening;
    private final ImageView img_classify_firming;
    private Context context;
    //找控件
    public XiaoHoder(View itemView) {

        super(itemView);
        img_classify_hydrating = (ImageView) itemView.findViewById(R.id.img_classify_hydrating);
        img_classify_soothing = (ImageView) itemView.findViewById(R.id.img_classify_soothing);
        img_classify_control_oil = (ImageView) itemView.findViewById(R.id.img_classify_control_oil);
        img_classify_whitening = (ImageView) itemView.findViewById(R.id.img_classify_whitening);
        img_classify_firming = (ImageView) itemView.findViewById(R.id.img_classify_firming);
        img_classify_hydrating.setOnClickListener(this);
        img_classify_soothing.setOnClickListener(this);
        img_classify_control_oil.setOnClickListener(this);
        img_classify_whitening.setOnClickListener(this);
        img_classify_firming.setOnClickListener(this);

    }
    @Override
    public void setDataList(Context context, CategoryRoot.DataBean.CategoryBean categoryBeen) {
        this.categoryBeen=categoryBeen;
         this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_classify_hydrating:
                jumpActivty(FuActivity.class,categoryBeen,0);
                break;
            case R.id.img_classify_soothing:
                jumpActivty(FuActivity.class,categoryBeen,1);

                break;
            case R.id.img_classify_control_oil:
                jumpActivty(FuActivity.class,categoryBeen,2);

                break;
            case R.id.img_classify_whitening:
                jumpActivty(FuActivity.class,categoryBeen,3);

                break;
            case R.id.img_classify_firming  :
                jumpActivty(FuActivity.class,categoryBeen,4);

                break;
        }

    }
    public void jumpActivty(Class cla, CategoryRoot.DataBean.CategoryBean categoryBean, int id){
        Intent intent=new Intent(context,cla);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",categoryBean);

        intent.putExtras(bundle);
        intent.putExtra("id", id);

        context.startActivity(intent);

    }
}
