package com.bwie.xyn.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwie.xyn.demo.application.MyApplication;
import com.bwie.xyn.demo.factory.FragmentFactory;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AutoLayoutActivity {


    public static ViewPager vp_mian;
    private RadioGroup rg_main;

    private List<View> viewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
              getSupportActionBar().hide();
            vp_mian = (ViewPager) findViewById(R.id.vp_main);
            rg_main = (RadioGroup) findViewById(R.id.rg_main);
            vp_mian.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
//                    if (position == 2) {
//                        //如果是未登录状态
//                        if (!MyApplication.isLogin) {
//                            startActivity(new Intent(MainActivity.this, LoagActiviry.class));
//                        }
//                    }
                    return FragmentFactory.getFragment(position);
                }

                @Override
                public int getCount() {
                    return 4;
                }
            });
        vp_mian.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < rg_main.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) rg_main.getChildAt(i);
                    if (i == position) {
                        vp_mian.setCurrentItem(i);
                        radioButton.setChecked(true);
                        radioButton.setTextColor(Color.RED);
                    } else {
                        radioButton.setTextColor(Color.BLACK);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
            rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    for (int i = 0; i < rg_main.getChildCount(); i++) {
                        RadioButton radioButton = (RadioButton) rg_main.getChildAt(i);
                        if (radioButton.getId() == checkedId) {
                            vp_mian.setCurrentItem(i);
                            radioButton.setTextColor(Color.RED);
                        } else {
                            radioButton.setTextColor(Color.BLACK);
                        }
                    }
                }
            });

        }


    }



