package com.example.week_01;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.UserDao;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<String> select;
    private WeekFlowLayout fl_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        fl_search = findViewById(R.id.fl_search);
        WeekFlowLayout fl_hot = findViewById(R.id.fl_hot);
        TitleBarView title = findViewById(R.id.title);
        select = UserDao.getInsanner(MainActivity.this).select();
        if (select.size() > 0) {
            for (int i = 0; i < select.size(); i++
                    ) {
                TextView tv = new TextView(MainActivity.this);
                tv.setText(select.get(i));
                tv.setBackgroundResource(R.drawable.edit_bg);
                fl_search.addView(tv);
                init(tv);
            }
        }

        title.setButtonClickListener(new TitleBarView.OnBuutonClickListener() {
            @Override
            public void onButtonClick(String str) {
                    UserDao.getInsanner(MainActivity.this).add(str);
                    TextView tv = new TextView(MainActivity.this);
                    tv.setText(str);
                    tv.setBackgroundResource(R.drawable.edit_bg);
                    fl_search.addView(tv);
                    init(tv);
                }

        });
        String[] sl = {"关注", "腾讯视频", "乔家大院", "小说", "新闻", "腾讯", "大前门", "头条"};
        for (int i = 0; i < sl.length; i++) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText(sl[i]);
            tv.setBackgroundResource(R.drawable.edit_bg);
            fl_hot.addView(tv);
        }
    }

    private void init(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                UserDao.getInsanner(MainActivity.this).delete(textView.getText().toString());

                return false;
            }
        });
    }

}

