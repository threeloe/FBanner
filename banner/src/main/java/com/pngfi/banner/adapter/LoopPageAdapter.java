package com.pngfi.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pngfi on 2018/3/20.
 */
public class LoopPageAdapter<R> extends PagerAdapter {

    protected List<R> mData = new ArrayList<>();
    protected ViewHolder<R> viewHolder;
    private boolean once;


    public int getCount() {
        return mData.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewHolder.getView(container.getContext(), toRealPosition(position), mData.get(position));
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

    public void setData(List<R> data) {
        mData.clear();
        mData.addAll(data);
        once = data.size() == 1;
        if (!once) {
            R first = data.get(0);
            R last = data.get(data.size() - 1);
            mData.add(first);
            mData.add(0, last);
        }
        notifyDataSetChanged();
    }


    public int toRealPosition(int position) {
        if (once)
            return 0;
        if (position == getCount() - 1) {
            return 0;
        } else if (position == 0) {
            return getCount() - 3;
        } else {
            return position - 1;
        }
    }


    public int toPosition(int realPosition) {
        if (once)
            return 0;
        return realPosition + 1;
    }

    @Override
    public int getItemPosition(Object object) {
        return  POSITION_NONE;
    }
}
