package com.bwie.xyn.demo.hoder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bwie.xyn.demo.AllgoodsActivity;
import com.bwie.xyn.demo.CategoryActivity;
import com.bwie.xyn.demo.MaskActivty;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.CategoryRoot;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public  class ShuHoder extends BaseHolder<CategoryRoot.DataBean.CategoryBean> implements View.OnClickListener {

    private  ImageView img_classify_facial_mask;
    private  ImageView img_classify_body_lotion;
    private  ImageView img_classify_kit;
    private  ImageView img_classify_emollient_water;
    private  ImageView img_classify_facial_cleanser;
    private  ImageView img_classify_other;
    private CategoryRoot.DataBean.CategoryBean categoryBeen;
    private Context context;




    //找控件
    public ShuHoder(View itemView) {
        super(itemView);
        img_classify_facial_mask = (ImageView) itemView.findViewById(R.id.img_classify_facial_mask);
        img_classify_body_lotion = (ImageView) itemView.findViewById(R.id.img_classify_body_lotion);
        img_classify_kit = (ImageView) itemView.findViewById(R.id.img_classify_kit);
        img_classify_emollient_water = (ImageView) itemView.findViewById(R.id.img_classify_emollient_water);
        img_classify_facial_cleanser = (ImageView) itemView.findViewById(R.id.img_classify_facial_cleanser);
        img_classify_other = (ImageView) itemView.findViewById(R.id.img_classify_other);
        img_classify_facial_mask.setOnClickListener(this);
        img_classify_body_lotion.setOnClickListener(this);
        img_classify_kit.setOnClickListener(this);
        img_classify_emollient_water.setOnClickListener(this);
        img_classify_facial_cleanser.setOnClickListener(this);
        img_classify_other.setOnClickListener(this);


    }

    @Override
    public void setDataList(Context context, CategoryRoot.DataBean.CategoryBean categoryBeen) {
        this.categoryBeen = categoryBeen;
           this.context=context;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.img_classify_facial_mask :
                jumpActivity(MaskActivty.class,categoryBeen.getChildren().get(0).getCat_name(),categoryBeen.getChildren().get(0).getId());
                break;
            case  R.id.img_classify_body_lotion:
                jumpActivity(CategoryActivity.class,categoryBeen.getChildren().get(2).getCat_name(),categoryBeen.getChildren().get(2).getId());

                break;
            case  R.id.img_classify_kit :
                jumpActivity(CategoryActivity.class,categoryBeen.getChildren().get(5).getCat_name(),categoryBeen.getChildren().get(5).getId());
                break;
            case  R.id.img_classify_emollient_water :
                jumpActivity(CategoryActivity.class,categoryBeen.getChildren().get(1).getCat_name(),categoryBeen.getChildren().get(1).getId());
                break;
            case  R.id.img_classify_facial_cleanser :
                jumpActivity(CategoryActivity.class,categoryBeen.getChildren().get(3).getCat_name(),categoryBeen.getChildren().get(3).getId());
                break;
            case  R.id.img_classify_other :
                jumpActivity(CategoryActivity.class,categoryBeen.getChildren().get(4).getCat_name(),categoryBeen.getChildren().get(4).getId());

                break;

        }


    }

    public void jumpActivity(Class cla,String title,String str){
        Intent intent=new Intent(context, cla);
        intent.putExtra("title",title);
        intent.putExtra("id",str);
        context.startActivity(intent);
    }


}
