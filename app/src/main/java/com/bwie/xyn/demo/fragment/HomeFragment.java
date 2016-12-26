package com.bwie.xyn.demo.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import android.view.View;

import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bwie.xyn.demo.AllgoodsActivity;
import com.bwie.xyn.demo.GoodsInFoActivity;
import com.bwie.xyn.demo.LoadMoreActivity;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.base.BaseFragment;
import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.bean.HoomRoot;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.NetUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.WebActivity;
import com.bwie.xyn.demo.view.MyGrideView;
import com.bwie.xyn.demo.view.MyListview;
import com.bwie.xyn.demo.view.RollViewPager;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
public class HomeFragment extends BaseFragment {
    String data = null;
    private RollViewPager vp_homeFragment;
    private int[] dotArray = {R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private List<String> imgUrlList;
    private List<View> dotList;
    private LinearLayout ll_dot_homefragMent;
    private List<HoomRoot.DataBean.Ad1Bean> ad1;
    private List<HoomRoot.DataBean.Ad5Bean> ad5;
    private MyGrideView gv_homeFragment;
    private DisplayImageOptions options;
    private TextView tv_hot;
    private MyGrideView gv_homeFragmentHot;
    private ImageView img_more;
    private RollViewPager vp_activity_homefragment;
    private List<HoomRoot.DataBean.ActivityInfoBean.ActivityInfoListBean> activityInfoList;
    private List<String> aInfoUrlList;
    private List<HoomRoot.DataBean.SubjectsBean> subjects;
    private MyListview lv_hotTile_homeFragment;
    private MyGrideView gv_homeFragmentMore;
    private List<HoomRoot.DataBean.DefaultGoodsListBean> defaultGoodsList;
    private SpringView springView_homeFragment;
    private MyBaseData myBaseData;
    private TextView tv_home_loagAll;
    public OnCallBack onCallBack;
    private BaseAdapter gv_homeFragmentadp;
    private BaseAdapter lv_hotTile_homeFragmentap;
    private BaseAdapter gv_homeFragmentMoreap;
    private List<HoomRoot.DataBean.BestSellersBean.GoodsListBean> goodsList;

    @Override
    public void onLoad() {
        myBaseData = new MyBaseData();
        // myBaseData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 1, BaseData.NORMALTIME);
        getIsConectied();
    }

    @Override
    public View createSuccessView() {
        View homeView = initView();
        intiBaseData();
        initViewData(ad1);
        initRoolData();
        setViewAdpter();

        return homeView;

    }

    //判断网络
    public void getIsConectied() {
        int netWorkType = NetUtils.getNetWorkType(getActivity());
        if (netWorkType == NetUtils.NETWORKTYPE_INVALID) {
            Toast.makeText(getActivity(), "没有网络了，请设置网络", Toast.LENGTH_SHORT).show();
        }
        myBaseData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 1, BaseData.NORMALTIME);
    }

    //适配数据
    private void setViewAdpter() {
        //设置活动项目数据
        if (gv_homeFragmentadp == null) {
            gv_homeFragmentadp = new CommonAdapter<HoomRoot.DataBean.Ad5Bean>(getActivity(), R.layout.gv, ad5) {
                @Override
                protected void convert(ViewHolder viewHolder, HoomRoot.DataBean.Ad5Bean item, int position) {
                    ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                    //  img.setImageResource(R.mipmap.ic_launcher);
                    ImageLoader.getInstance().displayImage(item.getImage(), img, options);
                    viewHolder.setText(R.id.tv_gv_name, item.getTitle());
                }
            };
        }
        gv_homeFragment.setAdapter(gv_homeFragmentadp);
        gv_homeFragmentadp.notifyDataSetChanged();

        gv_homeFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ad5.get(position).getAd_type_dynamic_data() != null) {
                    startWebActivity(ad5.get(position).getAd_type_dynamic_data());
                }
            }
        });
        //设置本周热销活动
        gv_homeFragmentHot.setAdapter(new CommonAdapter<HoomRoot.DataBean.BestSellersBean.GoodsListBean>(getActivity(), R.layout.homegrideview2_item, goodsList) {
            @Override
            protected void convert(ViewHolder viewHolder, HoomRoot.DataBean.BestSellersBean.GoodsListBean item, int position) {
                if (position < 6) {
                    ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                    //  img.setImageResource(R.mipmap.ic_launcher);
                    ImageLoader.getInstance().displayImage(item.getGoods_img(), img, options);
                    viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                    viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                    TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                    tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());
                }
            }
        });
        gv_homeFragmentHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpGoodsInfo(goodsList.get(position).getId());
            }
        });
        //设置热门专题数据
        if (lv_hotTile_homeFragmentap == null) {

            lv_hotTile_homeFragmentap = new CommonAdapter<HoomRoot.DataBean.SubjectsBean>(getActivity(), R.layout.listview_home, subjects) {
                @Override
                protected void convert(ViewHolder viewHolder, final HoomRoot.DataBean.SubjectsBean item, int position) {
                    ImageView img = viewHolder.getView(R.id.lv_img);
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    ImageLoader.getInstance().displayImage(item.getImage(), img, options);
                    final List<HoomRoot.DataBean.SubjectsBean.GoodsListBean> goodsList1 = item.getGoodsList();
                    LinearLayout ll_hottitle = viewHolder.getView(R.id.ll_hottitle);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), LoadMoreActivity.class);
                            //  HomeFragment.this.onCallBack.sendData(item);
                            intent.putExtra("item", (Serializable) item);
                            startActivity(intent);
                        }
                    });

                    ImageView img_more = viewHolder.getView(R.id.img_more);
                    img_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), LoadMoreActivity.class);
                            //  HomeFragment.this.onCallBack.sendData(item);
                            intent.putExtra("item", (Serializable) item);
                            startActivity(intent);
                        }
                    });
                    ll_hottitle.removeAllViews();
                    for (int i = 0; i < 6; i++) {
                        final HoomRoot.DataBean.SubjectsBean.GoodsListBean goodsListBean = goodsList1.get(i);
                        View view = CommonUtils.inflate(R.layout.homegrideview_item);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        ImageView iv = (ImageView) view.findViewById(R.id.img_gv);
                        ImageLoader.getInstance().displayImage(goodsList1.get(i).getGoods_img(), iv, options);
                        TextView tv_name = (TextView) view.findViewById(R.id.tv_gv_name);
                        tv_name.setText(goodsList1.get(i).getGoods_name());
                        TextView tv_gv_pricenow = (TextView) view.findViewById(R.id.tv_gv_pricenow);
                        tv_gv_pricenow.setText("￥" + goodsList1.get(i).getShop_price());
                        TextView tv_gv_priceago = (TextView) view.findViewById(R.id.tv_gv_priceago);
                        tv_gv_priceago.setText("￥" + goodsList1.get(i).getMarket_price());
                        tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        ll_hottitle.addView(view, params);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jumpGoodsInfo(goodsListBean.getId());
                            }
                        });
                    }

                }
            };
        }
        lv_hotTile_homeFragment.setAdapter(lv_hotTile_homeFragmentap);
        lv_hotTile_homeFragmentap.notifyDataSetChanged();
        // 设置其他数据
        //  img.setImageResource(R.mipmap.ic_launcher);
        if (gv_homeFragmentMoreap == null) {
            gv_homeFragmentMoreap = new CommonAdapter<HoomRoot.DataBean.DefaultGoodsListBean>(getActivity(), R.layout.homegrideview2_item, defaultGoodsList) {
                @Override
                protected void convert(ViewHolder viewHolder, HoomRoot.DataBean.DefaultGoodsListBean item, int position) {
                    ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                    //  img.setImageResource(R.mipmap.ic_launcher);
                    if (position < 6) {
                        ImageLoader.getInstance().displayImage(item.getGoods_img(), img, options);
                        viewHolder.setText(R.id.tv_gv_desc, item.getEfficacy());
                        viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                        viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                        TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                        tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());
                    }

                }
            };
        }
        gv_homeFragmentMore.setAdapter(gv_homeFragmentMoreap);
        gv_homeFragmentMoreap.notifyDataSetChanged();
        gv_homeFragmentMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                jumpGoodsInfo(defaultGoodsList.get(position).getId());
            }
        });
    }

    public void jumpGoodsInfo(String id) {
        Intent intent = new Intent(getActivity(), GoodsInFoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void intiBaseData() {
        Gson gson = new Gson();
        HoomRoot hoomRoot = gson.fromJson(data, HoomRoot.class);
        ad1 = hoomRoot.getData().getAd1();
        ad5 = hoomRoot.getData().getAd5();
        goodsList = hoomRoot.getData().getBestSellers().get(0).getGoodsList();
        tv_hot.setText(hoomRoot.getData().getBestSellers().get(0).getName());
        activityInfoList = hoomRoot.getData().getActivityInfo().getActivityInfoList();
        subjects = hoomRoot.getData().getSubjects();
        defaultGoodsList = hoomRoot.getData().getDefaultGoodsList();

    }

    private void initRoolData() {
        //设置轮播数据
        vp_homeFragment.initData(imgUrlList, dotArray, dotList);
        vp_homeFragment.startPage();
        vp_homeFragment.setCallback(new RollViewPager.CallBack() {
            @Override
            public void sendPossion(int possion) {
                if (ad1.get(possion % imgUrlList.size()).getAd_type_dynamic_data() != null) {
                    startWebActivity(ad1.get(possion % imgUrlList.size()).getAd_type_dynamic_data());
                }
            }
        });
        vp_homeFragment.setCurrentItem(100 * imgUrlList.size());
        //设置优惠活动轮播图
        //设置Page间间距
        vp_activity_homefragment.setPageMargin(30);
        //设置缓存的页面数量
        vp_activity_homefragment.setOffscreenPageLimit(3);
        vp_activity_homefragment.initData(aInfoUrlList, null, null);
        vp_activity_homefragment.starNoRoolltPage();
        vp_activity_homefragment.setCallback(new RollViewPager.CallBack() {
            @Override
            public void sendPossion(int possion) {
                String url = activityInfoList.get(possion % activityInfoList.size()).getActivityData();
                if (url != null) {
                    startWebActivity(url);
                }

            }
        });
        vp_activity_homefragment.setCurrentItem(100 * aInfoUrlList.size());

    }

    public void startWebActivity(String url) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    //初始化数据
    private void initViewData(List<HoomRoot.DataBean.Ad1Bean> ad1) {
        imgUrlList = new ArrayList<>();
        dotList = new ArrayList<>();
        aInfoUrlList = new ArrayList<>();
        imgUrlList.clear();
        dotList.clear();
        for (int i = 0; i < activityInfoList.size(); i++) {
            aInfoUrlList.add(activityInfoList.get(i).getActivityImg());
        }
        for (int i = 0; i < ad1.size(); i++) {
            imgUrlList.add(ad1.get(i).getImage());
            View view = new View(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i == 0) {
                view.setBackgroundResource(dotArray[0]);
            } else {
                view.setBackgroundResource(dotArray[1]);
            }
            layoutParams.setMargins(5, 5, 5, 5);
            view.setLayoutParams(layoutParams);
            dotList.add(view);
            ll_dot_homefragMent.addView(view);
        }
    }

    //初始化控件
    @NonNull
    private View initView() {
        options = ImageLoaderUtils.initOptions();
        View homeView = CommonUtils.inflate(R.layout.home_frangmentnew);
        vp_homeFragment = (RollViewPager) homeView.findViewById(R.id.vp_homeFragment);
        ll_dot_homefragMent = (LinearLayout) homeView.findViewById(R.id.ll_dot_homefragMent);
        gv_homeFragment = (MyGrideView) homeView.findViewById(R.id.gv_homeFragment);
        tv_hot = (TextView) homeView.findViewById(R.id.tv_hot_homefragment);
        gv_homeFragmentHot = (MyGrideView) homeView.findViewById(R.id.gv_homeFragmentHot);
        tv_home_loagAll = (TextView) homeView.findViewById(R.id.tv_home_loagAll);
        tv_home_loagAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllgoodsActivity.class);
                startActivity(intent);
            }
        });

        vp_activity_homefragment = (RollViewPager) homeView.findViewById(R.id.vp_activity_homefragment);
        lv_hotTile_homeFragment = (MyListview) homeView.findViewById(R.id.lv_hotTile_homeFragment);
        gv_homeFragmentMore = (MyGrideView) homeView.findViewById(R.id.gv_homeFragmentMore);
        springView_homeFragment = (SpringView) homeView.findViewById(R.id.springView_homeFragment);
        //设置上拉刷新
        springView_homeFragment.setHeader(new DefaultHeader(getActivity()));
        springView_homeFragment.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                myBaseData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 1, BaseData.NOTIME);
                springView_homeFragment.scrollTo(0, 0);
            }

            @Override
            public void onLoadmore() {

                springView_homeFragment.scrollTo(1, 0);
            }
        });
        springView_homeFragment.setType(SpringView.Type.FOLLOW);
        return homeView;
    }

    //获得数据的内部类
    class MyBaseData extends BaseData {
        @Override
        public void setResultData(String data) {
            HomeFragment.this.data = data;
            HomeFragment.this.showCurrentView(ShowinPage.StateType.STATE__LOAD_SUCCESS);
        }

        @Override
        protected void setResultDataError(ShowinPage.StateType stateError) {
            HomeFragment.this.showCurrentView(ShowinPage.StateType.STATE_ERROR);
        }
    }

    public void setCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;

    }

    public interface OnCallBack {
        public void sendData(HoomRoot.DataBean.SubjectsBean goodlist);
    }
}
