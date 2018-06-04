package com.pngfi.banner.indicator;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


import com.pngfi.banner.R;

/**
 * Created by pngfi on 2018/5/5.
 *
 * NumberIndicator is a subclass of TextView, you can use textSize ,textColor and background
 * just like a TextView.
 */

public class NumberIndicator extends TextView implements Indicator {

    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#EAEAEA");
    private int mCount;

    public NumberIndicator(Context context) {
        this(context,null);
    }

    public NumberIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public NumberIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int count=attrs.getAttributeCount();
        boolean hasTextColor=false,hasBackground=false;
        for (int i=0;i<count;i++){
            String attributeName = attrs.getAttributeName(i);
            if (attributeName.equals("textColor")){
                hasTextColor=true;
            }
            if (attributeName.equals("background")){
                hasBackground=true;
            }
        }
        if (!hasTextColor){
            setTextColor(DEFAULT_TEXT_COLOR);
        }
        if (!hasBackground){
            setBackgroundResource(R.drawable.default_bg_number_indicator);
        }
       setGravity(Gravity.CENTER);
    }


    @Override
    public  void setCount(int count) {
        if (count<=1){
            setVisibility(INVISIBLE);
        }else {
            setVisibility(VISIBLE);
        }
        mCount=count;
        setText("1/"+count);
    }

    @Override
    public void setSelected(int position) {
        String text=(position+1)+"/"+mCount;
        setText(text);
    }
}
