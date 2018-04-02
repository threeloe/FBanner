package com.pngfi.banner.adapter;

/**
 * Created by pngfi on 2018/3/20.
 */

import android.content.Context;
import android.view.View;

public interface ViewHolder<T>{
    View getView(Context context, int position, T data);
}