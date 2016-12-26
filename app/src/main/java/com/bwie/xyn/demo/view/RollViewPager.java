package com.bwie.xyn.demo.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class RollViewPager extends ViewPager {

    private DisplayImageOptions options;
    private List<String> imgUrlList;
    private int[] dotArray;
    private List<View> dotList;
    private CallBack callBack;
    private MyPagerAdapter myPagerAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = RollViewPager.this.getCurrentItem();
            currentItem++;
            RollViewPager.this.setCurrentItem(currentItem);
            this.sendEmptyMessageDelayed(0, 2000);

        }
    };


    public RollViewPager(Context context) {
        super(context);
        init();
    }

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //加载imgloder
        options = ImageLoaderUtils.initOptions();
    }

    public void startPage() {

        if (myPagerAdapter == null) {
            myPagerAdapter = new MyPagerAdapter();
        }
        this.setAdapter(myPagerAdapter);
        handler.sendEmptyMessageDelayed(0, 2000);
    }
    public void starNoRoolltPage() {

        if (myPagerAdapter == null) {
            myPagerAdapter = new MyPagerAdapter();
        }
        this.setAdapter(myPagerAdapter);
      handler = null;
    }
    public void initData(final List<String> imgUrlList, final int[] dotArray, final List<View> dotList) {
        this.imgUrlList = imgUrlList;
        this.dotArray = dotArray;
        this.dotList = dotList;
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(dotArray!=null&&dotArray!=null){
                for (int i = 0; i < dotList.size(); i++) {
                    if (position % imgUrlList.size() == i) {
                        dotList.get(i).setBackgroundResource(dotArray[0]);
                    } else {
                        dotList.get(i).setBackgroundResource(dotArray[1]);
                    }
                }}
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //接口回调
    public interface CallBack {
        void sendPossion(int possion);
    }

    public void setCallback(CallBack callBack) {
        this.callBack = callBack;
    }

    //屏幕不可见时
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(handler!=null)
        handler.removeCallbacksAndMessages(null);
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = CommonUtils.inflate(R.layout.rool_item);
            ImageView img = (ImageView) view.findViewById(R.id.img_rool);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            //img.setImageResource(R.mipmap.ic_launcher);
            ImageLoader.getInstance().displayImage(imgUrlList.get(position % imgUrlList.size()), img, options);
            container.addView(view);
            view.setOnTouchListener(new OnTouchListener() {

                private long ttme;
                private float downY;
                private float downX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downX = event.getX();
                            downY = event.getY();
                            ttme = System.currentTimeMillis();
                            if(handler!=null)
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            float upX = event.getX();
                            float upY = event.getY();
                            long upTime = System.currentTimeMillis();
                            if (downX == upX && downY == upY && upTime - ttme < 1000) {
                                if (callBack != null)
                                    callBack.sendPossion(position);
                            }

                        case MotionEvent.ACTION_CANCEL:
                            if(handler!=null)
                            handler.sendEmptyMessageDelayed(0, 2000);
                            break;

                    }

                    return true;
                }
            });
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
