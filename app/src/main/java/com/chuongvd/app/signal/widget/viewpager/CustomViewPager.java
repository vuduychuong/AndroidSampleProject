package com.chuongvd.app.signal.widget.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.chuongvd.app.signal.R;

public class CustomViewPager extends ViewPager {

    private boolean isDisableSmooth = false;
    private boolean isDisableSwipe = false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomViewPager);
        isDisableSmooth = a.getBoolean(R.styleable.CustomViewPager_is_disable_smooth, false);
        isDisableSwipe = a.getBoolean(R.styleable.CustomViewPager_is_disable_swipe, false);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, !isDisableSmooth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isDisableSwipe && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isDisableSwipe && super.onInterceptTouchEvent(ev);
    }
}
