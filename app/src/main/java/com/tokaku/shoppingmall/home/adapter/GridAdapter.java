package com.tokaku.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tokaku.shoppingmall.R;
import com.tokaku.shoppingmall.home.bean.ResultBeanData;

import java.util.List;

import static com.tokaku.shoppingmall.utils.urlText.URL_IMG;

public class GridAdapter extends BaseAdapter {
    private final List<ResultBeanData.ResultBean.ChannelInfoBean> resultDate;
    private final Context mContext;

    public GridAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> resultDate) {
        this.mContext = mContext;
        this.resultDate = resultDate;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_home_channel,null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
             holder = (ViewHolder) convertView.getTag();
        }

        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = resultDate.get(position);
        Glide.with(mContext).load(URL_IMG+channelInfoBean.getImage()).into(holder.imageView);
        System.out.println(URL_IMG+channelInfoBean.getImage());
        holder.textView.setText(channelInfoBean.getChannel_name());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
