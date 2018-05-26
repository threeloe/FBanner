package com.pngfi.banner.indicator;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pngfi.banner.R;

import java.util.List;

/**
 * Created by pngfi on 2018/5/13.
 */

public class TitleIndicator extends TextView implements Indicator{

    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#EAEAEA");
    private static final int DEFAULT_BACKGROUND_COLOR=Color.parseColor("#44B0B4A8");
    private List<CharSequence> mTitles;


    public TitleIndicator(Context context) {
        this(context, null);
    }

    public TitleIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int count = attrs.getAttributeCount();
        boolean hasTextColor = false, hasBackground = false;
        for (int i = 0; i < count; i++) {
            String attributeName = attrs.getAttributeName(i);
            if (attributeName.equals("textColor")) {
                hasTextColor = true;
            }
            if (attributeName.equals("background")) {
                hasBackground = true;
            }
        }
        if (!hasTextColor) {
            setTextColor(DEFAULT_TEXT_COLOR);
        }
        if (!hasBackground) {
            setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        }
    }


    public void setTitles(List<CharSequence> titles){
        mTitles=titles;
        if (mTitles!=null&&mTitles.size()!=0){
            setText(mTitles.get(0));
        }
    }

    @Override
    public void setCount(int count) {

    }

    @Override
    public void setSelected(int position) {
        if (mTitles!=null){
            setText(mTitles.get(position));
        }
    }
}
