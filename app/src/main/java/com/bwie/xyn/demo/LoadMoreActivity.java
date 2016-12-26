package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.xyn.demo.bean.HoomRoot;
import com.bwie.xyn.demo.fragment.HomeFragment;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.List;

public class LoadMoreActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_detail;
    private GridView gv_loadMore;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        getSupportActionBar().hide();
        options = ImageLoaderUtils.initOptions();
        initView();

       initData();
    }

    private void initData() {
        final Intent intent=getIntent();

        HoomRoot.DataBean.SubjectsBean item  = (HoomRoot.DataBean.SubjectsBean) intent.getSerializableExtra("item");
        tv_detail.setText(item.getDetail());
        tv_title.setText(item.getTitle());
        final List<HoomRoot.DataBean.SubjectsBean.GoodsListBean> goodsList1 = item.getGoodsList();
        gv_loadMore.setAdapter(new CommonAdapter<HoomRoot.DataBean.SubjectsBean.GoodsListBean>(LoadMoreActivity.this, R.layout.homegrideview2_item, goodsList1) {
            @Override
            protected void convert(ViewHolder viewHolder, HoomRoot.DataBean.SubjectsBean.GoodsListBean item, int position) {
                ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                ImageLoader.getInstance().displayImage(item.getGoods_img(), img,options);
                viewHolder.setText(R.id.tv_gv_desc, item.getEfficacy());
                viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());
            }
        });
        gv_loadMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(LoadMoreActivity.this,GoodsInFoActivity.class);
                intent1.putExtra("id",goodsList1.get(position).getId());
                startActivity(intent1);

            }
        });;

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        gv_loadMore = (GridView) findViewById(R.id.gv_loadMore);
    }

}
