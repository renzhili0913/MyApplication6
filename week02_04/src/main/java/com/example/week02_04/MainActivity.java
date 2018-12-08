package com.example.week02_04;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.week02_04.bean.PhoneBean;
import com.example.week02_04.presenter.IPresenterImpl;
import com.example.week02_04.utils.NullUtils;
import com.example.week02_04.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private EditText name, password;
    private CheckBox remember_password, automatic_logon;
    private Button login, register;
    private ImageView qq_login;
    private String names;
    private String pass;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private IPresenterImpl iPresenter;
    private String url = "http://120.27.23.105/user/login?mobile=%s&password=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter = new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = preferences.edit();
        //获取资源id
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        remember_password = findViewById(R.id.remember_password);
        automatic_logon = findViewById(R.id.automatic_logon);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        qq_login = findViewById(R.id.qq_login);
        //获取网络权限
        stateNetWork();
        //输入框事件
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    password.setFocusable(true);
                    password.setFocusableInTouchMode(true);

                } else {
                    password.setFocusable(false);
                    password.setFocusableInTouchMode(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //取值判断记住密码是否勾选
        if (preferences.getBoolean("remember", false)) {
            remember_password.setChecked(true);
             names = preferences.getString("names", null);
             pass = preferences.getString("pass", null);
            name.setText(names);
            password.setText(pass);
        }
        //取值判断自动登录是否勾选
        if (preferences.getBoolean("automatic", false)) {
            //请求数据
            iPresenter.getRequeryData(String.format(url, names, pass), null, PhoneBean.class);

        }
        automatic_logon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remember_password.setChecked(true);
                } else {
                    remember_password.setChecked(false);
                }
            }
        });
        //点击事件
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        qq_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.login:
                names = name.getText().toString().trim();
                pass = password.getText().toString().trim();
                if (NullUtils.getInsanner().isNull(names, pass)) {
                    iPresenter.getRequeryData(String.format(url, names, pass), null, PhoneBean.class);
                } else {
                    Toast.makeText(MainActivity.this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
                }
                break;
            //注册
            case R.id.register:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            //第三方qq登录
            case R.id.qq_login:
                UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        //第三方登录成功后跳转
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(Object o) {
        String result = (String) o;
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("names", names);
        startActivity(intent);
        finish();
        //登录成功判断是否记住密码
        if (remember_password.isChecked()) {
            editor.putString("names", names);
            editor.putString("pass", pass);
            editor.putBoolean("remember", true);
            editor.commit();
        } else {
            editor.clear();
            editor.commit();
        }
        //登录成功判断是否勾选自动登录
        if (automatic_logon.isChecked()) {
            editor.putBoolean("automatic", true);
            editor.commit();
        }
    }

    @Override
    public void onFail(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }
    //动态设置网络权限
    private void stateNetWork() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
            };
            ActivityCompat.requestPermissions(this,mStatenetwork,100);
        }
    }
    //动态注册的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission  = false;
        if(requestCode == 100){
            for (int i = 0;i<grantResults.length;i++){
                if(grantResults[i] == -1){
                    hasPermission = true;
                }
            }
        }
    }


}
