package com.example.week_01;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.UserBean;
import com.example.dao.UserDao;

import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private List<UserBean> select;
    private WeekFlowLayout fl_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //获取资源id
        fl_search = findViewById(R.id.fl_search);
        WeekFlowLayout fl_hot = findViewById(R.id.fl_hot);
        TitleBarView title = findViewById(R.id.title);
        //查询数据库中的数据展示在历史搜索下的textview中
        select = UserDao.getInsanner(MainActivity.this).select();
        if (select.size() > 0) {
            //遍历查询出来的数据
            for (int i = 0; i < select.size(); i++
                    ) {
                TextView tv = new TextView(MainActivity.this);
                //设置TextView文本
                tv.setText(select.get(i).getName());
                //设置textview的背景，自定义shape
                tv.setBackgroundResource(R.drawable.edit_bg);
                //将textview布局添加到自定义view  历史搜索的WeekFlowLayout中
                fl_search.addView(tv);
                //调用点击事件的方法
                init(tv,select.get(i).getUuid());
            }
        }

        title.setButtonClickListener(new TitleBarView.OnBuutonClickListener() {
            @Override
            public void onButtonClick(String str) {

                    TextView tv = new TextView(MainActivity.this);
                    //定义一个标识
                     UUID uuid = UUID.randomUUID();
                     //给textview设置标识
                     tv.setTag(uuid);
                     //转换成字符串
                     String s = String.valueOf(tv.getTag());
                     //添加到数据库
                    UserDao.getInsanner(MainActivity.this).add(str,s);
                    //设置TextView文本
                     tv.setText(str);
                     //设置textview的背景，自定义shape
                    tv.setBackgroundResource(R.drawable.edit_bg);
                    //将textview布局添加到自定义view  历史搜索的WeekFlowLayout中
                    fl_search.addView(tv);
                    //调用点击事件的方法
                    init(tv,s);
                }

        });
        //设置默认热门搜索中的值
        String[] sl = {"关注", "腾讯视频", "乔家大院", "小说", "新闻", "腾讯", "大前门", "头条"};
        for (int i = 0; i < sl.length; i++) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText(sl[i]);
            tv.setBackgroundResource(R.drawable.edit_bg);
            fl_hot.addView(tv);
        }
    }
    //存错点击和长按事件方法的方法
    private void init(final TextView textView,final String uuid) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //调用数据库删除的方法，根据唯一标识uuid删除数据库中的数据
                UserDao.getInsanner(MainActivity.this).delete(uuid);
                //删除视图
                fl_search.removeView(v);
                return true;
            }
        });
    }

}

