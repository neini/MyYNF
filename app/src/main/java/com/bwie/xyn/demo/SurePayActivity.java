package com.bwie.xyn.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xyn.demo.bean.AdressRoot;
import com.bwie.xyn.demo.bean.GoodsInfo;
import com.bwie.xyn.demo.dao.AdressDao;
import com.bwie.xyn.demo.dao.GoodsDao;
import com.bwie.xyn.demo.pay.PayDemoActivity;
import com.bwie.xyn.demo.view.MyListview;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.List;

public class SurePayActivity extends AppCompatActivity implements View.OnClickListener {

    private MyListview lv_surePay;
    private List<GoodsInfo.DataBean.GoodsBean> goodslist;
    private TextView tv_shownews_sp;
    private float money;
    private int num;
    private TextView tv_money_pay;
    private RadioButton rb_zfb_pay;
    private RadioButton rb_wx_pay;
    private TextView tv_title;
    private TextView tv_adress_surPay;
    private GoodsDao dao;
    private AdressDao adao;
    private List<AdressRoot> adressRootList;
     public static final int NEW_RQTYPE = 200;
    public static final int CHOOSE_RQTYPE = 300;
    private AdressRoot adressRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure_pay);
        getSupportActionBar().hide();
        dao = new GoodsDao(this);
        adao = new AdressDao(this);
        adressRootList = adao.select();
        getIntentData();
        initView();
        if (adressRootList.size() > 0) {
            setAdress(adressRootList.get(0));
        }
        setMoneyData();
        setAdapterData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        adressRootList = adao.select();
    }

    public void setAdress(AdressRoot adressRoot) {
        tv_adress_surPay.setText(adressRoot.getpName() + "   " + adressRoot.getpPhone() + "\r\n" + adressRoot.getpCity() + adressRoot.getpAdress());
    }

    //设置付款金额
    private void setMoneyData() {
        for (int i = 0; i < goodslist.size(); i++) {
            money += (Float.parseFloat(goodslist.get(i).getShop_price()) * Integer.parseInt(goodslist.get(i).getNum()));
            num += Integer.parseInt(goodslist.get(i).getNum());
        }
        tv_shownews_sp.setText("共计" + num + "件商品，需付款" + money + "元");
        tv_money_pay.setText("实付款：" + money + "元");
    }

    //设置适配器数据
    private void setAdapterData() {
        lv_surePay.setAdapter(new CommonAdapter<GoodsInfo.DataBean.GoodsBean>(this, R.layout.item_lv_cart, goodslist) {
            private ImageButton imgbt_reduce;
            private ImageButton imgbt_add;
            private int num;
            private TextView tv_no;
            private LinearLayout ll_no;
            private TextView tv_no_cart;

            @Override
            protected void convert(ViewHolder viewHolder, final GoodsInfo.DataBean.GoodsBean item, int position) {
                ImageView imageView = viewHolder.getView(R.id.img_cart);
                ImageLoader.getInstance().displayImage(item.getGoods_img(), imageView);
                viewHolder.setText(R.id.tv_name_cart, item.getGoods_name());
                viewHolder.setText(R.id.tv_price_cart, "￥" + item.getShop_price());
                tv_no_cart = viewHolder.getView(R.id.tv_no_cart);
                tv_no_cart.setText("数量" + item.getNum());
                ll_no = viewHolder.getView(R.id.ll_no);
                CheckBox cbt_cart = viewHolder.getView(R.id.cbt_cart);
                cbt_cart.setVisibility(View.GONE);
            }
        });
    }

    //获取购买的数据
    private void getIntentData() {
        Intent intent = getIntent();
        goodslist = (List<GoodsInfo.DataBean.GoodsBean>) intent.getSerializableExtra("list");
    }

    //找控件
    private void initView() {
        lv_surePay = (MyListview) findViewById(R.id.lv_surPay);
        tv_adress_surPay = (TextView) findViewById(R.id.tv_adress_surPay);
        tv_adress_surPay.setOnClickListener(this);
        findViewById(R.id.tv_showAll_sp).setOnClickListener(this);
        tv_shownews_sp = (TextView) findViewById(R.id.tv_shownews_sp);
        findViewById(R.id.bt_pay).setOnClickListener(this);
        tv_money_pay = (TextView) findViewById(R.id.tv_money_pay);
        rb_zfb_pay = (RadioButton) findViewById(R.id.rb_zfb_pay);
        rb_wx_pay = (RadioButton) findViewById(R.id.rb_wx_pay);
        findViewById(R.id.img_back).setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("确认订单");
        findViewById(R.id.img_other_loading).setVisibility(View.GONE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.img_back:
                finish();
                break;
            //跳转地址界面
            case R.id.tv_adress_surPay:
                   if(adressRootList.size() == 0){
                jumpAdress(AdressActivity.class, NEW_RQTYPE);
                   } else{
                     jumpAdress(AdressChooseActivity.class,CHOOSE_RQTYPE);
                   }
                break;
            //显示全部
            case R.id.tv_showAll_sp:
                break;
            //结算
            case R.id.bt_pay:
                if (adao.select().size() == 0) {
                    Toast.makeText(this, "请填写收货地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = goodslist.size() - 1; i >= 0; i--) {
                    dao.add("paydata", goodslist.get(i));
                    dao.delete("cardata", goodslist.get(i).getId());
                }
                //支付宝支付
                if (rb_zfb_pay.isChecked()) {
                    jumpPay(PayDemoActivity.class);
                }
                // 微信支付
                else if (rb_wx_pay.isChecked()) {
                }
                break;
        }
    }

    //跳转到填写地址的界面
    public void jumpAdress(Class cla, int requstCode) {
        Intent intent = new Intent(this, cla);
        startActivityForResult(intent, requstCode);
    }

    // 接受回传的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NEW_RQTYPE:
                switch (resultCode) {
                    case  AdressActivity.SURPAY_RETYPE:
                        adressRoot = (AdressRoot) data.getSerializableExtra("adress");
                        setAdress(adressRoot);
                        break;
                }
                break;
            case  CHOOSE_RQTYPE:
                switch (resultCode) {
                    case AdressChooseActivity.SURE_RETYPR:
                    adressRoot = (AdressRoot) data.getSerializableExtra("adress");
                    setAdress(adressRoot);
                     break;
                }
                break;
        }

    }

    //跳转支付页面
    public void jumpPay(Class cla) {
        Intent intent = new Intent(SurePayActivity.this, cla);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) goodslist);
        intent.putExtra("money", money);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}
