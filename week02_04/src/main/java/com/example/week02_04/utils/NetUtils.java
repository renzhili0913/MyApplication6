package com.example.week02_04.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {
    private static NetUtils insanner;
    private Gson gson;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };
    public NetUtils() {
        gson=new Gson();
    }

    public static NetUtils getInsanner() {
        if (insanner==null){
            insanner=new NetUtils();
        }
        return insanner;
    }
    //定义接口
    public interface CallBack<T>{
        void onSuccess(T t);
    }
    public void getRequery(final String urldata, final Class clazz, final CallBack callBack){
        new Thread(){
            @Override
            public void run() {
                final Object requery = getRequery(urldata, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(requery);
                    }
                });
            }
        }.start();
    }
    //解析
    public <E> E getRequery(String urldata,Class clazz){
        return (E) gson.fromJson(getRequery(urldata),clazz);
    }
    //获取网络数据
    public String getRequery(String urldata){
        String result="";
        try {
            URL url = new URL(urldata);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            if (urlConnection.getResponseCode()==200){
                result=getStram2String(urlConnection.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //字节流转换字符流
    private String getStram2String(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        for (String tmp=br.readLine();tmp!=null;tmp=br.readLine()){
            builder.append(tmp);
        }
        return builder.toString();
    }
}
