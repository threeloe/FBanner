package com.pngfi.banner.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by pngfi on 2018/5/5.
 *
 * NumberIndicator是一个TextView,textSize和textColor这只字体的颜色和大小
 * background设置背景
 */

public class NumberIndicator extends AppCompatTextView implements Indicator {


    public NumberIndicator(Context context) {
        this(context,null);
    }

    public NumberIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public NumberIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public  void setCount(int count) {
        if (count<=0)
            return;

    }

    @Override
    public void setSelected(int position) {

    }
}
