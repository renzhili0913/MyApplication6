package com.example.week_01;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TitleBarView extends LinearLayout {
    Context context;
    public TitleBarView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init() {
        //获取布局
        View view = View.inflate(context,R.layout.title,null);
        //通过布局获取EditText资源id
        final EditText editText = view.findViewById(R.id.edit_title);
        //通过获取EditText资源id
        view.findViewById(R.id.search_title).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //第五步判断声明的接口不为空
                if (mOnButtonClickListener!=null){

                    String trim = editText.getText().toString().trim();
                    if (trim.equals("")){
                        return;
                    }else{
                        //第六步
                        mOnButtonClickListener.onButtonClick(trim);
                    }

                }
            }
        });
        addView(view);
    }
    //第三步声明接口
    OnBuutonClickListener mOnButtonClickListener;
    //第四步，定义方法参数为接口，并赋值与声明的变量上
    public void setButtonClickListener(OnBuutonClickListener onBuutonClickListener){
        mOnButtonClickListener=onBuutonClickListener;
    }
    //第一步定义接口
    public interface OnBuutonClickListener{
        //第二步创建方法，以及需要的参数
        void onButtonClick(String str);
    }

}
