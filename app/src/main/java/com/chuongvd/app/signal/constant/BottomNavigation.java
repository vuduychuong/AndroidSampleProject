package com.chuongvd.app.signal.constant;

import android.support.annotation.IntDef;

@IntDef({
        BottomNavigation.SIGNAL, BottomNavigation.CHART, BottomNavigation.INFOMATION,
        BottomNavigation.PROFILE
})
public @interface BottomNavigation {
    int SIGNAL = 0;
    int CHART = 1;
    int INFOMATION = 2;
    int PROFILE = 3;
}
