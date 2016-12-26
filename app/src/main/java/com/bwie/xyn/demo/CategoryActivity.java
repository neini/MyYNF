package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.bean.Goods;
import com.bwie.xyn.demo.hoder.ShuHoder;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.LogUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.MyGrideView;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 这是属性中除面膜外的界面
 */
public class CategoryActivity extends AppCompatActivity {

    private SpringView sv_categoryActivity;
    private GridView gv_category;
  ;
    private DisplayImageOptions options;
    private TextView tv_tutile;
    private ImageView img_other_loading;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().hide();
        init();
        initView();
        getData();

    }
    public void init(){
        gson = new Gson();
        options = ImageLoaderUtils.initOptions();
    }

    private void initView() {
        sv_categoryActivity = (SpringView) findViewById(R.id.sv_categoryActivity);
        gv_category = (GridView) findViewById(R.id.gv_categoryActivity);
        tv_tutile = (TextView) findViewById(R.id.tv_title);
        img_other_loading = (ImageView) findViewById(R.id.img_other_loading);
        img_other_loading.setVisibility(View.GONE);
         ImageView img_back= (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData() {
        final Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        tv_tutile.setText(intent.getStringExtra("title"));
        new BaseData() {

            @Override
            public void setResultData(String data) {
                Goods goods = gson.fromJson(data, Goods.class);
              final List<Goods.DataBean> goodList = goods.getData();
                BaseAdapter adapter= new CommonAdapter<Goods.DataBean>(CategoryActivity.this, R.layout.homegrideview2_item, goodList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, Goods.DataBean item, int position) {

                        ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                        //  img.setImageResource(R.mipmap.ic_launcher);
                        ImageLoader.getInstance().displayImage(item.getGoods_img(), img,options);
                        viewHolder.setText(R.id.tv_gv_desc, item.getEfficacy());
                        viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                        viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                        TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                        tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());
                    }
                };
                gv_category.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                gv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1=new Intent(CategoryActivity.this,GoodsInFoActivity.class);
                        intent1.putExtra("id",goodList.get(position).getId());
                        startActivity(intent1);
                    }
                });
            }

            @Override
            protected void setResultDataError(ShowinPage.StateType stateError) {

            }
        }.getData(URLUtils.CATEGORYURL1, URLUtils.CATEGORYURL1Args + id, 0, BaseData.NORMALTIME);
    }
}
