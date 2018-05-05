package com.pngfi.banner;

import java.util.List;

/**
 * Created by pngfi on 2018/3/22.
 */

public interface Indicator {

    <T>void setData(List<T> data);


    void setSelected(int position);

}
