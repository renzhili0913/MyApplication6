package com.example.week02_04;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.week02_04.adapter.MyFragmentAdapter;
import com.example.week02_04.model.MyCallBack;


public class LoginActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyCallBack myCallBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_item);
        initView();
    }

    private void initView() {
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        //创建适配器
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);



    }
    //传值到fragment
    public void  getData(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
        //接受zhi
        Intent intent = getIntent();
        String names = intent.getStringExtra("names");
        myCallBack.setData(names);
    }
}
