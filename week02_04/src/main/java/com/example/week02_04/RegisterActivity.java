package com.example.week02_04;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week02_04.bean.PhoneBean;
import com.example.week02_04.presenter.IPresenterImpl;
import com.example.week02_04.utils.NullUtils;
import com.example.week02_04.view.IView;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private EditText name,password,verification_Code;
    private TextView send_verification_code;
    private Button register;
    private String names;
    private String pass;
    private IPresenterImpl iPresenter;
    private String url="http://120.27.23.105/user/reg?mobile=%s&password=%s";
    private int trim=10;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if (trim>0){
               trim--;
               send_verification_code.setText(trim+"s");
               handler.sendEmptyMessageDelayed(0,1000);
           }else{
               send_verification_code.setText("发送验证码");
               trim=10;
           }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_item);
        iPresenter=new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        //获取资源id
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        verification_Code=findViewById(R.id.verification_Code);
        send_verification_code=findViewById(R.id.send_verification_code);
        //输入框事件
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入手机号11位时，密码框获取焦点
                if (s.length()==11){
                    password.setFocusable(true);
                    password.setFocusableInTouchMode(true);

                }else{
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
                //当密码框输入6位以上时，验证码可以获取
                if (s.length()>=6){
                    verification_Code.setFocusable(true);
                    verification_Code.setFocusableInTouchMode(true);
                }else{
                    verification_Code.setFocusable(false);
                    verification_Code.setFocusableInTouchMode(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verification_Code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当验证码可以获取到时，注册按钮可以点击
                if (s.length()>0){
                    register.setEnabled(true);
                }else{
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //点击注册
        register.setOnClickListener(this);
        //点击获取验证码
        send_verification_code.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //注册
            case R.id.register:
                names = name.getText().toString().trim();
                pass = password.getText().toString().trim();
                if (NullUtils.getInsanner().isNull(names, pass)){
                    iPresenter.getRequeryData(String.format(url, names, pass),null,PhoneBean.class);
                }else{
                    Toast.makeText(RegisterActivity.this,"请输入账号或密码",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.send_verification_code:
                Random random = new Random();
                int i = random.nextInt(999999);
                verification_Code.setText(i+"");
                handler.sendEmptyMessageDelayed(0,1000);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object o) {
        String result= (String) o;
        Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(String str) {
        Toast.makeText(RegisterActivity.this,str,Toast.LENGTH_SHORT).show();
    }
}
