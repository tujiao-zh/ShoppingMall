package com.tokaku.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getStar(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("star", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void saveStar(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("star", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getHistory(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void saveHistory(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }


}
