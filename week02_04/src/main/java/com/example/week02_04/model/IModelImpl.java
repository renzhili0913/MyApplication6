package com.example.week02_04.model;

import com.example.week02_04.utils.NetUtils;

public class IModelImpl implements IModel {
    private MyCallBack myCallBack;
    @Override
    public void getRequeryData(String url, String params, Class clazz, final MyCallBack myCallBack) {
        this.myCallBack=myCallBack;
        NetUtils.getInsanner().getRequery(url, clazz, new NetUtils.CallBack() {
            @Override
            public void onSuccess(Object o) {
                myCallBack.setData(o);
            }
        });
    }
}
