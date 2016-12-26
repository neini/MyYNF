package com.bwie.xyn.demo;

import android.animation.BidirectionalTypeConverter;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.fragment.HomeFragment;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.NetUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.ShowinPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FristActivity extends AppCompatActivity {

    private List<View> pageList;
    private ViewPager vp_frist;
    private ScheduledExecutorService ses;
    private int i = 0;
    private int t = 3;
    private Button bt_jump_page2;
    private Timer timer;
    private View page1;
    private View page2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        getSupportActionBar().hide();

        initData();
        initView();

    }

    private void getDataFromNet() {
        //判断网络是否连接
        int netWorkType = NetUtils.getNetWorkType(this);
        if (netWorkType != 0) {
            new BaseData() {
                @Override
                public void setResultData(String data) {
                }
                @Override
                protected void setResultDataError(ShowinPage.StateType stateError) {
                }
            }.getData(URLUtils.homeUrl, URLUtils.homeArgs, 1, BaseData.NORMALTIME);
        }

    }

    private void initView() {
        bt_jump_page2 = (Button) page2.findViewById(R.id.bt_jump_page2);
        bt_jump_page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FristActivity.this, PageLogActivity.class);
                startActivity(intent);
                 timer.cancel();
                  finish();
            }
        });
        vp_frist.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View view = pageList.get(position);
                if (view.getParent() != null) {
                    ViewPager vp = (ViewPager) view.getParent();
                    vp.removeView(view);
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(pageList.get(position));
            }

            @Override
            public int getCount() {
                return pageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }

    private void initData() {
        getDataFromNet();
        vp_frist = (ViewPager) findViewById(R.id.vp_frist);

        page1 = CommonUtils.inflate(R.layout.page1_frist);
        page2 = CommonUtils.inflate(R.layout.page2_frist);

        pageList = new ArrayList<View>();
        pageList.add(page1);
        pageList.add(page2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Task(), 3, 4, TimeUnit.SECONDS);

    }

    @Override
    protected void onStop() {
        super.onStop();
        ses.shutdown();
        timer.cancel();
    }

    class Task implements Runnable {



        @Override
        public void run() {
            i++;
            if (i == 1) {

                timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        t--;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                bt_jump_page2.setText("跳过" + (t + 1) + "s");
                                if (t == 0) {
                                    Intent intent = new Intent(FristActivity.this, PageLogActivity.class);
                                    startActivity(intent);
                                    ses.shutdown();
                                    timer.cancel();
                                    finish();
                                }
                            }
                        });

                    }
                };
                timer.schedule(task, 0, 1000);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vp_frist.setCurrentItem(i);
                    if (i >= 1) {
                        ses.shutdown();
                    }
                }
            });
        }
    }
}
