package com.pngfi.banner.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pngfi.banner.R;

import java.util.List;

/**
 * Created by pngfi on 2018/3/21.
 */

public class DotIndicator extends LinearLayout implements Indicator {


    private int dotRes;
    private int dotMargin;

    private int selectedPosition = 0;


    public DotIndicator(Context context) {
        this(context, null);
    }

    public DotIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DotIndicator);
        dotRes = ta.getResourceId(R.styleable.DotIndicator_dot, R.drawable.bg_dot_view);
        dotMargin = (int) ta.getDimension(R.styleable.DotIndicator_dotMargin, dp2px(12));
        ta.recycle();
    }


    private int dp2px(float f) {
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, getResources().getDisplayMetrics());
        return i;
    }



    @Override
    public <T> void setData(List<T> data) {
        if (data==null||data.size()<=0)
            return;
        if (data.size()<= 1)
            setVisibility(View.INVISIBLE);
        else
            setVisibility(View.VISIBLE);
        removeAllViews();
        selectedPosition = 0;
        for (int i = 0; i < data.size(); i++) {
            final ImageView imageView = new ImageView(getContext());
            LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.rightMargin= (i == data.size()- 1 ? 0 : dotMargin);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(dotRes);
            addView(imageView);
            if (i == selectedPosition) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setSelected(true);
                    }
                });
            }
        }
    }

    @Override
    public void setSelected(int position) {
        int last = selectedPosition;
        selectedPosition = position;
        getChildAt(position).setSelected(true);
        getChildAt(last).setSelected(false);
    }
}
