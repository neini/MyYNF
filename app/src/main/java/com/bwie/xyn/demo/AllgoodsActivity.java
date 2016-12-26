package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.bean.AllGoodsRoot;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.MyGrideView;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllgoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private List<AllGoodsRoot.DataBean> data1;
    private MyGrideView gv_allgoods;
    private SpringView springView_all;
    private TextView tv_normol_all;
    private TextView tv_low_all;
    private TextView tv_up_all;
    private TextView tv_up_all1;
    private DisplayImageOptions options;
    private MyBaseData myBaseData;
    private CommonAdapter allAdpter;
    public int NORMORALTYPE=0;
    public int UPTYPE=1;
    private int LOWTYPE=2;
    private ImageView img_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allgoods);
        getSupportActionBar().hide();
        initView();
        initBaseData();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        options = ImageLoaderUtils.initOptions();
        myBaseData = new MyBaseData();
        img_back = (ImageView) findViewById(R.id.img_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        gv_allgoods = (MyGrideView) findViewById(R.id.gv_Allgoods);
        springView_all = (SpringView) findViewById(R.id.springView_All);
        tv_normol_all = (TextView) findViewById(R.id.tv_normol_All);
        tv_low_all = (TextView) findViewById(R.id.tv_low_All);
        tv_up_all = (TextView) findViewById(R.id.tv_up_All);
        tv_low_all.setOnClickListener(this);
        tv_normol_all.setOnClickListener(this);
        tv_up_all.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tv_title.setText("全部商品");
        //上拉刷新
        springView_all.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allAdpter=null;
                initBaseData();
                springView_all.scrollTo(0,0);
            }

            @Override
            public void onLoadmore() {

            }
        });
        springView_all.setHeader(new DefaultHeader(this));
        springView_all.setFooter(new DefaultFooter(this));
        springView_all.setType(SpringView.Type.FOLLOW);
    }
    //初始化数据
    private void initBaseData() {

        myBaseData.getData(URLUtils.AllGoodsurl, URLUtils.AllGoodArgs, 0, BaseData.NORMALTIME);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_normol_All:
                allAdpter=null;
                initBaseData();
                break;
            case R.id.tv_low_All:
                setAdapterData(data1,2);
                break;
            case R.id.tv_up_All:
                setAdapterData(data1,1);
                break;
            case R.id.img_back:
                     finish();
                break;
        }
    }
        //设置适配器
    public void setAdapterData(final List<AllGoodsRoot.DataBean> dataList, int type) {


        if(type==LOWTYPE){
          Collections.sort(dataList, new Comparator<AllGoodsRoot.DataBean>() {
              @Override
              public int compare(AllGoodsRoot.DataBean lhs, AllGoodsRoot.DataBean rhs) {
                  if(lhs.getShop_price()>rhs.getShop_price())
                      return  -1;
                  return 1;
              }
          });
        }else if(type==UPTYPE){
            Collections.sort(dataList, new Comparator<AllGoodsRoot.DataBean>() {
                @Override
                public int compare(AllGoodsRoot.DataBean lhs, AllGoodsRoot.DataBean rhs) {
                    if(lhs.getShop_price()>rhs.getShop_price())
                        return  1;
                    return -1;
                }
            });
        }



        if(allAdpter==null) {
            allAdpter = new CommonAdapter<AllGoodsRoot.DataBean>(this, R.layout.homegrideview2_item, dataList) {
                @Override
                protected void convert(ViewHolder viewHolder, AllGoodsRoot.DataBean item, int position) {
                    ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                    ImageLoader.getInstance().displayImage(item.getGoods_img(), img, options);
                    viewHolder.setText(R.id.tv_gv_desc, item.getEfficacy());
                    viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                    viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                    TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                    tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());

                }
            };

        }
       allAdpter.notifyDataSetChanged();
        gv_allgoods.setAdapter(allAdpter);
       gv_allgoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(AllgoodsActivity.this,GoodsInFoActivity.class);
                 intent.putExtra("id",dataList.get(position).getId());
                startActivity(intent);
           }
       });


    }

    class MyBaseData extends BaseData {
        @Override
        public void setResultData(String data) {
            Gson gson = new Gson();
            AllGoodsRoot allGoodsRoot = gson.fromJson(data, AllGoodsRoot.class);
            data1 = allGoodsRoot.getData();
            setAdapterData(data1,0);
        }

        @Override
        protected void setResultDataError(ShowinPage.StateType stateError) {

        }
    }

}
