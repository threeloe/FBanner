[![API](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=11)
[![License](http://img.shields.io/badge/License-Apache%202.0-brightgreen.svg?style=flat)](https://opensource.org/licenses/Apache-2.0)

**一个灵活易用的Banner组件,具体实现原理请看[打造一个灵活易用的Banner组件](https://www.jianshu.com/p/4708e7da2013)**
****

## 特点
- 灵活，轮播组件与指示器组件分离，可以任何组合
- 内置点指示器、数字指示器、标题指示器，可以自由定制，也可以实现自己的指示器


## 截图
![效果示例](https://github.com/pngfi/FBanner/blob/master/art/1.png)
![效果示例](https://github.com/pngfi/FBanner/blob/master/art/2.png)
![效果示例](https://github.com/pngfi/FBanner/blob/master/art/3.png)
![效果示例](https://github.com/pngfi/FBanner/blob/master/art/4.png)
![效果示例](https://github.com/pngfi/FBanner/blob/master/art/5.png)

[Demo APK](https://github.com/pngfi/FBanner/blob/master/art/demo.apk) 

## 下载
The **LATEST_VERSION**: [![Download](https://api.bintray.com/packages/pngfi/maven/fbanner/images/download.svg)](https://bintray.com/pngfi/maven/fbanner/_latestVersion)
```groovy
  dependencies {
     compile 'com.pngfi:fbanner:${LATEST_VERSION}'
  }
```
## 基本使用
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

设置ViewHolder，在getView方法中定制UI
```
 public class BannerViewHolder implements ViewHolder<String> {
         private boolean isRoundedCorner = true;

         public BannerViewHolder() {

         }

         public BannerViewHolder(boolean roundedCorner) {
             isRoundedCorner = roundedCorner;
         }

         @Override
         public View getView(Context context, final int position, String data) {
             final View inflate = LayoutInflater.from(context).inflate(R.layout.item_banner_apartment_activity, null);
             ImageView imageView = inflate.findViewById(R.id.image);
             if (isRoundedCorner) {
                 GlideApp.with(imageView).load(data).transforms(new CenterCrop(), new RoundedCorners(dp2px(10))).disallowHardwareConfig().into(imageView);
             } else {
                 GlideApp.with(imageView).load(data).transforms(new CenterCrop()).disallowHardwareConfig().into(imageView);
             }

             inflate.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(MainActivity.this, "click" + position, Toast.LENGTH_SHORT).show();
                 }
             });
             return inflate;
         }
     }


    firstBanner.setViewHolder(new BannerViewHolder());

```
添加指示器：

```
   firstBanner.addIndicator(dotView);
```
设置数据：
```
    firstBanner.setData(Arrays.asList(images));
```
上面四步就完成FBanner的基本使用,其中BannerViewPager就是一个增强版的ViewPager。DotIndicator是点状指示器。

## BannerViewPager
 attr | format | desc
  -------- | ---|---
  autoTurning|boolean|是否自动翻页，默认true
  turningDuration|integer|自动翻页的间隔，单位毫秒，默认2000
  manualTurning|boolean|是否手动翻页，默认true
  smoothScrollDuration|integer|滑动动画时间

 可以正常使用ViewPager的几乎所有方法，目前对于PageTransformer的支持存在以定问题，
 如果必须要使用PageTransformer的话要保证页面总数量不要太多。



## 指示器
点状指示器：
```
<com.pngfi.banner.indicator.DotIndicator
                android:id="@+id/dotView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:layout_marginBottom="21dp"
                android:layout_marginTop="15dp"
                app:dot="@drawable/default_bg_dot_view"
                app:dotMargin="16dp" />

```
数字指示器：
```
    <com.pngfi.banner.indicator.NumberIndicator
                    android:id="@+id/numberIndicator"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_alignParentBottom="true"
                    android:layout_alignParentRight="true"
                    android:layout_marginBottom="20dp"
                    android:layout_marginRight="20dp"
                    android:background="@drawable/bg_number_indicator"
                    android:textColor="@color/colorAccent" />
```
标题指示器：
```
    <com.pngfi.banner.indicator.TitleIndicator
                    android:id="@+id/titleIndicator"
                    android:layout_width="match_parent"
                    android:layout_height="30dp"
                    android:layout_alignParentBottom="true"
                    android:gravity="center_vertical"
                    android:paddingLeft="20dp" />
```
标题指示器需要另外设置标题
```
   titleIndicator.setTitles(Arrays.asList(titles));
```
