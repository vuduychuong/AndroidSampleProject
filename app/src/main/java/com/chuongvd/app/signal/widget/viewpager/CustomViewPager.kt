package com.chuongvd.app.signal.widget.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.chuongvd.app.signal.R

class CustomViewPager : ViewPager {

  private var isDisableSmooth = false
  private var isDisableSwipe = false

  constructor(context: Context) : super(context) {}

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.CustomViewPager)
    isDisableSmooth = a.getBoolean(R.styleable.CustomViewPager_is_disable_smooth, false)
    isDisableSwipe = a.getBoolean(R.styleable.CustomViewPager_is_disable_swipe, false)
  }

  override fun setCurrentItem(item: Int) {
    super.setCurrentItem(item, !isDisableSmooth)
  }

  override fun onTouchEvent(ev: MotionEvent): Boolean {
    return !isDisableSwipe && super.onTouchEvent(ev)
  }

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    return !isDisableSwipe && super.onInterceptTouchEvent(ev)
  }
}
