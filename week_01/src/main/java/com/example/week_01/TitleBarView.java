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
        View view = View.inflate(context,R.layout.title,null);
        final EditText editText = view.findViewById(R.id.edit_title);
        view.findViewById(R.id.search_title).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener!=null){
                    String trim = editText.getText().toString().trim();
                    if (trim.equals("")){
                        return;
                    }else{
                        mOnButtonClickListener.onButtonClick(trim);
                    }

                }
            }
        });
        addView(view);
    }

    OnBuutonClickListener mOnButtonClickListener;
    public void setButtonClickListener(OnBuutonClickListener onBuutonClickListener){
        mOnButtonClickListener=onBuutonClickListener;
    }
    public interface OnBuutonClickListener{
        void onButtonClick(String str);
    }

}
