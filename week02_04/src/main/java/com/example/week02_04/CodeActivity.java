package com.example.week02_04;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class CodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView zXingView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_item);
        //获取资源id
        zXingView=findViewById(R.id.zixngview);
        zXingView.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //打开相机
        zXingView.startCamera();
        //显示扫描框
        zXingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭相机
        zXingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁
        zXingView.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
