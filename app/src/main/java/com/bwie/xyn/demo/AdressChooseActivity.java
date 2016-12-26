package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.xyn.demo.bean.AdressRoot;
import com.bwie.xyn.demo.dao.AdressDao;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class AdressChooseActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tv_title, tv_right;
    private ListView lv_adMa;
    private AdressDao dao;
    private List<AdressRoot> adressRootList;
    public static final int SURE_RETYPR = 400;
    private Intent intent;
    private TextView tv_lv;
    private BaseAdapter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_manger);
        dao = new AdressDao(this);
        adressRootList = dao.select();
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adressRootList = dao.select();
        adpter.notifyDataSetChanged();
    }

    //设置ListvView数据 并设置listView的点击事件
    private void initData() {
           if(adpter == null) {
               adpter = new CommonAdapter<AdressRoot>(this, R.layout.lv_tv2, adressRootList) {


                   @Override
                   protected void convert(ViewHolder viewHolder, AdressRoot item, int position) {
                       tv_lv = viewHolder.getView(R.id.tv_lv);
                       viewHolder.setText(R.id.tv_lv, item.getpName() + "  " + item.getpPhone() + "\r\n" + item.getpCity() + item.getpAdress());
                   }
               };
           }
        lv_adMa.setAdapter(adpter);
        adpter.notifyDataSetChanged();
        lv_adMa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = getIntent();
                jump(intent, SURE_RETYPR, adressRootList.get(position));
                AdressChooseActivity.this.finish();
            }
        });


    }

    // 带值回传的方法
    public void jump(Intent intent, int resultCode, AdressRoot adressRoot) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("adress", adressRoot);
        intent.putExtras(bundle);
        setResult(resultCode, intent);
    }

    //找控件
    private void initView() {
        findViewById(R.id.bt_addAdr).setOnClickListener(this);
        findViewById(R.id.img_back).setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选择收货地址");
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
        tv_right.setOnClickListener(this);
        findViewById(R.id.img_other_loading).setVisibility(View.GONE);
        lv_adMa = (ListView) findViewById(R.id.lv_adMa);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //后退
            case R.id.img_back:
                finish();
                break;
            //跳到管理地址页面
            case R.id.tv_right:

                tv_right.setVisibility(View.GONE);
                tv_title.setText("管理收货地址");
                lv_adMa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        intent =  new Intent(AdressChooseActivity.this,AdressActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("adr", adressRootList.get(position));
                        intent.putExtras(bundle);
                        startActivityForResult(intent,500);
                    }
                });
                break;
            //跳到新建地址页面
            case R.id.bt_addAdr:
                intent = new Intent(this, AdressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
