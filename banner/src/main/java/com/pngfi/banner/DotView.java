package com.pngfi.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by pngfi on 2018/3/21.
 */

public class DotView extends LinearLayout implements Indicator {


    private int dotRes;
    private int dotMargin;

    private int selectedPosition = 0;


    public DotView(Context context) {
        this(context, null);
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        setOrientation(HORIZONTAL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DotView);
        dotRes = ta.getResourceId(R.styleable.DotView_dot, R.drawable.bg_dot_view);
        dotMargin = (int) ta.getDimension(R.styleable.DotView_dotMargin, dp2px(12));
        ta.recycle();
    }


    private int dp2px(float f) {
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, getResources().getDisplayMetrics());
        return i;
    }


    @Override
    public void setCount(int count) {
        if (count <= 0)
            return;
        if (count <= 1)
            setVisibility(View.INVISIBLE);
        else
            setVisibility(View.VISIBLE);
        removeAllViews();
        selectedPosition=0;
        for (int i = 0; i < count; i++) {
            final ImageView imageView = new ImageView(getContext());
            MarginLayoutParams lp = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = i == count - 1 ? 0 : dotMargin;
            imageView.setLayoutParams(lp);
            imageView.setImageResource(dotRes);
            addView(imageView);
            if (i == selectedPosition){
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
