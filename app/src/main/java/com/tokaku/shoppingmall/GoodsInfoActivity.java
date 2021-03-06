package com.tokaku.shoppingmall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tokaku.shoppingmall.cart.Settlement;
import com.tokaku.shoppingmall.cart.utils.CartStorage;
import com.tokaku.shoppingmall.my.utils.HistoryStorage;
import com.tokaku.shoppingmall.my.utils.StarStorage;

import static com.tokaku.shoppingmall.utils.urlText.URL_IMG;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {
    private ImageButton back;
    private ImageButton more;
    private ImageView image;
    private TextView price;
    private TextView name;
    private WebView web;
    private ImageView service;
    private CheckBox star;
    private Button add;
    private Button buy;
    private GoodsBean goodsBean;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-06-03 15:34:12 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        back = findViewById( R.id.back );
        more = findViewById( R.id.more );
        image = findViewById( R.id.item_cart_image);
        price = findViewById( R.id.item_cart_price);
        name = findViewById( R.id.item_cart_name);
        web = findViewById( R.id.web );
        service = findViewById( R.id.service );
        star = findViewById( R.id.star );
        add = findViewById( R.id.add );
        buy = findViewById( R.id.buy );

        back.setOnClickListener( this );
        more.setOnClickListener( this );
        add.setOnClickListener( this );
        buy.setOnClickListener( this );
        star.setOnClickListener( this );
        service.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2020-06-03 15:34:12 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == back ) {
            // 返回
            finish();
        } else if ( v == more ) {
            // 更多
            Toast.makeText(this,"更多功能敬请期待！",Toast.LENGTH_SHORT).show();
        } else if ( v == add ) {
            // 加入购物车按钮
            CartStorage.getInstance().addData(goodsBean);
            Toast.makeText(this,"加入购物车",Toast.LENGTH_SHORT).show();
        } else if ( v == buy ) {
            // 立即购买
            Intent intent = new Intent(this, Settlement.class);
            intent.putExtra("oneGood",goodsBean);
            startActivity(intent);
        } else if (v == star) {
            if (goodsBean.isStared()){
                Toast.makeText(this,"该商品已取消收藏",Toast.LENGTH_SHORT).show();
                goodsBean.setStared(false);
                StarStorage.getInstance().deleteData(goodsBean);
            }else {
                Toast.makeText(this,"该商品已加入收藏",Toast.LENGTH_SHORT).show();
                goodsBean.setStared(true);
                StarStorage.getInstance().addData(goodsBean);
            }
            star.setChecked(goodsBean.isStared());
        } else if (v == service) {
            Toast.makeText(this,"转到客服",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();

        Intent intent = getIntent();
        goodsBean = (GoodsBean) intent.getSerializableExtra("goodsBean");
        goodsBean.setStared(StarStorage.getInstance().haveData(goodsBean));
        HistoryStorage.getInstance().addData(goodsBean);
        setData(goodsBean);
    }

    private void setData(GoodsBean goodsBean) {
        Glide.with(this).load(URL_IMG + goodsBean.getImageUrl()).into(image);
        String p = goodsBean.getPrice();
        price.setText(p);
        name.setText(goodsBean.getName());
        star.setChecked(StarStorage.getInstance().haveData(goodsBean));
        web.loadUrl("https://www.baidu.com");
        WebSettings settings = web.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

    }
}
