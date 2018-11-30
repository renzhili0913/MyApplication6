package com.example.ballviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ButtonView extends LinearLayout {
    Context context;
    public ButtonView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init() {
        View view = View.inflate(context,R.layout.button_item,null);
        this.addView(view);
    }


}
