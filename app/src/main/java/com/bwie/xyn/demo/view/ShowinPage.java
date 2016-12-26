package com.bwie.xyn.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.utils.CommonUtils;

/**
 * Created by Administrator on 2016/11/28.
 */
public abstract class ShowinPage extends FrameLayout implements View.OnClickListener {

    private View showinpage_state_unload;
    private View showinpage_state_load_emptry;
    private View showinpage_state_load_error;
    private View showinpage_state_loading;

    public static final int STATE_UNLOAD = 1;
    public static final int STATE_LOAD_ERROR = 2;
    public static final int STATE_LOAD_SUCCESS = 3;
    public static final int STATE_LOAD_EMPTY = 4;
    public static final int STATE_LOADING = 5;
    //不能写静态的currentState
    public  int currentState=STATE_UNLOAD;
    private View showinpage_state_load_sucess;
    private Button bt_reload;
    private  LayoutParams layoutParams;

    public ShowinPage(Context context) {
        super(context);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (showinpage_state_unload == null) {
            showinpage_state_unload = CommonUtils.inflate(R.layout.showinpage_state_unload);
            this.addView(showinpage_state_unload, layoutParams);
        }
        if (showinpage_state_load_emptry == null) {
            showinpage_state_load_emptry = CommonUtils.inflate(R.layout.showinpage_state_load_emptry);
            this.addView(showinpage_state_load_emptry, layoutParams);
        }
        if (showinpage_state_load_error == null) {
            showinpage_state_load_error = CommonUtils.inflate(R.layout.showinpage_state_load_error);
            this.addView(showinpage_state_load_error, layoutParams);
        }
        if (showinpage_state_loading == null) {
            showinpage_state_loading = CommonUtils.inflate(R.layout.showinpage_state_loading);
            this.addView(showinpage_state_loading, layoutParams);
        }
        showPage();

        onLoad();

    }

    /**
     * 对外提供的设置当前状态的方法
     */
    public void showCurrentPage(StateType stateType) {
      currentState = stateType.getCurrentState();
        showPage();
    }

    public void showPage() {
        CommonUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                showUIPage();
            }
        });
    }

    public void showUIPage() {
        if (showinpage_state_load_emptry != null) {
            showinpage_state_load_emptry.setVisibility(currentState == STATE_LOAD_EMPTY ? View.VISIBLE : GONE);
        }
        if (showinpage_state_load_error != null) {
            bt_reload = (Button) showinpage_state_load_error.findViewById(R.id.bt_reLoad);
            bt_reload.setOnClickListener(this);
            showinpage_state_load_error.setVisibility(currentState == STATE_LOAD_ERROR ? View.VISIBLE : GONE);
        }
        if (showinpage_state_loading != null) {
            showinpage_state_loading.setVisibility(currentState == STATE_LOADING ? View.VISIBLE : GONE);
        }
        if (showinpage_state_unload != null) {
            showinpage_state_unload.setVisibility(currentState == STATE_UNLOAD ? View.VISIBLE : GONE);
        }
        if (showinpage_state_load_sucess == null && currentState == STATE_LOAD_SUCCESS) {
            showinpage_state_load_sucess = createSuccessView();
            this.addView(showinpage_state_load_sucess,layoutParams);
        }
        if (showinpage_state_load_sucess != null) {
            showinpage_state_load_sucess.setVisibility(currentState == STATE_LOAD_SUCCESS ? View.VISIBLE : GONE);
        }

    }

    public abstract View createSuccessView();

    public abstract void onLoad();

    /**
     * 这是重置的方法
     */
    public void restView() {
        if (currentState != STATE_UNLOAD) {
            currentState = STATE_UNLOAD;
        }
        showPage();
        onLoad();

    }

    /**
     * 这是枚举圈起来的范围
     */
    public enum StateType {
        STATE_ERROR(2), STATE_UNLOAD(1), STATE_LOADING(5), STATE_lOAD_EMPTY(4),
        STATE__LOAD_SUCCESS(3);
        private int currentState;

        StateType(int currentState) {
            this.currentState = currentState;
        }

        public int getCurrentState() {
            return currentState;
        }
    }

    /**
     *
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reLoad:
                restView();
                break;
        }

    }
}
