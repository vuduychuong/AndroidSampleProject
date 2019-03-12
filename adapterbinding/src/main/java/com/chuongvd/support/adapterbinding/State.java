package com.chuongvd.support.adapterbinding;

/**
 * Created by chuongvd on 1/8/19.
 */
public @interface State {
    int LOADED = 0;
    int LOADING = 1;
    int FAILED = 2;
    int EMPTY = 3;
}
