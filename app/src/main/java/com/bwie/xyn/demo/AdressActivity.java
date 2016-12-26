package com.bwie.xyn.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.AdressRoot;
import com.bwie.xyn.demo.dao.AdressDao;
import com.bwie.xyn.demo.utils.CommonUtils;

public class AdressActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_right;
    private EditText et_adress;
    private EditText et_person_ad;
    private EditText et_phone_ad;
    private EditText et_city_ad;
    private TextView tv_title;
    private StringBuilder stringBuilder;
    private String pName;
    private String pPhone;
    private String pCity;
    private String pAdress;
    private AdressRoot adressRoot;
    private AdressDao dao;
    public static final int SURPAY_RETYPE = 100;
    private Intent intent;
    private AdressRoot adr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        getSupportActionBar().hide();
        dao = new AdressDao(this);
        initView();
        getIntentData();
    }

    public void getIntentData() {
        intent = getIntent();
        //修改地址
        adr = (AdressRoot) intent.getSerializableExtra("adr");
           if(adr != null) {
               et_adress.setText(adr.getpAdress());
               et_city_ad.setText(adr.getpCity());
               et_person_ad.setText(adr.getpName());
               et_phone_ad.setText(adr.getpPhone());
           }
    }

    private void initView() {
        findViewById(R.id.img_back).setOnClickListener(this);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("新建收货地址");
        findViewById(R.id.img_other_loading).setVisibility(View.GONE);
        et_adress = (EditText) findViewById(R.id.et_adress);
        et_person_ad = (EditText) findViewById(R.id.et_person_ad);
        et_phone_ad = (EditText) findViewById(R.id.et_phone_ad);
        et_city_ad = (EditText) findViewById(R.id.et_city_ad);
    }

    public void getEtData() {
        pName = et_person_ad.getText().toString().trim();
        pPhone = et_phone_ad.getText().toString().trim();
        pCity = et_city_ad.getText().toString().trim();
        pAdress = et_adress.getText().toString().trim();
        if (TextUtils.isEmpty(pName)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pPhone)) {
            Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pAdress)) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        adressRoot = new AdressRoot(pName, pPhone, pCity, pAdress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //后退
            case R.id.img_back:
                finish();
                break;
            //保存
            case R.id.tv_right:
                getEtData();
                if (adressRoot == null) {
                    return;
                }
                if (adr != null) {
                    dao.update(adressRoot, adr.getId());
                    jump(intent, 600);
                } else if (dao.select().size() == 0) {
                    dao.add(adressRoot);
                    jump(intent, SURPAY_RETYPE);

                } else {
                    dao.add(adressRoot);
                    jump(intent, 200);
                }
                AdressActivity.this.finish();
                break;
        }
    }


    //跳转页面方法
    public void jump(Intent intent, int resultCode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("adress", adressRoot);
        intent.putExtras(bundle);
        setResult(resultCode, intent);
    }
}
