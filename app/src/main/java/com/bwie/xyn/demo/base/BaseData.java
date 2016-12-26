package com.bwie.xyn.demo.base;

import android.provider.Settings;
import android.util.Log;

import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.MD5Encoder;
import com.bwie.xyn.demo.view.ShowinPage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Administrator on 2016/11/29.
 */
public abstract class BaseData {

    private final File fileDir;
    public static final int NOTIME = 0;
    public static final int NORMALTIME = 24 * 60 * 60 * 1000;

    public BaseData() {
        //找到存储路径
        File file = CommonUtils.getContext().getCacheDir();
        //创建存储文件家
        fileDir = new File(file, "ynf");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    //获取数据
    public void getData(String path, String args, int index, int validTime) {
        if (validTime == 0) {
            getDataFromNet(path, args, index, validTime);
        } else {
            String data = getDataFromLocal(path, args, index, validTime);
            if (data == null) {
                getDataFromNet(path, args, index, validTime);
            } else {
                setResultData(data);
            }

        }

    }

    public abstract void setResultData(String data);

    private String getDataFromLocal(String path, String args, int index, int validTime) {

        try {
            File file = new File(fileDir, MD5Encoder.encode(path+args) + index);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = bufferedReader.readLine();
            long time = Long.parseLong(s);
            if (System.currentTimeMillis() < time) {
                StringBuilder builder = new StringBuilder();
                String len;
                while ((len = bufferedReader.readLine()) != null) {
                    builder.append(len);
                }
                return builder.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        RequestParams requestParams = new RequestParams(path + "?" + args);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String s) {
                setResultData(s);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writeToLocal(path, args, index, validTime, s);
                    }
                }).start();

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                setResultDataError(ShowinPage.StateType.STATE_ERROR);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    protected abstract void setResultDataError(ShowinPage.StateType stateError);

    private void writeToLocal(String path, String args, int index, int validTime, String s) {
        try {
            File file = new File(fileDir, MD5Encoder.encode(path+args) + index);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(System.currentTimeMillis() + validTime + "\r\n");
            bufferedWriter.write(s);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
