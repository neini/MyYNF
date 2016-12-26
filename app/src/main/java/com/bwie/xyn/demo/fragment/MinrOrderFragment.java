package com.bwie.xyn.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.dao.GoodsDao;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/18.
 */
public class MinrOrderFragment extends Fragment {

    private View view;
    private ListView lv_morderFragment;
    private GoodsDao dao;
    private String table;
    private List<GoodsInfo.DataBean.GoodsBean> list;
    private ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mineorder_fragment, null);
        dao = new GoodsDao(getActivity());
        initView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
            if(list != null){
                list.clear();
            }
        table = (String) getArguments().get("table");
        list = dao.select(table);
        if (list.size() <= 0) {
            img.setVisibility(View.VISIBLE);
            lv_morderFragment.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.GONE);
            lv_morderFragment.setVisibility(View.VISIBLE);
            lv_morderFragment.setAdapter(new CommonAdapter<GoodsInfo.DataBean.GoodsBean>(getActivity(), R.layout.item_lv_cart, list) {
                @Override
                protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.GoodsBean item, int position) {
                    viewHolder.getView(R.id.cbt_cart).setVisibility(View.GONE);
                    ImageView img_cart = viewHolder.getView(R.id.img_cart);
                    ImageLoader.getInstance().displayImage(item.getGoods_img(), img_cart);
                    viewHolder.setText(R.id.tv_name_cart, item.getGoods_name());
                    viewHolder.setText(R.id.tv_price_cart, "价格:" + item.getShop_price());
                    viewHolder.setText(R.id.tv_no_cart, "数量：" + item.getNum());

                }
            });
              lv_morderFragment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                  @Override
                  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                      dao.delete(table,list.get(position).getId());
                      initData();
                      return true;
                  }
              });
        }
    }

    public void initView() {
        lv_morderFragment = (ListView) view.findViewById(R.id.lv_morderFragment);
        img = (ImageView) view.findViewById(R.id.img);
    }

    public static Fragment getFragmentData(String table) {
        MinrOrderFragment minrOrderFragment = new MinrOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("table", table);
        minrOrderFragment.setArguments(bundle);
        return minrOrderFragment;
    }

}
