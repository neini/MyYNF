package com.bwie.xyn.demo.factory;

import android.support.v4.app.Fragment;

import com.bwie.xyn.demo.fragment.CartFragment;
import com.bwie.xyn.demo.fragment.CatagoryFragment;
import com.bwie.xyn.demo.fragment.HomeFragment;
import com.bwie.xyn.demo.fragment.MineFragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/28.
 */
public class FragmentFactory {

    public static HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentHashMap.get(position);

        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CatagoryFragment();
                break;
            case 2:
                fragment = new CartFragment();
                break;
            case 3:
                fragment = new MineFragment();
                break;
        }
        fragmentHashMap.put(position,fragment);
        return fragment;
    }
}
