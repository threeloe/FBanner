package com.pngfi.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


import com.pngfi.banner.adapter.LoopPageAdapter;
import com.pngfi.banner.adapter.ViewHolder;
import com.pngfi.banner.indicator.Indicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pngfi on 2018/3/20.
 */
public class LoopViewPager extends ViewPager {

    private static final String TAG = "LoopViewPager";

    private LoopPageAdapter mAdapter;

    private static final int MSG_AUTO_TURNING = 0X208;

    //是否能手动翻页
    private boolean manualTurning = true;
    //是否自动翻页
    private boolean autoTurning = true;
    private int turningDuration = 2000;


    //只有一张图片
    private boolean once = false;
    private Indicator mIndicator;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_AUTO_TURNING) {
                if (once || !autoTurning) {
                    stopTurning();
                    return;
                }
                //没有数据
                if (mAdapter == null || mAdapter.getCount() == 0) {
                    return;
                }
                int item = LoopViewPager.super.getCurrentItem();
                LoopViewPager.super.setCurrentItem(item + 1, true);
                mHandler.sendEmptyMessageDelayed(MSG_AUTO_TURNING, turningDuration);
            }
        }
    };


    public void setAutoTurning(boolean autoTurning) {
        this.autoTurning = autoTurning;
        if (autoTurning) {
            startTuring();
        } else {
            stopTurning();
        }
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(mAdapter.toPosition(item));
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(mAdapter.toPosition(item), smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        return mAdapter.toRealPosition(super.getCurrentItem());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        super.removeOnPageChangeListener(proxyRootListener);
    }

    private void startTuring() {
        if (mHandler.hasMessages(MSG_AUTO_TURNING))
            mHandler.removeMessages(MSG_AUTO_TURNING);
        mHandler.sendEmptyMessageDelayed(MSG_AUTO_TURNING, turningDuration);
    }

    private void stopTurning() {
        if (mHandler.hasMessages(MSG_AUTO_TURNING)) {
            mHandler.removeMessages(MSG_AUTO_TURNING);
        }
    }

    private void init() {
        setOffscreenPageLimit(2);
        super.addOnPageChangeListener(proxyRootListener);
    }

    public <T> void setViewHolder(ViewHolder<T> holder) {
        mAdapter = new LoopPageAdapter(holder);
        super.setAdapter(mAdapter);
    }

    public <T> void setData(List<T> data) {
        if (mAdapter == null) {
            throw new IllegalStateException("setViewHolder must be called first");
        }
        if (data == null || data.size() == 0)
            return;
        if (mIndicator != null) {
            mIndicator.
                    setCount(data.size());
        }
        once = data.size() == 1;
        mAdapter.setData(data);
        setCurrentItem(0);
        startTuring();
    }


    public void setManualTurning(boolean isCanScroll) {
        this.manualTurning = isCanScroll;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        //window 没有焦点之后停止滑动，回来之后再开始滑动
        if (hasWindowFocus) {
            startTuring();
        } else {
            stopTurning();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (manualTurning) {
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (manualTurning)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    public LoopPageAdapter getAdapter() {
        return mAdapter;
    }


    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListeners.add(listener);
    }


    @Override
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListeners.remove(listener);
    }

    @Override
    public void clearOnPageChangeListeners() {
        mOnPageChangeListeners.clear();
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListeners.add(onPageChangeListener);
    }

    private ArrayList<OnPageChangeListener> mOnPageChangeListeners = new ArrayList<>();


    /**
     *这个Listener用于分发所有事件
     */
    private OnPageChangeListener proxyRootListener = new OnPageChangeListener() {
        private int mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                for (OnPageChangeListener listener : mOnPageChangeListeners) {
                    listener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = mAdapter.toRealPosition(position);
            for (OnPageChangeListener listener : mOnPageChangeListeners) {
                listener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING) {
                if (LoopViewPager.super.getCurrentItem() == mAdapter.getCount() - 1) {
                    LoopViewPager.super.setCurrentItem(1, false);
                } else if (LoopViewPager.super.getCurrentItem() == 0) {
                    LoopViewPager.super.setCurrentItem(mAdapter.getCount() - 2, false);
                }
            }
            for (OnPageChangeListener listener : mOnPageChangeListeners) {
                listener.onPageScrollStateChanged(state);
            }
        }
    };


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            if (autoTurning)
                startTuring();
        } else if (action == MotionEvent.ACTION_DOWN) {
            if (autoTurning)
                stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }


    public void bindIndicator(Indicator indicator) {
        mIndicator = indicator;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("LOOPViewPager", position + "---");
                mIndicator.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
