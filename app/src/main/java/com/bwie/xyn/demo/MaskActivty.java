package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.bean.Goods;
import com.bwie.xyn.demo.fragment.MaskFragment;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.MyGrideView;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是面膜分类的界面
 */

public class MaskActivty extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rg_mask;
    private ViewPager vp_mask;

    private String data;
    private List<Goods.DataBean> goodList;
    private Gson gson;
    private DisplayImageOptions options;
    private TextView tv_title;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_activty);
        getSupportActionBar().hide();
        initView();
        initData();

    }


    private void initData() {
        final List<Fragment> list = new ArrayList<>();
        list.add(MaskFragment.getUrlData("9"));
        list.add(MaskFragment.getUrlData("10"));
        list.add(MaskFragment.getUrlData("23"));
        vp_mask.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        vp_mask.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < list.size(); i++) {
                    RadioButton rb = (RadioButton) rg_mask.getChildAt(i);
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
        rg_mask.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list.size(); i++) {
                    RadioButton rb = (RadioButton) rg_mask.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        vp_mask.setCurrentItem(i);
                        rb.setTextColor(Color.RED);
                    } else {
                        rb.setTextColor(Color.BLACK);
                    }
                }
            }
        });

    }

    private void initView() {
      img_back= (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        rg_mask = (RadioGroup) findViewById(R.id.rg_mask);
        vp_mask = (ViewPager) findViewById(R.id.vp_mask);
        tv_title = (TextView) findViewById(R.id.tv_title);
        Intent intent = getIntent();
        tv_title.setText(intent.getStringExtra("title"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
