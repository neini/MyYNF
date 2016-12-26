package com.bwie.xyn.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xyn.demo.GoodsInFoActivity;
import com.bwie.xyn.demo.LoagActiviry;
import com.bwie.xyn.demo.MainActivity;
import com.bwie.xyn.demo.PageLogActivity;
import com.bwie.xyn.demo.R;
import com.bwie.xyn.demo.SurePayActivity;
import com.bwie.xyn.demo.base.BaseFragment;
import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.dao.GoodsDao;
import com.bwie.xyn.demo.factory.FragmentFactory;
import com.bwie.xyn.demo.pay.PayDemoActivity;
import com.bwie.xyn.demo.utils.CommonUtils;
import com.bwie.xyn.demo.utils.ImageLoaderUtils;
import com.bwie.xyn.demo.view.ShowinPage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {

    private View loagView;
    private GoodsDao dao;

    private List<GoodsInfo.DataBean.GoodsBean> goodslist;
    private View view;
    private DisplayImageOptions options;
    private TextView tv_carNum;
    private ListView lv_cart;
    private TextView tv_carEdit;
    private CheckBox cb_car_selectAll;
    private BaseAdapter adapter;
    private TextView tv_car_tolMoney;
    private Button bt_car_delete;
    private Button bt_car_pay;
    private RelativeLayout rl_car;
    private RelativeLayout rl_car_empty;
    private Button bt_car_shoping;
    private boolean flag;
    int num = 0;
    private int count = 0;
    private int tem;

    @Override
    public void onLoad() {
        CartFragment.this.showCurrentView(ShowinPage.StateType.STATE__LOAD_SUCCESS);
    }

    @Override
    public void onResume() {
        super.onResume();
        goodslist = dao.select("cardata");
        initCarView();
        initData();
        showView();

    }

    @Override
    public View createSuccessView() {
        dao = new GoodsDao(getActivity());
        view = CommonUtils.inflate(R.layout.cart_fragment);
        return view;
    }

    //当购物车里有东西的时候显示
    private void showCarData() {
        rl_car_empty.setVisibility(View.GONE);
        rl_car.setVisibility(View.VISIBLE);
    }

    //判断是否有数据显示页面
    public void showView() {
        if (goodslist.size() == 0) {
            showEmpty();
        } else {
            showCarData();
        }
    }

    //设置listview数据
    private void setLvAdapterData() {
        adapter = new CommonAdapter<GoodsInfo.DataBean.GoodsBean>(getActivity(), R.layout.item_lv_cart, goodslist) {

            @Override
            protected void convert(ViewHolder viewHolder, final GoodsInfo.DataBean.GoodsBean item, int position) {
                ImageView imageView = viewHolder.getView(R.id.img_cart);
                ImageLoader.getInstance().displayImage(item.getGoods_img(), imageView);
                viewHolder.setText(R.id.tv_name_cart, item.getGoods_name());
                viewHolder.setText(R.id.tv_price_cart, "￥" + item.getShop_price());
                TextView tv_no_cart = viewHolder.getView(R.id.tv_no_cart);
                tv_no_cart.setText("数量:" + item.getNum());
                final LinearLayout ll_no = viewHolder.getView(R.id.ll_no);
                final TextView tv_no = viewHolder.getView(R.id.tv_no);
                tv_no.setText(item.getNum());
                num = Integer.parseInt(item.getNum());
                final ImageButton imgbt_add = viewHolder.getView(R.id.imgbt_add);
                final ImageButton imgbt_reduce = viewHolder.getView(R.id.imgbt_reduce);
                if (flag) {
                    ll_no.setVisibility(View.VISIBLE);
                    tv_no_cart.setVisibility(View.GONE);
                } else {
                    ll_no.setVisibility(View.GONE);
                    tv_no_cart.setVisibility(View.VISIBLE);
                }
                imgbt_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ++num;
                        if (num > 5) {
                            num = 5;
                            imgbt_add.setImageResource(R.mipmap.add_unable);
                        } else {
                            imgbt_add.setImageResource(R.mipmap.add_able);
                        }
                        imgbt_reduce.setImageResource(R.mipmap.reduce_able);
                        tv_no.setText(num + "");
                        item.setNum(num + "");
                    }
                });
                imgbt_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        --num;
                        if (num < 1) {
                            num = 1;
                            imgbt_reduce.setImageResource(R.mipmap.reduce_unable);
                        } else {
                            imgbt_reduce.setImageResource(R.mipmap.reduce_able);
                        }
                        imgbt_add.setImageResource(R.mipmap.add_able);
                        tv_no.setText(num + "");
                        item.setNum(num + "");
                    }
                });

                final CheckBox cbt_cart = viewHolder.getView(R.id.cbt_cart);
                cbt_cart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setFlag(true);
                        } else {
                            item.setFlag(false);
                        }
                        setTotalMoney();
                    }
                });
                cbt_cart.setChecked(item.isFlag());
                setTotalMoney();
            }
        };
        adapter.notifyDataSetChanged();
        lv_cart.setAdapter(adapter);
        lv_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsInFoActivity.class);
                intent.putExtra("id", goodslist.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        count = 0;
        tem = 0;
    }

    //找控件
    private void initCarView() {
        bt_car_shoping = (Button) view.findViewById(R.id.bt_car_shoping);
        rl_car = (RelativeLayout) view.findViewById(R.id.rl_car);
        rl_car_empty = (RelativeLayout) view.findViewById(R.id.rl_car_empty);
        tv_carNum = (TextView) view.findViewById(R.id.tv_carNum);
        lv_cart = (ListView) view.findViewById(R.id.lv_cart);
        tv_carEdit = (TextView) view.findViewById(R.id.tv_carEdit);
        tv_car_tolMoney = (TextView) view.findViewById(R.id.tv_car_tolMoney);
        cb_car_selectAll = (CheckBox) view.findViewById(R.id.cb_car_selectAll);
        bt_car_delete = (Button) view.findViewById(R.id.bt_car_delete);
        bt_car_pay = (Button) view.findViewById(R.id.bt_car_pay);
        tv_carEdit.setOnClickListener(this);
        bt_car_delete.setOnClickListener(this);
        bt_car_pay.setOnClickListener(this);
        bt_car_shoping.setOnClickListener(this);
        cb_car_selectAll.setOnClickListener(this);
    }

    //计算总共价钱
    public void setTotalMoney() {
        double sum = 0;
        tem = 0;
        for (int i = 0; i < goodslist.size(); i++) {
            if (goodslist.get(i).isFlag()) {
                sum += (Double.parseDouble(goodslist.get(i).getShop_price()) * Integer.parseInt(goodslist.get(i).getNum()));
                tem = tem + 1;
            }
        }
        cb_car_selectAll.setChecked(tem == goodslist.size() ? true : false);
        tv_car_tolMoney.setText("总计：￥" + (float) (Math.round(sum * 100)) / 100);
    }

    //删除的方法
    public void delete() {
        List tempList = new ArrayList();
        for (int i = 0; i < goodslist.size(); i++) {
            if (goodslist.get(i).isFlag()) {
                tempList.add(goodslist.get(i));
                dao.delete("cardata", goodslist.get(i).getId());
            }
        }
        goodslist.removeAll(tempList);
        adapter.notifyDataSetChanged();
        showView();

    }

    //当数据库没有数据的时候显示
    private void showEmpty() {
        rl_car_empty.setVisibility(View.VISIBLE);
        rl_car.setVisibility(View.GONE);

    }

    //初始化数据
    public void initData() {
        tv_carNum.setText("购物车" + "(" + goodslist.size() + ")");
        setLvAdapterData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //编辑
            case R.id.tv_carEdit:
                if (tv_carEdit.getText().toString().equals("编辑")) {
                    tv_carEdit.setText("完成");
                    bt_car_delete.setVisibility(View.VISIBLE);
                    tv_car_tolMoney.setVisibility(View.GONE);
                    bt_car_pay.setVisibility(View.GONE);
                    flag = true;

                } else if (tv_carEdit.getText().toString().equals("完成")) {
                    tv_carEdit.setText("编辑");
                    bt_car_delete.setVisibility(View.GONE);
                    tv_car_tolMoney.setVisibility(View.VISIBLE);
                    bt_car_pay.setVisibility(View.VISIBLE);
                    flag = false;
                }
                adapter.notifyDataSetChanged();
                break;
            //删除
            case R.id.bt_car_delete:
                if (tem <= 0) {
                    Toast.makeText(getActivity(), "你还没有选中商品o", Toast.LENGTH_SHORT).show();
                } else {
                    delete();
                }
                break;
            //跳转到首页
            case R.id.bt_car_shoping:
                ((MainActivity) getActivity()).vp_mian.setCurrentItem(0);
                break;
            //结算
            case R.id.bt_car_pay:
                if (tem <= 0) {
                    Toast.makeText(getActivity(), "你还没有选中商品o", Toast.LENGTH_SHORT).show();
                } else {
                    jumpPayActivity();
                }
                break;
            //设置是否全选
            case R.id.cb_car_selectAll:
                if (cb_car_selectAll.isChecked()) {
                    count = goodslist.size();
                    for (int i = 0; i < goodslist.size(); i++) {
                        goodslist.get(i).setFlag(true);
                    }
                } else {
                    for (int i = 0; i < goodslist.size(); i++) {
                        goodslist.get(i).setFlag(false);
                    }
                    count = 0;
                }
                setTotalMoney();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    //跳转到支付确认页面
    private void jumpPayActivity() {
        List<GoodsInfo.DataBean.GoodsBean> tempList = new ArrayList<GoodsInfo.DataBean.GoodsBean>();
        for (int i = 0; i < goodslist.size(); i++) {
            if (goodslist.get(i).isFlag()) {
                tempList.add(goodslist.get(i));
            }
        }
        Intent intent = new Intent(getActivity(), SurePayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) tempList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
