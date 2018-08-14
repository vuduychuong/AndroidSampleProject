package com.chuongvd.app.signal.widget.viewpager;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class ItemNavigationFragment extends ItemFragment {

    private AHBottomNavigationItem mNavigationItem;

    public ItemNavigationFragment(String title, Fragment fragment) {
        super(title, fragment);
        mNavigationItem = new AHBottomNavigationItem(title, null);
    }

    public ItemNavigationFragment(String title, @DrawableRes int resource, Fragment fragment) {
        super(title, fragment);
        mNavigationItem = new AHBottomNavigationItem(title, resource);
    }

    public ItemNavigationFragment(String title, @DrawableRes int resource, @ColorRes int color,
            Fragment fragment) {
        super(title, fragment);
        mNavigationItem = new AHBottomNavigationItem(title, resource, color);
    }

    public ItemNavigationFragment(String title, Drawable drawable, int color, Fragment fragment) {
        super(title, fragment);
        mNavigationItem = new AHBottomNavigationItem(title, drawable, color);
    }

    public AHBottomNavigationItem getNavigationItem() {
        return mNavigationItem;
    }
}
