package com.tokaku.shoppingmall.cart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokaku.shoppingmall.GoodsBean;
import com.tokaku.shoppingmall.R;
import com.tokaku.shoppingmall.cart.adapter.OrderAdapter;
import com.tokaku.shoppingmall.cart.utils.CartStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Settlement extends Activity {
    private RecyclerView rv_goodsOrder;
    private
    List<GoodsBean> goodsSelectedList;
    private TextView count;
    private TextView total;
    private Button subOrder;

    private String totalPrice;
    private int goodsCount;
    GoodsBean oneGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        Intent intent = getIntent();
        oneGood = (GoodsBean) intent.getSerializableExtra("oneGood");
        if (oneGood!=null){
            goodsSelectedList = new ArrayList<>();
            goodsSelectedList.add(oneGood);
        }else {
            goodsSelectedList = CartStorage.getInstance().getSelectedData();
        }

        getTotal();
        findAllView();
        setView();
        setOnClick();

    }

    private void setOnClick() {
        /**
         * 提交订单
         */
        subOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存订单信息

                //从购物车中删除已购买
                if (oneGood==null){
                    CartStorage.getInstance().deleteSelectData();
                }else {
                    CartStorage.getInstance().deleteData(oneGood);
                }
                finish();
            }
        });
    }

    private void findAllView() {
        View.inflate(this,R.layout.activity_settlement,null);
        rv_goodsOrder = findViewById(R.id.order_goods);
        count = findViewById(R.id.settlement_count);
        total = findViewById(R.id.total);
        subOrder = findViewById(R.id.submitOrder);
    }

    private void setView() {
        String s = "共" + goodsCount + "件商品";
        count.setText(s);
        total.setText(totalPrice);
        rv_goodsOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        OrderAdapter adapter = new OrderAdapter(this, goodsSelectedList,subOrder);
        rv_goodsOrder.setAdapter(adapter);
    }

    private void getTotal() {
        double totalPrice = 0;
        for (GoodsBean goodsBean : goodsSelectedList) {
            goodsCount += goodsBean.getGoods_num();
            totalPrice += goodsBean.getGoods_num()*Double.parseDouble(goodsBean.getPrice());
        }
        this.totalPrice = String.valueOf(totalPrice);
    }


}