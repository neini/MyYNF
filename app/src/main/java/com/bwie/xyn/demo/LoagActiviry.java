package com.bwie.xyn.demo;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.application.MyApplication;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 这是登录的界面
 */
public class LoagActiviry extends AppCompatActivity implements View.OnClickListener {

    private Button bt_loag;
    private EditText et_mima_loag;
    private EditText et_num_loag;
    private LinearLayout ll_mobile_loag;
    private EditText et_yan_loag;
    private RadioGroup rg_loag;
    private TextView tv_other_loag;
    private TextView tv_zu_loag;
    private TextView tv_hy_loag;
    private ImageView img_back;
    private TextView tv_nima_loag;
    private LinearLayout parent;
    private PopupWindow popWindow;
    private PopupWindow popupWindow;
    private View otherLoagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loagin_page);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        otherLoagView = CommonUtils.inflate(R.layout.showtype);
        parent = (LinearLayout) findViewById(R.id.loagmain);
        tv_nima_loag = (TextView) findViewById(R.id.tv_nima_loag);
        img_back = (ImageView) findViewById(R.id.img_back);
        bt_loag = (Button) findViewById(R.id.bt_loag);
        et_mima_loag = (EditText) findViewById(R.id.et_mima_loag);
        et_num_loag = (EditText) findViewById(R.id.et_num_loag);
        ll_mobile_loag = (LinearLayout) findViewById(R.id.ll_mobile_loag);
        et_yan_loag = (EditText) findViewById(R.id.et_yan_loag);
        rg_loag = (RadioGroup) findViewById(R.id.rg_loag);
        tv_other_loag = (TextView) findViewById(R.id.tv_other_loag);
        tv_zu_loag = (TextView) findViewById(R.id.tv_zu_loag);
        tv_hy_loag = (TextView) findViewById(R.id.tv_hy_loag);
        otherLoagView.findViewById(R.id.img_qq).setOnClickListener(this);
        img_back.setOnClickListener(this);
        bt_loag.setOnClickListener(this);
        tv_hy_loag.setOnClickListener(this);
        tv_zu_loag.setOnClickListener(this);
        bt_loag.setOnClickListener(this);
        tv_other_loag.setOnClickListener(this);

        rg_loag.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_loag:
                        et_mima_loag.setVisibility(View.VISIBLE);
                        ll_mobile_loag.setVisibility(View.GONE);
                        tv_nima_loag.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_mobile_loag:
                        et_mima_loag.setVisibility(View.GONE);
                        ll_mobile_loag.setVisibility(View.VISIBLE);
                        tv_nima_loag.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    public void showPop(View v) {

        popupWindow = new PopupWindow(v,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_other_loag:
                showPop(otherLoagView);
                break;
            case R.id.img_qq:
                UMShareAPI mShareAPI = UMShareAPI.get(LoagActiviry.this);
                mShareAPI.getPlatformInfo(LoagActiviry.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            MyApplication.isLogin = true;
            CommonUtils.saveSp("user_name", data.get("screen_name"));//用户民
            CommonUtils.saveSp("user_icon", data.get("profile_image_url"));//头像
            finish();
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
}
