package com.pngfi.banner.indicator;

import java.util.List;

/**
 * Created by pngfi on 2018/3/22.
 */

public interface Indicator {

    /**
     * 设置页面的数量
     * @param count
     */
    void setCount(int count);

    /**
     * 设置选中了position页面
     * @param position
     */
    void setSelected(int position);

}
