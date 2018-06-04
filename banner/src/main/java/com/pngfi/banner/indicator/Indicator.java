package com.pngfi.banner.indicator;

import java.util.List;

/**
 * Created by pngfi on 2018/3/22.
 */

public interface Indicator {

    /**
     * set the count of page
     */
    void setCount(int count);

    /**
     * set the position of the page that is current selected
     */
    void setSelected(int position);

}
