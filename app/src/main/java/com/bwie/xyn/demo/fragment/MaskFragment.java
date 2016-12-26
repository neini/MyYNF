package com.bwie.xyn.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.xyn.demo.GoodsInFoActivity;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.base.BaseData;
import com.bwie.xyn.demo.bean.Goods;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.utils.URLUtils;
import com.bwie.xyn.demo.view.MyGrideView;
import com.bwie.xyn.demo.view.ShowinPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class MaskFragment extends Fragment implements SpringView.OnFreshListener {

    private View view;
    private SpringView sv_maskfragment;
    private MyGrideView gv_makfragmnt;
    private DisplayImageOptions options;
    private String urlId;
    private MybaseData mybaseData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mybaseData = new MybaseData();
        view = inflater.inflate(R.layout.mask_fragment, null);
        options = ImageLoaderUtils.initOptions();
        inintView();
        return view;
    }

    private void inintView() {

        sv_maskfragment = (SpringView) view.findViewById(R.id.sv_maskfragment);
        gv_makfragmnt = (MyGrideView) view.findViewById(R.id.gv_makfragmnt);
        sv_maskfragment.setFooter(new DefaultFooter(getActivity()));
        sv_maskfragment.setHeader(new DefaultHeader(getActivity()));
        sv_maskfragment.setListener(this);
        sv_maskfragment.setType(SpringView.Type.FOLLOW);
        urlId = getArguments().getString("urlId");
        getData();

    }
  public void getData(){
      mybaseData.getData(URLUtils.CATEGORYURL1,URLUtils.CATEGORYURL1Args+urlId,0,BaseData.NORMALTIME);
  }

    //对外提供一个加载自己的方法
    public static Fragment getUrlData(String urlId) {
        MaskFragment fragment = new MaskFragment();
        Bundle bundle = new Bundle();
        //根据URLId的不同，获取不同的数据
        bundle.putString("urlId", urlId);
        fragment.setArguments(bundle);
        return fragment;
    }


   //设置适配器数据
    public void setData(final List<Goods.DataBean> goodList) {
        BaseAdapter adapter= new CommonAdapter<Goods.DataBean>(getActivity(), R.layout.homegrideview2_item, goodList) {
            @Override
            protected void convert(ViewHolder viewHolder, Goods.DataBean item, int position) {
                ImageView img = (ImageView) viewHolder.getConvertView().findViewById(R.id.img_gv);
                //  img.setImageResource(R.mipmap.ic_launcher);
                ImageLoader.getInstance().displayImage(item.getGoods_img(), img, options);
                viewHolder.setText(R.id.tv_gv_desc, item.getEfficacy());
                viewHolder.setText(R.id.tv_gv_name, item.getGoods_name());
                viewHolder.setText(R.id.tv_gv_pricenow, "￥" + item.getShop_price());
                TextView tv_gv_priceago = viewHolder.getView(R.id.tv_gv_priceago);
                tv_gv_priceago.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.setText(R.id.tv_gv_priceago, "￥" + item.getMarket_price());
            }
        };
        if(adapter!=null)
            gv_makfragmnt.setAdapter(adapter);
        gv_makfragmnt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), GoodsInFoActivity.class);
                intent.putExtra("id",goodList.get(position).getId());
                startActivity(intent);
            }
        });
        }

    @Override
    public void onRefresh() {

    }
    public void stopLoad() {
        sv_maskfragment.onFinishFreshAndLoad();
    }
    @Override
    public void onLoadmore() {
        stopLoad();
    }
      class MybaseData extends BaseData{

          @Override
          public void setResultData(String data) {
              Gson gson=new Gson();
              Goods goods = gson.fromJson(data, Goods.class);
              List<Goods.DataBean> dataBeanList = goods.getData();
                  setData(dataBeanList);
          }

          @Override
          protected void setResultDataError(ShowinPage.StateType stateError) {

          }
      }
}


