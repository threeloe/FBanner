package com.pngfi.banner.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by pngfi on 2018/5/5.
 */

public class NumberIndicator extends AppCompatTextView implements Indicator {


    public NumberIndicator(Context context) {
        super(context);
    }

    public NumberIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public <T> void setData(List<T> data) {
        if (data == null || data.size() == 0)
            return;
        
    }

    @Override
    public void setSelected(int position) {

    }
}
