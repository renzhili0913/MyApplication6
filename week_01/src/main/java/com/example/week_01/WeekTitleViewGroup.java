package com.example.week_01;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.LinearLayout;

public class WeekTitleViewGroup extends LinearLayout {
    Context context;
    public WeekTitleViewGroup(Context context) {
        super(context);
        this.context=context;
    }

    public WeekTitleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init() {
       // View view = View.inflate(context,R.layout.week_title_item,null);
    }

}
