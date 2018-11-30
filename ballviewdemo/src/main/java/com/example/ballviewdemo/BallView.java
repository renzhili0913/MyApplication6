package com.example.ballviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class BallView extends View {
    private Paint mPaint;
    private Context context;
    /*
    * 圆的初始位置及半径
    * */
    private int x=100;
    private int y=100;
    private int radius=80;

    public BallView(Context context) {
        this(context,null);
        this.context=context;
    }

    public BallView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
        this.context=context;
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       // setMeasuredDimension(160,160);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#B1F4CD"));
        canvas.drawCircle(x,y,radius,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x= (int) event.getX();
                y= (int) event.getY();
            case MotionEvent.ACTION_MOVE:
                x= (int) event.getX();
                y= (int) event.getY();
            case MotionEvent.ACTION_UP:
                x= (int) event.getX();
                y= (int) event.getY();
                break;
        }
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int widht = manager.getDefaultDisplay().getWidth();
        int height = manager.getDefaultDisplay().getHeight();
        if (x>=50&y>=50&x<widht-50&y<height-50){
            postInvalidate();
        }
        return true;
    }
}
