package com.pngfi.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pngfi on 2018/3/20.
 */
public class LoopPageAdapter<T> extends PagerAdapter {

    protected List<T> mData;
    protected ViewHolder<T> viewHolder;

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    @Override
    public int getCount() {
        return getRealCount() == 0 ? 0 : Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        View view = viewHolder.getView(container.getContext(), realPosition, mData.get(realPosition));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public LoopPageAdapter(ViewHolder holder) {
        viewHolder = holder;
        mData = new ArrayList<>();
    }

   public void setData(List<T> datas) {
        mData = datas;
        notifyDataSetChanged();
    }
}
