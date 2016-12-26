package com.bwie.xyn.demo;

import android.app.ProgressDialog;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.application.MyApplication;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.DataClearManager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 这是设置的的界面
 */

public class SetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back_set;
    private LinearLayout ll_know_set;
    private LinearLayout ll_aboutus_set;
    private LinearLayout ll_clear_set;
    private LinearLayout ll_updata_set;
    private LinearLayout ll_yijian_set;
    private Button bt_set_unloag;
    private LinearLayout ll_call_set;
    private TextView tv_cun_set;
    private TextView tv_uagate_set;
    private File file;
    private TextView tv_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        getSupportActionBar().hide();
        initView();
        isLoaging();
        file = getCacheDir();
        setSize();
        getBan();


    }

    //设置缓存大小的方法
    public void setSize() {
        try {
            String size = DataClearManager.getCacheSize(file);
            tv_cun_set.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_call = (TextView) findViewById(R.id.tv_call);
        tv_cun_set = (TextView) findViewById(R.id.tv_cun_set);
        tv_uagate_set = (TextView) findViewById(R.id.tv_uagate_set);
        img_back_set = (ImageView) findViewById(R.id.img_back_set);
        ll_know_set = (LinearLayout) findViewById(R.id.ll_know_set);
        ll_aboutus_set = (LinearLayout) findViewById(R.id.ll_aboutus_set);
        ll_clear_set = (LinearLayout) findViewById(R.id.ll_clear_set);
        ll_updata_set = (LinearLayout) findViewById(R.id.ll_updata_set);
        ll_yijian_set = (LinearLayout) findViewById(R.id.ll_yijian_set);
        bt_set_unloag = (Button) findViewById(R.id.bt_set_unloag);
        ll_call_set = (LinearLayout) findViewById(R.id.ll_call_set);
        img_back_set.setOnClickListener(this);
        ll_know_set.setOnClickListener(this);
        ll_aboutus_set.setOnClickListener(this);
        ll_clear_set.setOnClickListener(this);
        ll_updata_set.setOnClickListener(this);
        ll_yijian_set.setOnClickListener(this);
        bt_set_unloag.setOnClickListener(this);
        ll_call_set.setOnClickListener(this);


    }

    public void getBan() {
        RequestParams params = new RequestParams("");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //判断是否登录
    public void isLoaging() {
        if (MyApplication.isLogin) {
            bt_set_unloag.setVisibility(View.VISIBLE);
        } else {
            bt_set_unloag.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //后退
            case R.id.img_back_set:
                finish();
                break;
            //清楚缓存
            case R.id.ll_clear_set:
                DataClearManager.deleteFolderFile(file.getPath(), true);
                setSize();
                break;
            //更新版本
            case R.id.ll_updata_set:
                loagApk();
                //拨打电话
            case R.id.ll_call_set:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_call.getText().toString().trim()));
                startActivity(intent);
                break;
            //退出登录
            case R.id.bt_set_unloag:
                MyApplication.isLogin = false;//设置登录状态
                CommonUtils.saveSp("user_name", null);//用户民
                CommonUtils.saveSp("user_icon", null);//头像
                break;
        }

    }

    /*
    获取当前版本号
     */
    public String getVersionName() {
        PackageManager pa = getPackageManager();
        try {
            PackageInfo packageInfo = pa.getPackageInfo(getPackageName(), 0);
            String visionName = packageInfo.versionName;
            return visionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    跳转到安装界面
     */

    public void junpInStall() {
        //安装apk界面打开
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");

        Log.i("AAAA", "-----" + file.getAbsolutePath());
        intent.setDataAndType(
                Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /*
下载apk
 */
    public void loagApk() {

        RequestParams params = new RequestParams("");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        x.http().get(params, new Callback.ProgressCallback<File>() {
            //成功的方法
            @Override
            public void onSuccess(File file) {
                progressDialog.dismiss();
                junpInStall();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long l, long l1, boolean b) {
                progressDialog.setMax((int) l);
                progressDialog.setProgress((int) l1);
            }
        });
    }
}


