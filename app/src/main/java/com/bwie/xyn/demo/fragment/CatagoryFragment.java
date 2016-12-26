package com.bwie.xyn.demo.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bwie.xyn.demo.AllgoodsActivity;
import com.bwie.xyn.demo.GoodsInFoActivity;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.adapter.MyRecyclerView;
import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.base.BaseFragment;

import com.bwie.xyn.demo.bean.CategoryRoot;
import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.MyGrideView;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
public class CatagoryFragment extends BaseFragment {

    private View categoryView;
    String data;
    private MyBaseData myBaseData;

    private EditText et_category;
    private RecyclerView rv_category;
    private SpringView sv_category;
    private TextView tv_lookAll;
    private List<CategoryRoot.DataBean.CategoryBean> category;
    private MyGrideView gv_classify;
    private List<CategoryRoot.DataBean.GoodsBriefBean> goodsBrief;
    private DisplayImageOptions options;

    @Override
    public void onLoad() {
        myBaseData = new MyBaseData();
        myBaseData.getData(URLUtils.CATEGORYURL, URLUtils.CATEGORYARGS, 0, BaseData.NORMALTIME);
    }

    //初始化数据
    private void initData() {
        Gson gson = new Gson();
        CategoryRoot categoryRoot = gson.fromJson(data, CategoryRoot.class);
        category = categoryRoot.getData().getCategory();

        goodsBrief = categoryRoot.getData().getGoodsBrief();
    }


    @Override
    public View createSuccessView() {
        categoryView = CommonUtils.inflate(R.layout.catrgory_fragment);
        initData();
        initView();
        reFresh();
        rv_category.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        rv_category.setAdapter(new MyRecyclerView(category, getActivity()));
        return categoryView;
    }

    //初始化控件
    private void initView() {
        options = ImageLoaderUtils.initOptions();
        et_category = (EditText) categoryView.findViewById(R.id.et_category);
        rv_category = (RecyclerView) categoryView.findViewById(R.id.rv_category);
        sv_category = (SpringView) categoryView.findViewById(R.id.sv_category);
        tv_lookAll = (TextView) categoryView.findViewById(R.id.tv_lookAll_category);
        gv_classify = (MyGrideView) categoryView.findViewById(R.id.gv_classify);
        gv_classify.setAdapter(new CommonAdapter<CategoryRoot.DataBean.GoodsBriefBean>(getActivity(), R.layout.homegrideview2_item, goodsBrief) {
            @Override
            protected void convert(ViewHolder viewHolder, CategoryRoot.DataBean.GoodsBriefBean item, int position) {
                ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                //  img.setImageResource(R.mipmap.ic_launcher);

                ImageLoader.getInstance().displayImage(item.getGoods_img(), img, options);
                viewHolder.setText(R.id.tv_gv_desc, item.getEfficacy());
                viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());

            }
        });
        gv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), GoodsInFoActivity.class);
                intent.putExtra("id",goodsBrief.get(position).getId());
                startActivity(intent);
            }
        });
        tv_lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllgoodsActivity.class);
                startActivity(intent);
            }
        });
    }

    //下拉刷新
    public void reFresh() {
        sv_category.setHeader(new DefaultHeader(getActivity()));
        sv_category.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                myBaseData.getData(URLUtils.CATEGORYURL, URLUtils.CATEGORYARGS, 0, BaseData.NOTIME);
                sv_category.scrollTo(0, 0);
            }

            @Override
            public void onLoadmore() {

            }
        });
        sv_category.setType(SpringView.Type.FOLLOW);

    }

    class MyBaseData extends BaseData {

        @Override
        public void setResultData(String data) {
            CatagoryFragment.this.data = data;
            CatagoryFragment.this.showCurrentView(ShowinPage.StateType.STATE__LOAD_SUCCESS);
        }

        @Override
        protected void setResultDataError(ShowinPage.StateType stateError) {
            CatagoryFragment.this.showCurrentView(ShowinPage.StateType.STATE_ERROR);
        }
    }
}
