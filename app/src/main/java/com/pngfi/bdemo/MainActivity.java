package com.pngfi.bdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.pngfi.banner.DotView;
import com.pngfi.banner.LoopViewPager;
import com.pngfi.banner.adapter.ViewHolder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private LoopViewPager banner;
    private DotView dotView;
    private String[] images = {"https://pic.nanguazufang.cn/g3/05/d3/42f0-df28-4617-a216-71f15e1aaf7869",
            "https://pic.nanguazufang.cn/g3/10/fa/5b8c-107f-484b-a013-5613f463915231",
            "https://pic.nanguazufang.cn/g3/a3/9c/0832-2281-4925-a6f3-72ed9b3fe1a960",
            "https://pic.nanguazufang.cn/g3/6e/1d/474b-91d3-4a7f-9c5f-b723f7194a7d08",
            "https://pic.nanguazufang.cn/g3/85/8f/9278-9261-4794-88de-13f9c275dfcb86"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.banner);
        dotView = findViewById(R.id.dotView);
        banner.setViewHolder(new BannerViewHolder());
        banner.setPageMargin(dp2px(8));
        banner.bindIndicator(dotView);
        banner.setData(Arrays.asList(images));
    }


    public class BannerViewHolder implements ViewHolder<String> {
        @Override
        public View getView(Context context, final int position, String data) {
            final View inflate = LayoutInflater.from(context).inflate(R.layout.item_banner_apartment_activity, null);
            ImageView imageView = inflate.findViewById(R.id.image);
            GlideApp.with(imageView).load(data).transforms(new CenterCrop(), new RoundedCorners(dp2px(10))).disallowHardwareConfig().into(imageView);
            return inflate;
        }
    }

    public int dp2px(float f) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, getResources().getDisplayMetrics());
    }


    public void plus1(View view){
       banner.setCurrentItem(banner.getCurrentItem()+1);
    }

    public void plus5(View view){
        banner.setCurrentItem(banner.getCurrentItem()+4);

    }

    public void seekTo(View view){
        long begin=System.currentTimeMillis();
        banner.setCurrentItem(banner.getCurrentItem()+1000000000,false);
        Log.i("Main",System.currentTimeMillis()-begin+"----");

    }
}
