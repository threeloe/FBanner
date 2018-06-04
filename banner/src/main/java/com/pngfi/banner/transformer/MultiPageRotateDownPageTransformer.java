package com.pngfi.banner.transformer;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by pngfi on 2018/5/21.
 */

public class MultiPageRotateDownPageTransformer implements ViewPager.PageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    @Override
    public void transformPage(View view, float position) {
        if (position < -1) {
            // This page is way off-screen to the left.
            view.setRotation(mMaxRotate * -1);
            view.setPivotX(view.getWidth());
            view.setPivotY(view.getHeight());

        } else if (position <= 1) {

            if (position < 0) {
                view.setPivotX(view.getWidth() * (0.5f + 0.5f * (-position)));
                view.setPivotY(view.getHeight());
                view.setRotation(mMaxRotate * position);
            } else {
                view.setPivotX(view.getWidth() * 0.5f * (1 - position));
                view.setPivotY(view.getHeight());
                view.setRotation(mMaxRotate * position);
            }
        } else {
            // This page is way off-screen to the right.
            view.setRotation(mMaxRotate);
            view.setPivotX(view.getWidth() * 0);
            view.setPivotY(view.getHeight());
        }
    }
}
