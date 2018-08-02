package com.chuongvd.app.signal.ui

import android.databinding.BindingAdapter
import android.os.SystemClock
import android.support.annotation.DimenRes
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chuongvd.app.signal.widget.GridSpacingItemDecoration

private val THRESHOLD_CLICK_TIME: Long = 1000

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
  view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("clickSafe")
fun setClickSafe(view: View, listener: View.OnClickListener) {
  view.setOnClickListener(object : View.OnClickListener {
    private var mLastClickTime: Long = 0

    override fun onClick(v: View) {
      if (SystemClock.elapsedRealtime() - mLastClickTime < THRESHOLD_CLICK_TIME) {
        return
      }
      listener.onClick(v)
      mLastClickTime = SystemClock.elapsedRealtime()
    }
  })
}

/***
 * Set view selected
 * @param view
 * @param isSelected
 */
@BindingAdapter("selected")
fun setViewSelected(view: View, isSelected: Boolean) {
  view.isSelected = isSelected
}

/***
 * Set adapter for viewpager
 * @param viewPager
 * @param adapter
 */
@BindingAdapter(value = *arrayOf("pagerAdapter", "onPagerChangeListener", "offScreenLimit"),
    requireAll = false)
fun setPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter,
    onPageChangeListener: ViewPager.OnPageChangeListener?, offScreen: Int) {
  viewPager.adapter = adapter
  if (onPageChangeListener != null) {
    viewPager.addOnPageChangeListener(onPageChangeListener)
  }
  if (offScreen != 0) {
    viewPager.offscreenPageLimit = offScreen
  }
}

/***
 * Set position for viewpager
 * @param viewPager
 * @param selectedPosition
 */
@BindingAdapter(value = *arrayOf("selectedPosition"), requireAll = false)
fun setPagerPosition(viewPager: ViewPager, selectedPosition: Int) {
  if (selectedPosition < 0) return
  if (null == viewPager.adapter) return
  if (viewPager.adapter!!.count <= selectedPosition) return
  viewPager.currentItem = selectedPosition
}

/***
 * Set adapter for recyclerview
 * @param recyclerView
 * @param adapter
 * @param columns
 * @param orientation
 * @param itemDecoration
 */
@BindingAdapter(value = *arrayOf("adapter", "columns", "orientation", "itemDecoration", "space",
    "includeEdge", "reverse"), requireAll = false)
fun setRecyclerViewData(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>,
    columns: Int, orientation: Int, itemDecoration: RecyclerView.ItemDecoration?,
    @DimenRes space: Int, includeEdge: Boolean, isReverse: Boolean) {
  var itemDecoration = itemDecoration
  val isGrid = columns > 1
  recyclerView.adapter = adapter
  recyclerView.setHasFixedSize(true)
  val layoutManager: RecyclerView.LayoutManager
  val context = recyclerView.context
  if (isGrid) {
    layoutManager = GridLayoutManager(context, columns)
    itemDecoration = GridSpacingItemDecoration(context, columns, space, includeEdge, false)
  } else {

    layoutManager = LinearLayoutManager(context, orientation, false)
    layoutManager.reverseLayout = isReverse
  }
  if (itemDecoration != null) {
    recyclerView.addItemDecoration(itemDecoration)
  }
  recyclerView.layoutManager = layoutManager
}