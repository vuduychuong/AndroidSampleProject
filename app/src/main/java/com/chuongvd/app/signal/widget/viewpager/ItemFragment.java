package com.chuongvd.app.signal.widget.viewpager;

import android.support.v4.app.Fragment;

public class ItemFragment {
    private String mTitle;
    private Fragment mFragment;

    public ItemFragment(String title, Fragment fragment) {
        mTitle = title;
        mFragment = fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
