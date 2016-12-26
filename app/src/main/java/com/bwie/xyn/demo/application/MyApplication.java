package com.bwie.xyn.demo.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MyApplication extends Application{

    private  static Context context;
    private static Handler handler;
    private  static int mainThreadId;
    public static boolean isLogin = false;//判定是否登录

    @Override
    public void onCreate() {
        super.onCreate();

        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
        //设置获取上下文
        context = getApplicationContext();
         //设置获取handler
        handler = new Handler();
        //设置获取主线程的线程号
        mainThreadId = Process.myTid();
        //imageloader的初始化
        ImageLoaderUtils.initConfiguration(this);
       // xutils的初始化设置
        x.Ext.init(this);
        //设置debug模式
        x.Ext.setDebug(true);


    }

    public static Context getContext(){
        return  context;
    }

    public  static Handler getHandler(){
        return  handler;
    }

    public static int getMainThreadId(){
        return  mainThreadId;
    }
    public static Thread getMainThread(){
        return  Thread.currentThread();
    }
}
