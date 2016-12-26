package com.bwie.xyn.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.bean.Goods;
import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.bean.Img;
import com.bwie.xyn.demo.dao.GoodsDao;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.MyListview;
import com.bwie.xyn.demo.view.RollViewPager;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodsInFoActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] dotArray = {R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private ImageView img_colection_info;
    private ImageView img_di_info;
    private ImageView img_quan_info;
    private MyListview lv_activity_info;
    private MyListview lv_info;
    private RadioGroup rg_info;
    private TextView tv_addCar_info;
    private TextView tv_kefu_info;
    private TextView tv_name_info;
    private TextView tv_pay_info;
    private TextView tv_priceAgo_info;
    private TextView tv_priceNow_info;
    private TextView tv_sc_info;
    private TextView tv_xl_info;
    private TextView tv_yf_info;
    private RollViewPager vp_info;
    private String data;
    private MyBaseData myaBaseData;
    private DisplayImageOptions options;
    private Gson gson;
    private GoodsInfo goodsInfo;
    private List<GoodsInfo.DataBean.ActivityBean> activityList;
    private List<GoodsInfo.DataBean.CommentsBean> commentsList;
    private List<GoodsInfo.DataBean.GoodsBean.AttributesBean> attributesList;
    private String goods_desc;
    private GoodsInfo.DataBean.GoodsBean goods;
    private List<GoodsInfo.DataBean.GoodsBean.GalleryBean> gallery;
    private LinearLayout vp_dotinfo;
    private List<Img> imgs;
    private DisplayImageOptions crileOptions;
    private ImageView img_back;
    private ImageView img_car;
    private ImageView img_other_loading;
    private TextView tv_type;
    private GridView gv_type;
    private RelativeLayout parent;
    private PopupWindow popupWindow;
    private View popView;
    private int i = 1;
    private TextView tv_no;
    private GoodsDao dao;
    private ImageView imgbt_add;
    private ImageView imgbt_reduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_goods_in_fo);
        init();
        initView();
        getData();

    }

    private void init() {
        dao = new GoodsDao(this);
        myaBaseData = new MyBaseData();
        options = ImageLoaderUtils.initOptions();
        gson = new Gson();
        crileOptions = ImageLoaderUtils.initCrileOptions();
    }

    //获取网络数据
    public void getData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        myaBaseData.getData(URLUtils.GOODSINTOURL, URLUtils.GOODSINTOARGS + id, 0, BaseData.NORMALTIME);

    }

    //设置数据
    public void setData() {
        tv_name_info.setText(goods.getGoods_name());
        tv_priceNow_info.setText("￥" + goods.getShop_price());
        tv_priceAgo_info.setText("￥" + goods.getMarket_price());
        tv_sc_info.setText("收藏 " + goods.getCollect_count());
        tv_xl_info.setText("销量 " + goods.getStock_number());
        String is_allow_credit = goods.getIs_allow_credit();
        if ("1".equals(is_allow_credit)) {
            img_quan_info.setVisibility(View.VISIBLE);
        }
        intiAvtivityData();
        initRooldata();
    }


    //设置活动的数据并跳转详情页
    private void intiAvtivityData() {
        lv_activity_info.setAdapter(new CommonAdapter<GoodsInfo.DataBean.ActivityBean>(GoodsInFoActivity.this, R.layout.lv_tv, activityList) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.ActivityBean item, int position) {
                viewHolder.setText(R.id.tv_activity__info, item.getTitle());
            }
        });
        lv_activity_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (activityList.get(position).getDescription() != null) {
                    Intent intent = new Intent(GoodsInFoActivity.this, WebActivity.class);
                    intent.putExtra("url", activityList.get(position).getDescription());
                    startActivity(intent);
                }
            }
        });
        //设置图片信息
        setImgData();
        //设置商品参数详情 以及评论
        rg_info.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rg_info.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rg_info.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        rb.setTextColor(Color.RED);
                    } else {
                        rb.setTextColor(Color.BLACK);
                    }
                }
                switch (checkedId) {
                    case R.id.rb_info:
                        setImgData();
                        break;
                    case R.id.rb_can_info:
                        initGoodsInFodata();
                        break;
                    case R.id.rb_pin_info:
                        initContentData();
                        break;
                }
            }

            //适配评论数据
            private void initContentData() {
                lv_info.setAdapter(new CommonAdapter<GoodsInfo.DataBean.CommentsBean>(GoodsInFoActivity.this, R.layout.lv_conect, commentsList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.CommentsBean item, int position) {
                        viewHolder.setText(R.id.tv_content, item.getContent());
                        viewHolder.setText(R.id.tv_usename, item.getUser().getNick_name());
                        viewHolder.setText(R.id.tv_time, item.getCreatetime());
                        ImageView imageView = viewHolder.getView(R.id.img_user);
                        if (item.getUser().getIcon() != null) {
                            ImageLoader.getInstance().displayImage(item.getUser().getIcon(), imageView, options);
                        } else {
                            imageView.setImageResource(R.mipmap.user_icon_default);
                        }

                    }
                });
            }

            //适配商品参数数据
            private void initGoodsInFodata() {

                lv_info.setAdapter(new CommonAdapter<GoodsInfo.DataBean.GoodsBean.AttributesBean>(GoodsInFoActivity.this, R.layout.lv_tv2, attributesList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.GoodsBean.AttributesBean item, int position) {
                        viewHolder.setText(R.id.tv_lv, item.getAttr_name() + "    " + item.getAttr_value());
                    }
                });
            }
        });
    }

    //适配图片详情
    private void setImgData() {
        lv_info.setAdapter(new CommonAdapter<Img>(GoodsInFoActivity.this, R.layout.lv_img, imgs) {
            @Override
            protected void convert(ViewHolder viewHolder, Img item, int position) {
                ImageView img = viewHolder.getView(R.id.img_info);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoader.getInstance().displayImage(item.getUrl(), img, options);
            }
        });
    }

    private void initView() {
        parent = (RelativeLayout) findViewById(R.id.main);
        img_car = (ImageView) findViewById(R.id.img_car);
        img_car.setVisibility(View.VISIBLE);
        img_back = (ImageView) findViewById(R.id.img_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商品详情");
        img_car = (ImageView) findViewById(R.id.img_car);
        img_other_loading = (ImageView) findViewById(R.id.img_other_loading);
        vp_dotinfo = (LinearLayout) findViewById(R.id.vp_dotinfo);
        img_colection_info = (ImageView) findViewById(R.id.img_colection_info);
        img_di_info = (ImageView) findViewById(R.id.img_di_info);
        img_quan_info = (ImageView) findViewById(R.id.img_quan_info);
        lv_activity_info = (MyListview) findViewById(R.id.lv_activity_info);
        lv_info = (MyListview) findViewById(R.id.lv_info);
        rg_info = (RadioGroup) findViewById(R.id.rg_info);
        tv_addCar_info = (TextView) findViewById(R.id.tv_addCar_info);
        tv_kefu_info = (TextView) findViewById(R.id.tv_kefu_info);
        tv_name_info = (TextView) findViewById(R.id.tv_name_info);
        tv_pay_info = (TextView) findViewById(R.id.tv_pay_info);
        tv_priceAgo_info = (TextView) findViewById(R.id.tv_priviceAgo_info);
        tv_priceNow_info = (TextView) findViewById(R.id.tv_priviceNow_info);
        tv_sc_info = (TextView) findViewById(R.id.tv_sc_info);
        tv_xl_info = (TextView) findViewById(R.id.tv_xl_info);
        tv_yf_info = (TextView) findViewById(R.id.tv_yf_info);
        vp_info = (RollViewPager) findViewById(R.id.vp_info);
        img_back.setOnClickListener(this);
        img_car.setOnClickListener(this);
        img_other_loading.setOnClickListener(this);
        tv_addCar_info.setOnClickListener(this);
        tv_pay_info.setOnClickListener(this);
    }

    //设置viewpage的数据
    public void initRooldata() {
        List<String> urlList = new ArrayList<>();
        List<View> dotlist = new ArrayList<>();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
        layoutParams.setMargins(5, 5, 5, 5);
        for (int i = 0; i < gallery.size(); i++) {
            View view = new View(this);
            urlList.add(gallery.get(i).getNormal_url());
            if (i == 0) {
                view.setBackgroundResource(dotArray[0]);
            } else {
                view.setBackgroundResource(dotArray[1]);
            }
            dotlist.add(view);
            vp_dotinfo.addView(view, layoutParams);

        }
        vp_info.initData(urlList, dotArray, dotlist);
        vp_info.starNoRoolltPage();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_car:
                break;
            case R.id.img_other_loading:
                initOtherPop();
                break;
            case R.id.tv_addCar_info:
                initAddCarrPop();
                popView.findViewById(R.id.bt_qd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goods.setNum(String.valueOf(i));
                        boolean add = dao.add("cardata",goods);
                        if (add) {
                            Toast.makeText(GoodsInFoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();

                        }
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.tv_pay_info:
                initAddCarrPop();
                popView.findViewById(R.id.bt_qd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.tv_kefu_info:
                break;
            case R.id.tv_type:
                popupWindow.dismiss();
                break;
            case R.id.imgbt_add:
                ++i;
                if (i > goods.getRestrict_purchase_num()) {
                    i = goods.getRestrict_purchase_num();
                    imgbt_add.setImageResource(R.mipmap.add_unable);
                } else {
                    imgbt_add.setImageResource(R.mipmap.add_able);
                }
                imgbt_reduce.setImageResource(R.mipmap.reduce_able);
                tv_no.setText(i + "");
                break;
            case R.id.imgbt_reduce:
                --i;
                if (i < 1) {
                    i = 1;
                    imgbt_reduce.setImageResource(R.mipmap.reduce_unable);
                } else {
                    imgbt_reduce.setImageResource(R.mipmap.reduce_able);
                }
                imgbt_add.setImageResource(R.mipmap.add_able);
                tv_no.setText(i + "");
                break;

        }
    }

    private void initAddCarrPop() {
        popView = getLayoutInflater().inflate(R.layout.item_addcar, null);
        imgbt_add = (ImageView) popView.findViewById(R.id.imgbt_add);
        imgbt_add.setOnClickListener(this);
        imgbt_reduce = (ImageView) popView.findViewById(R.id.imgbt_reduce);
        imgbt_reduce.setOnClickListener(this);
        ImageView img = (ImageView) popView.findViewById(R.id.img);
        TextView tv_desc = (TextView) popView.findViewById(R.id.tv_desc);
        tv_no = (TextView) popView.findViewById(R.id.tv_no);
        String no = (String) tv_no.getText();
        goods.setNum(no);
        ImageLoader.getInstance().displayImage(goods.getGoods_img(), img, options);
        tv_desc.setText("￥" + goods.getShop_price() + "\n" + "库存 " + goods.getStock_number() + "\n" + "限购 " + goods.getRestrict_purchase_num());
        showPop(popView);
    }

    //
    private void initOtherPop() {
        popView = getLayoutInflater().inflate(R.layout.showtype, null);
        popView.findViewById(R.id.tv_type).setOnClickListener(this);
        popView.findViewById(R.id.img_qq).setOnClickListener(this);
        popView.findViewById(R.id.img_wx).setOnClickListener(this);
        popView.findViewById(R.id.img_web).setOnClickListener(this);
        showPop(popView);
    }

    class MyBaseData extends BaseData {
        @Override
        public void setResultData(String data) {
            GoodsInFoActivity.this.data = data;
            goodsInfo = gson.fromJson(data, GoodsInfo.class);
            activityList = goodsInfo.getData().getActivity();
            commentsList = goodsInfo.getData().getComments();
            goods = goodsInfo.getData().getGoods();
            attributesList = goods.getAttributes();
            goods_desc = goods.getGoods_desc();
            gallery = goods.getGallery();
            imgs = gson.fromJson(goods_desc, new TypeToken<List<Img>>() {
            }.getType());
            setData();
        }

        @Override
        protected void setResultDataError(ShowinPage.StateType stateError) {

        }
    }

    //展示popwindow的方法
    public void showPop(View v) {
        popupWindow = new PopupWindow(v,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

    }

    //跳转的方法
    public void jump(Class cla) {
        Intent intent = new Intent(GoodsInFoActivity.this, cla);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", goods);
        intent.putExtras(bundle);
    }

}
