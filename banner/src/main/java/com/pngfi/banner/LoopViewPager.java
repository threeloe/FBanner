package com.pngfi.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.pngfi.banner.adapter.LoopPageAdapter;
import com.pngfi.banner.adapter.ViewHolder;

import java.util.List;

/**
 * Created by pngfi on 2018/3/20.
 */
public class LoopViewPager extends ViewPager {


    OnPageChangeListener mOnLoopPageChangeListener;
    private LoopPageAdapter mAdapter;

    private boolean canScroll = true; //手指能滑动
    private static final int MSG_SLIDING = 0X11;

    private int turningDuration = 2000;


    private boolean autoTurning = true;

    //只有一张图片
    private boolean once = false;


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
            if (msg.what == MSG_SLIDING) {
                if (once || !autoTurning) {
                    stopTurning();
                    return;
                }
                //没有数据
                if (mAdapter.getCount() == 0) {
                    return;
                }
                int item = LoopViewPager.super.getCurrentItem();
                setCurrentItem(item + 1, true);
                mHandler.sendEmptyMessageDelayed(MSG_SLIDING, turningDuration);
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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void startTuring() {
        if (mHandler.hasMessages(MSG_SLIDING))
            mHandler.removeMessages(MSG_SLIDING);
        mHandler.sendEmptyMessageDelayed(MSG_SLIDING, turningDuration);
    }

    private void stopTurning() {
        if (mHandler.hasMessages(MSG_SLIDING)) {
            mHandler.removeMessages(MSG_SLIDING);
        }
    }

    private void init() {
        setOffscreenPageLimit(2);
        addOnPageChangeListener(onPageChangeListener);
    }

    public <T> void setViewHolder(ViewHolder<T> holder) {
        mAdapter = new LoopPageAdapter(holder);
        super.setAdapter(mAdapter);
    }

    public <T> void setData(List<T> data) {
        if (data == null || data.size() == 0)
            return;
        mAdapter.setData(data);
        if (mIndicator != null) {
            mIndicator.setCount(data.size());
        }
        once = data.size() == 1;
        setCanScroll(!once);
        /**
         * 起始的参数设置很重要，不能设置太大。太大会导致两个问题：
         * 1.ANR 由于ViewPager的测量在主线程中 ,例如设置为Integer.MAX_VALUE/2
         * 2.不会ANR，但是布局测量出现问题，导致page与page之间重叠紊乱，例如100000*data.size()
         */
        int times= Integer.MAX_VALUE / data.size();
        setCurrentItem(100*data.size(),false);
    }


    public void setCanScroll(boolean isCanScroll) {
        this.canScroll = isCanScroll;
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
        if (canScroll) {
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    public LoopPageAdapter getAdapter() {
        return mAdapter;
    }

    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }


    public void setOnLoopPageChangeListener(OnPageChangeListener listener) {
        mOnLoopPageChangeListener = listener;
    }




    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOnLoopPageChangeListener != null) {
                    mOnLoopPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

            if (mOnLoopPageChangeListener != null) {
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mOnLoopPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > 0.5) {
                        mOnLoopPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOnLoopPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOnLoopPageChangeListener != null) {
                mOnLoopPageChangeListener.onPageScrollStateChanged(state);
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


    private Indicator mIndicator;


    public void bindIndicator(Indicator indicator) {
        mIndicator = indicator;
        setOnLoopPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.setSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
