package com.bwie.xyn.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.xyn.demo.view.ShowinPage;

/**
 * Created by Administrator on 2016/11/28.
 */
public abstract class BaseFragment extends Fragment {

    private ShowinPage showinPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showinPage = new ShowinPage(getContext()) {
            @Override
            public View createSuccessView() {
                return BaseFragment.this.createSuccessView();
            }

            @Override
            public void onLoad() {
            }
        };
        BaseFragment.this.onLoad();
        return showinPage;
    }

    public abstract void onLoad();

    public abstract View createSuccessView();

    public void showCurrentView(ShowinPage.StateType stateType) {
        if (showinPage != null) {
            showinPage.showCurrentPage(stateType);
        }


    }
}
