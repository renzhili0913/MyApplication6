package com.example.week_01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class WeekGroupNameLayout extends TextView {
    public WeekGroupNameLayout(Context context) {
        super(context);
    }

    public WeekGroupNameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //自定义属性
        //第一步，在value文件夹下建立一个attr.xml文件，
        //第二步，写<declear....标签
        //第三步，写<attr 标签 name：方法名 format：属性
        //第四步，在继承TextView的自定义view中有AttributeSet的构造方法里写以下代码
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.WeekFlowLayout);
        int color= typedArray.getColor(R.styleable.WeekFlowLayout_textColor,Color.RED);
        setTextColor(color);
        //第五步，在布局文件根控件中写xmlns:app="http://schemas.android.com/apk/res-auto"
        //第六步，在想要调用自定义属性的控件中添加app:方法名=“想要设置的值”
        //回收
        typedArray.recycle();
    }
}
