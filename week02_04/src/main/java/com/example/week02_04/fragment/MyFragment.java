package com.example.week02_04.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.week02_04.CodeActivity;
import com.example.week02_04.LoginActivity;
import com.example.week02_04.R;
import com.example.week02_04.model.MyCallBack;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class MyFragment extends Fragment {
    private ImageView scan_QR_code,image_code;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_item,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取资源id
        scan_QR_code=view.findViewById(R.id.scan_QR_code);
        image_code=view.findViewById(R.id.image_code);
        ((LoginActivity) getActivity()).getData(new MyCallBack() {
            @Override
            public void setData(Object o) {
                String name = (String) o;
                QRTask qrTask = new QRTask(getActivity(),image_code);
                qrTask.execute(name);
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        //扫描二维码
        scan_QR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断android版本号是否是6.0以上，（Build.VERSION_CODES.M：表示版本号是6.0）
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    //checkSelfPermission判断当前有没有第二个参数所代表的权限，当前权限为相机权限
                    //如果与给定条件不同，这没有此权限。需要授权
                    if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},100);

                    }  else{
                        //如果版本是6.0以上，且条件满足，授权，直接跳转
                        Intent intent = new Intent(getActivity(),CodeActivity.class);
                        getActivity().startActivity(intent);
                    }
                }else{
                    //版本低于6.0，直接跳转，通过清单文件配置请求权限
                    Intent intent = new Intent(getActivity(),CodeActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(getActivity(), CodeActivity.class));
        }
    }

    //创建静态内部类处理文本框获取到的字符串，并生成二维码赋值与imageview展示
    static class QRTask extends AsyncTask<String,Void,Bitmap> {
        //软引用类型
        private WeakReference<Context> mContent;
        private WeakReference<ImageView> mImageView;

        public QRTask(Context content,ImageView imageView ) {
            mContent=new WeakReference<>(content);
            mImageView=new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }
            //软应用只能通过get()获取到相应的方法，返回要生成的二维码的尺寸大小
            int size = mContent.get().getResources().getDimensionPixelOffset(R.dimen.qr_code_size);
            //返回生成的二维码图片
            return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //生成的二维码不为空就直接赋值与imageview上
            if (bitmap!=null){
                mImageView.get().setImageBitmap(bitmap);
            }else{
                Toast.makeText(mContent.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
