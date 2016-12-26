package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.CategoryRoot;
import com.bwie.xyn.demo.fragment.MaskFragment;
import com.bwie.xyn.demo.utils.CommonUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是肤质的界面
 */
public class FuActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rg_fu;
    private ViewPager vp_fu;
    private CategoryRoot.DataBean.CategoryBean categoryBean;
    private TextView tv_title;
    private int id;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fu);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        rg_fu = (RadioGroup) findViewById(R.id.rg_fu);
        vp_fu = (ViewPager) findViewById(R.id.vp_fu);
        categoryBean = (CategoryRoot.DataBean.CategoryBean) getIntent().getSerializableExtra("bean");
        id = getIntent().getIntExtra("id", 0);
        tv_title.setText(categoryBean.getCat_name());
        initRb();
        initAdapter();

    }

    private void initAdapter() {
        vp_fu.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MaskFragment.getUrlData(categoryBean.getChildren().get(position).getId() + "");
            }
            @Override
            public int getCount() {
                return categoryBean.getChildren().size();
            }
        });

          vp_fu.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              }
              @Override
              public void onPageSelected(int position) {
                        for (int i=0;i<rg_fu.getChildCount();i++){
                              RadioButton radioButton= (RadioButton) rg_fu.getChildAt(i);
                            if(i==position){

                                 radioButton.setTextColor(Color.RED);
                            }else{
                                radioButton.setTextColor(Color.BLACK);
                            }
                        }
              }

              @Override
              public void onPageScrollStateChanged(int state) {

              }
          });
        rg_fu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0;i<rg_fu.getChildCount();i++){
                    RadioButton radioButton= (RadioButton) rg_fu.getChildAt(i);
                    if(checkedId==radioButton.getId()){
                        vp_fu.setCurrentItem(i);
                        radioButton.setTextColor(Color.RED);
                    }else{
                        radioButton.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        vp_fu.setCurrentItem(id);
        RadioButton  rb = (RadioButton) rg_fu.getChildAt(id);
        rb.setChecked(true);

    }

    public void initRb() {
        rg_fu.removeAllViews();
        for (int i = 0; i < categoryBean.getChildren().size(); i++) {
            RadioButton rb = (RadioButton) CommonUtils.inflate(R.layout.rb_item);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
             params.setMargins(20,10,20,10);
            rb.setText(categoryBean.getChildren().get(i).getCat_name());
            rg_fu.addView(rb,params);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
