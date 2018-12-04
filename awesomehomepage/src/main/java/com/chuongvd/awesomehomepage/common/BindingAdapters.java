package com.chuongvd.awesomehomepage.common;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class BindingAdapters {

    /***
     * Set adapter for viewpager
     * @param viewPager
     * @param adapter
     */
    @BindingAdapter(value = {
            "pagerAdapter", "onPagerChangeListener", "offScreenLimit"
    }, requireAll = false)
    public static void setPagerAdapter(ViewPager viewPager, PagerAdapter adapter,
            ViewPager.OnPageChangeListener onPageChangeListener, int offScreen) {
        viewPager.setAdapter(adapter);
        if (onPageChangeListener != null) {
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
        if (offScreen != 0) {
            viewPager.setOffscreenPageLimit(offScreen);
        }
    }

    /***
     * Set position for viewpager
     * @param viewPager
     * @param selectedPosition
     */
    @BindingAdapter(value = { "selectedPosition" }, requireAll = false)
    public static void setPagerPosition(ViewPager viewPager, int selectedPosition) {
        if (selectedPosition < 0) return;
        if (null == viewPager.getAdapter()) return;
        if (viewPager.getAdapter().getCount() <= selectedPosition) return;
        viewPager.setCurrentItem(selectedPosition);
    }
}