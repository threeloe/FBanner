[![API](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=11)
[![License](http://img.shields.io/badge/License-Apache%202.0-brightgreen.svg?style=flat)](https://opensource.org/licenses/Apache-2.0)

**一个灵活易用的Banner组件**
****

## 特点
- 灵活，轮播组件与指示器组件分离，可以任何组合
- 内置点指示器、数字指示器、标题指示器，可以自由定制，也可以实现自己的指示器


## 截图
|---|
|![效果示例](http://oceh51kku.bkt.clouddn.com/banner_example1.png)|
|![效果示例](http://oceh51kku.bkt.clouddn.com/banner_example1.png)|
|![效果示例](http://oceh51kku.bkt.clouddn.com/banner_example1.png)|
|![效果示例](http://oceh51kku.bkt.clouddn.com/banner_example1.png)|
|![效果示例](http://oceh51kku.bkt.clouddn.com/banner_example1.png)|

## 下载
The **LATEST_VERSION**: [![Download](https://api.bintray.com/packages/pngfi/maven/rangeseekbar/images/download.svg)](https://bintray.com/pngfi/maven/rangeseekbar/_latestVersion)
```groovy
  dependencies {
     compile 'com.pngfi:fbanner:${LATEST_VERSION}'
  }
```
## 用法
```

<LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clipChildren="false"
            android:orientation="vertical">

            <com.pngfi.banner.BannerViewPager
                android:id="@+id/banner"
                android:clipChildren="false"
                android:layout_width="match_parent"
                app:autoTurning="false"
                android:layout_height="175dp"
                android:layout_marginLeft="40dp"
                android:layout_marginRight="40dp"
                android:layout_marginTop="15dp" />

            <com.pngfi.banner.indicator.DotIndicator
                android:id="@+id/dotView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:layout_marginBottom="21dp"
                android:layout_marginTop="15dp"
                app:dot="@drawable/default_bg_dot_view"
                app:dotMargin="16dp" />

        </LinearLayout>

```

listening the progress changes
```
 seekBar.setOnSeekBarChangeListener(new RangeSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(RangeSeekBar seekBar, float lesserProgress, float largerProgress, boolean fromUser) {
                tv.setText((int) lesserProgress+" to "+(int)largerProgress);
            }
        });
```

change the progress
```
 seekbar.setProgress(lesser,larger);
```
get the current progress
```
 float[] progress=seekbar.getProgress();
 float lesserProgress=progress[0];
 float largerProgress=progress[1];

```
## Attr
 attr | format | desc
  -------- | ---|---
  rb_max|float|the max value of progress,default 100
  rb_min|float|the min value of progress,default 0
  rb_progressBackground|color|the background color of progress bar
  rb_progressBackgroundHeight|dimension|background height
  rb_progressColor|color|the progress color
  rb_progressHeight|dimension|the height of progress bar
  rb_thumb|drawable|the drawable resource of SeekBar button, support multiple drawable. note:the StateListDrawable only support pressed state.
  rb_stepCount|positive integer|the progress is divided into many `step`s, this is the count of steps, had better assign it.
  rb_gap|positive integer|the minimal step count between two thumbs,default 0.
  rb_seekToTouch|boolean|the thumb will seek to the location where user click if true,default true.
  rb_shadowColor|color|shadow color around thumb
  rb_shadowRadius|dimension|shadow radius
  rb_shadowOffsetX|dimension|horizontal offset of shadow
  rb_shadowOffsetY|dimension|vertical offset  of shadow
