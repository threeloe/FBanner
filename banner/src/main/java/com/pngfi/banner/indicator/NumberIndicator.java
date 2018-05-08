package com.pngfi.banner.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.pngfi.banner.R;

import java.util.List;

/**
 * Created by pngfi on 2018/5/5.
 *
 * NumberIndicator是一个TextView,textSize和textColor这只字体的颜色和大小
 * background设置背景
 */

public class NumberIndicator extends AppCompatTextView implements Indicator {

    private static final int DEFAULE_TEXT_COLOR=Color.parseColor("#EAEAEA");
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
            setTextColor(DEFAULE_TEXT_COLOR);
        }
        if (!hasBackground){
            setBackgroundResource(R.drawable.default_bg_number_indicator);
        }
       setGravity(Gravity.CENTER);
    }


    @Override
    public  void setCount(int count) {
        if (count<=0)
            return;
        mCount=count;
    }

    @Override
    public void setSelected(int position) {
        String text=(position+1)+"/"+mCount;
        setText(text);
    }
}
