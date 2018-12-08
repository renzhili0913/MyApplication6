package com.example.week02_04.model;

public interface IModel {
    void getRequeryData(String url, String params, Class clazz, MyCallBack myCallBack);
}
