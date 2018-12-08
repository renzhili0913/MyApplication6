package com.example.week02_04.utils;

import android.text.TextUtils;

public class NullUtils {
    private static NullUtils insanner;

    public NullUtils() {
    }

    public static NullUtils getInsanner() {
        if (insanner==null){
            insanner=new NullUtils();
        }
        return insanner;
    }
    public boolean isNull(String name,String password){
        return !TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password);
    }
}
