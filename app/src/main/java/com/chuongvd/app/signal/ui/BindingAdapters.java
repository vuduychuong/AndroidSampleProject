package com.chuongvd.app.signal.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.SystemClock;
import android.support.annotation.DimenRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chuongvd.app.signal.widget.GridSpacingItemDecoration;

public class BindingAdapters {
    private static final long THRESHOLD_CLICK_TIME = 1000;

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({ "clickSafe" })
    public static void setClickSafe(View view, final View.OnClickListener listener) {
        view.setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime = 0;

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < THRESHOLD_CLICK_TIME) {
                    return;
                }
                listener.onClick(v);
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        });
    }

    /***
     * Set view selected
     * @param view
     * @param isSelected
     */
    @BindingAdapter({ "selected" })
    public static void setViewSelected(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

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

    /***
     * Set adapter for recyclerview
     * @param recyclerView
     * @param adapter
     * @param columns
     * @param orientation
     * @param itemDecoration
     */
    @BindingAdapter(value = {
            "adapter", "columns", "orientation", "itemDecoration", "space", "includeEdge", "reverse"
    }, requireAll = false)
    public static void setRecyclerViewData(RecyclerView recyclerView, RecyclerView.Adapter adapter,
            int columns, int orientation, RecyclerView.ItemDecoration itemDecoration,
            @DimenRes int space, boolean includeEdge, boolean isReverse) {
        boolean isGrid = columns > 1;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        Context context = recyclerView.getContext();
        if (isGrid) {
            layoutManager = new GridLayoutManager(context, columns);
            itemDecoration =
                    new GridSpacingItemDecoration(context, columns, space, includeEdge, false);
        } else {

            layoutManager = new LinearLayoutManager(context, orientation, false);
            ((LinearLayoutManager) layoutManager).setReverseLayout(isReverse);
        }
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }
        recyclerView.setLayoutManager(layoutManager);
    }
}