package com.bwie.xyn.demo.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.xyn.demo.LoagActiviry;
import com.bwie.xyn.demo.MineOrder;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.application.MyApplication;
import com.bwie.xyn.demo.base.BaseFragment;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.SetingActivity;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.view.ShowinPage;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView img_setting_mine;
    private ImageView img_backmoney_mine;
    private ImageView img_get_mine;
    private ImageButton imbt_load_mine;
    private ImageView img_mine;
    private ImageView img_pay_mine;
    private ImageView img_center_mine;
    private ImageView img_pi_mine;
    private ImageView img_send_mine;
    private LinearLayout ll_con_mine;
    private LinearLayout ll_mydan_mine;
    private LinearLayout ll_myjiang_mine;
    private LinearLayout ll_myLove_mine;
    private LinearLayout ll_mymoney_mine;
    private LinearLayout ll_mine_hui;
    private RelativeLayout re_laog_mine;
    private TextView tv_name_mine;

    @Override
    public void onLoad() {
        MineFragment.this.showCurrentView(ShowinPage.StateType.STATE__LOAD_SUCCESS);

    }

    @Override
    public View createSuccessView() {
        view = CommonUtils.inflate(R.layout.mine_fragment);
        //初始化控件
        initView();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfoData();
    }

    private void initUserInfoData() {
        if(MyApplication.isLogin) {
            String user_name = CommonUtils.getSp("user_name");
            String user_icon = CommonUtils.getSp("user_icon");
            if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_icon)) {
                tv_name_mine.setText(user_name);
                ImageLoader.getInstance().displayImage(user_icon, img_mine, ImageLoaderUtils.initCrileOptions());
                re_laog_mine.setVisibility(View.GONE);
                ll_mine_hui.setVisibility(View.VISIBLE);
            } else {
                re_laog_mine.setVisibility(View.VISIBLE);
                ll_mine_hui.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        tv_name_mine = (TextView) view.findViewById(R.id.tv_name_mine);
        img_setting_mine = (ImageView) view.findViewById(R.id.img_setting_mine);
        img_backmoney_mine = (ImageView) view.findViewById(R.id.img_backmoney_mine);
        img_get_mine = (ImageView) view.findViewById(R.id.img_get_mine);
        imbt_load_mine = (ImageButton) view.findViewById(R.id.img_load_mine);
        img_mine = (ImageView) view.findViewById(R.id.img_mine);
        img_pay_mine = (ImageView) view.findViewById(R.id.img_pay_mine);
        img_center_mine = (ImageView) view.findViewById(R.id.img_center_mine);
        img_pi_mine = (ImageView) view.findViewById(R.id.img_pi_mine);
        img_send_mine = (ImageView) view.findViewById(R.id.img_send_mine);
        ll_con_mine = (LinearLayout) view.findViewById(R.id.ll_con_mine);
        ll_mydan_mine = (LinearLayout) view.findViewById(R.id.ll_mydan_mine);
        ll_myjiang_mine = (LinearLayout) view.findViewById(R.id.ll_myjiang_mine);
        ll_myLove_mine = (LinearLayout) view.findViewById(R.id.ll_myLove_mine);
        ll_mymoney_mine = (LinearLayout) view.findViewById(R.id.ll_mymoney_mine);
        ll_mine_hui = (LinearLayout) view.findViewById(R.id.ll_mine_hui);
        re_laog_mine = (RelativeLayout) view.findViewById(R.id.re_laog_mine);
        img_setting_mine.setOnClickListener(this);
        img_backmoney_mine.setOnClickListener(this);
        img_get_mine.setOnClickListener(this);
        imbt_load_mine.setOnClickListener(this);
        img_mine.setOnClickListener(this);
        img_pay_mine.setOnClickListener(this);
        img_center_mine.setOnClickListener(this);
        img_pi_mine.setOnClickListener(this);
        img_send_mine.setOnClickListener(this);
        ll_con_mine.setOnClickListener(this);
        ll_mydan_mine.setOnClickListener(this);
        ll_myLove_mine.setOnClickListener(this);
        ll_myjiang_mine.setOnClickListener(this);
        ll_mymoney_mine.setOnClickListener(this);
        ll_mine_hui.setOnClickListener(this);
        re_laog_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击进入设置页面
            case R.id.img_setting_mine:
                Intent intent = new Intent(getActivity(), SetingActivity.class);
                startActivity(intent);
                break;
            //点击进入登录页面
            case R.id.img_load_mine:
                Intent intent1 = new Intent(getActivity(), LoagActiviry.class);
                startActivity(intent1);
                break;
            case R.id.ll_mydan_mine:
                jumpOrder(0);
                break;
            //跳转待付款界面
            case R.id.img_pay_mine:
                jumpOrder(1);
                break;
            //待发货界面
            case R.id.img_send_mine:
                jumpOrder(2);
                break;
            //待收货
            case R.id.img_get_mine:
                jumpOrder(3);
                break;
            //待评价
            case R.id.img_pi_mine:
                jumpOrder(4);
                break;
            //退款
            case R.id.img_backmoney_mine:
                break;
        }
    }

    public void jumpOrder(int id) {
        Intent intent = new Intent(getActivity(), MineOrder.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
