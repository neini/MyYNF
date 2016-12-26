package com.bwie.xyn.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_title;
    private ImageView img_dtherLoaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rool);
        getSupportActionBar().hide();
        intView();

        loadWeb();
        goBack();
    }

    private void goBack() {

    }

    private void loadWeb() {
        WebView web_home = (WebView) findViewById(R.id.web_home);
        Intent intent = getIntent();
        if (intent.getStringExtra("url") != null) ;
        {
            web_home.loadUrl(intent.getStringExtra("url"));
        }
    }

    private void intView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        img_dtherLoaging = (ImageView) findViewById(R.id.img_other_loading);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
