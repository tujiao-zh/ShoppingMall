package com.tokaku.shoppingmall.my.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tokaku.shoppingmall.GoodsBean;
import com.tokaku.shoppingmall.MyApplication;
import com.tokaku.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class StarStorage {
    private static final String JSON_STAR = "json_star";
    private static StarStorage instance;
    private Context context;
    SparseArray<GoodsBean> sparseArray;

    private StarStorage(Context context) {
        this.context = context;
        sparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    public static StarStorage getInstance() {
        if (instance == null) {
            instance = new StarStorage(MyApplication.getContext());
        }
        return instance;
    }

    private void listToSparseArray() {
        List<GoodsBean> list = getAllData();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GoodsBean goodsBean = list.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getId()), goodsBean);
            }
        }
    }

    private List<GoodsBean> sparseToArraytolist() {
        List<GoodsBean> list = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            list.add(goodsBean);
        }
        return list;
    }

    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        String json = CacheUtils.getStar(context, JSON_STAR);
        if (!TextUtils.isEmpty(json)) {
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeanList;
    }

    public void addData(GoodsBean goodsBean) {
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getId()));
        if (tempData == null) {
            tempData = goodsBean;
            sparseArray.put(Integer.parseInt(tempData.getId()), tempData);
            commit();
        }
    }

    public boolean haveData(GoodsBean goodsBean) {
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getId()));
        if (tempData == null) {
            return false;
        }else {
            return true;
        }
    }

    public void deleteData(GoodsBean goodsBean) {
        sparseArray.delete(Integer.parseInt(goodsBean.getId()));
        commit();
    }

    public void updateData(GoodsBean goodsBean) {
        sparseArray.put(Integer.parseInt(goodsBean.getId()), goodsBean);
        commit();
    }

    private void commit() {
        List<GoodsBean> list = sparseToArraytolist();
        String json = new Gson().toJson(list);
        CacheUtils.saveStar(context, JSON_STAR, json);
    }

}
