package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.fragment.MinrOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MineOrder extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp_mine;
    private RadioGroup rg_mine;
    private TextView tv_title;
    private TextView tv_right;
    private List<Fragment> flist;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        initView();
        initData();
        vp_mine.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return flist.get(position);
            }

            @Override
            public int getCount() {
                return flist.size();
            }
        });
        vp_mine.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < rg_mine.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rg_mine.getChildAt(i);
                    if (i == position) {
                        rb.setTextColor(Color.RED);
                    } else {
                        rb.setTextColor(Color.BLACK);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg_mine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rg_mine.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rg_mine.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        vp_mine.setCurrentItem(i);
                        rb.setTextColor(Color.RED);
                    } else {
                        rb.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        vp_mine.setCurrentItem(id);
    }

    private void initData() {
        flist = new ArrayList<>();
        flist.add(MinrOrderFragment.getFragmentData("cardata"));
        flist.add(MinrOrderFragment.getFragmentData("paydata"));
        flist.add(MinrOrderFragment.getFragmentData("senddata"));
        flist.add(MinrOrderFragment.getFragmentData("getdata"));
        flist.add(MinrOrderFragment.getFragmentData("contentdata"));

    }

    private void initView() {
        findViewById(R.id.img_back).setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的订单");
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("退款订单");
        tv_right.setOnClickListener(this);
        findViewById(R.id.img_other_loading).setVisibility(View.GONE);
        vp_mine = (ViewPager) findViewById(R.id.vp_mine);
        rg_mine = (RadioGroup) findViewById(R.id.rg_mine);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.img_back:
                finish();
                break;
            //进入退款订单页面
            case R.id.tv_right:
                break;
        }
    }
}
