package com.bwie.xyn.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.xyn.demo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class PageLogActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private List<View> viewList;
    private ViewPager vp_pageDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_log);
        getSupportActionBar().hide();
        sp = getSharedPreferences("isfrist", MODE_PRIVATE);
        boolean flag = sp.getBoolean("flag", false);
        if (flag) {
            Intent intent = new Intent(PageLogActivity.this, MainActivity.class);

            startActivity(intent);
            finish();
            return;
        }

        vp_pageDemo = (ViewPager) findViewById(R.id.vp_pagedemo);
        viewList = new ArrayList<>();
        viewList.add(CommonUtils.inflate(R.layout.guide_1));
        viewList.add(CommonUtils.inflate(R.layout.guide_2));
        viewList.add(CommonUtils.inflate(R.layout.guide_3));
        viewList.add(CommonUtils.inflate(R.layout.guide_4));
        viewList.add(CommonUtils.inflate(R.layout.guide_5));
        viewList.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageLogActivity.this, MainActivity.class);
                sp.edit().putBoolean("flag", true).commit();
                startActivity(intent);
                finish();
            }
        });
        vp_pageDemo.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View view = viewList.get(position);
                if (view.getParent() != null) {
                    ViewPager vp = (ViewPager) view.getParent();
                    vp.removeView(view);
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

    }
}
