package com.pngfi.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;


import com.pngfi.banner.adapter.BannerPageAdapter;
import com.pngfi.banner.adapter.ViewHolder;
import com.pngfi.banner.indicator.Indicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pngfi on 2018/3/20.
 */
public class BannerViewPager extends ViewPager {

    private static final String TAG = "LoopViewPager";

    private BannerPageAdapter mAdapter;


    private ArrayList<OnPageChangeListener> mOnPageChangeListeners = new ArrayList<>();
    private static final int MSG_AUTO_TURNING = 0X520;


    private boolean manualTurning = true;

    private boolean autoTurning = true;
    private int turningDuration = 2000;
    private int smoothScrollDuration = 900;

    private List<Indicator> mIndicators = new ArrayList<>();
    private InternalScroller mScroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
        autoTurning = ta.getBoolean(R.styleable.BannerViewPager_autoTurning, true);
        manualTurning = ta.getBoolean(R.styleable.BannerViewPager_manualTurning, true);
        turningDuration = ta.getInt(R.styleable.BannerViewPager_turningDuration, 2000);
        smoothScrollDuration = ta.getInt(R.styleable.BannerViewPager_smoothScrollDuration, 900);

        super.addOnPageChangeListener(proxyRootListener);
        try {
            Field scrollField = ViewPager.class.getDeclaredField("mScroller");
            scrollField.setAccessible(true);
            mScroller = new InternalScroller(getContext());
            mScroller.setDuration(smoothScrollDuration);
            scrollField.set(this, mScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_AUTO_TURNING) {
                //no data or only one
                if (mAdapter == null || mAdapter.getCount() == 0 || mAdapter.getCount() == 1 || !autoTurning) {
                    stopTurning();
                    return;
                }
                int item = BannerViewPager.super.getCurrentItem();
                BannerViewPager.super.setCurrentItem(item + 1, true);
                mHandler.sendEmptyMessageDelayed(MSG_AUTO_TURNING, turningDuration);
            }
        }
    };


    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(mAdapter.toPosition(item));
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        BannerViewPager.super.setCurrentItem(mAdapter.toPosition(item), smoothScroll);
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


    public <T> void setViewHolder(ViewHolder<T> holder) {
        mAdapter = new BannerPageAdapter(holder);
        super.setAdapter(mAdapter);
    }

    public <T> void setData(List<T> data) {
        if (mAdapter == null) {
            throw new IllegalStateException("setViewHolder must be called first");
        }
        if (data == null || data.size() == 0)
            return;
        for (Indicator indicator : mIndicators) {
            indicator.setCount(data.size());
        }

        mAdapter.setData(data);
        setCurrentItem(0,false);
        startTuring();
    }

    public void setAutoTurning(boolean autoTurning) {
        this.autoTurning = autoTurning;
        if (autoTurning) {
            startTuring();
        } else {
            stopTurning();
        }
    }


    public void setManualTurning(boolean manualTurning) {
        this.manualTurning = manualTurning;
    }


    public void setTurningDuration(int turningDuration){
       this.turningDuration=turningDuration;
    }

    public void setSmoothScrollDuration(int smoothScrollDuration){
        mScroller.setDuration(smoothScrollDuration);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
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

    public BannerPageAdapter getAdapter() {
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


    /**
     * dispatch all PageChange events
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
                if (BannerViewPager.super.getCurrentItem() == mAdapter.getCount() - 1) {
                    BannerViewPager.super.setCurrentItem(1, false);
                } else if (BannerViewPager.super.getCurrentItem() == 0) {
                    BannerViewPager.super.setCurrentItem(mAdapter.getCount() - 2, false);
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


    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        setPageTransformer(reverseDrawingOrder, transformer, View.LAYER_TYPE_HARDWARE);
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer, int pageLayerType) {
        super.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
        if (transformer == null) {
            setOffscreenPageLimit(1);
        } else {
            setOffscreenPageLimit(100);
        }
    }


    /**
     * called before setData
     *
     * @param indicator
     */
    public void addIndicator(Indicator indicator) {
        mIndicators.add(indicator);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (Indicator in : mIndicators) {
                    in.setSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void removeIndicator(Indicator indicator) {
        mIndicators.remove(indicator);
    }


    private class InternalScroller extends Scroller {
        private int scrollDuration = 1000;

        public InternalScroller(Context context) {
            super(context);
        }

        public InternalScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public InternalScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, scrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, scrollDuration);
        }

        public void setDuration(int duration) {
            scrollDuration = duration;
        }

    }
}
