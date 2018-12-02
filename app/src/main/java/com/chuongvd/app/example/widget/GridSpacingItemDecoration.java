package com.chuongvd.app.example.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount;
    private int mSpacing;
    private boolean mIncludeEdge;
    private boolean hasHeader;

    public GridSpacingItemDecoration(Context context, int spanCount, @DimenRes int dimens,
            boolean includeEdge, boolean hasHeader) {
        try {
            this.mSpacing = context.getResources().getDimensionPixelSize(dimens);
        } catch (Resources.NotFoundException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
        this.mSpanCount = spanCount;
        this.mIncludeEdge = includeEdge;
        this.hasHeader = hasHeader;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if (position == 0) return;
        int column = (position - 1) % mSpanCount; // item column

        if (mIncludeEdge) {
            outRect.left = mSpacing
                    - column * mSpacing
                    / mSpanCount; // mSpacing - column * ((1f / mSpanCount) * mSpacing)
            outRect.right = (column + 1) * mSpacing
                    / mSpanCount; // (column + 1) * ((1f / mSpanCount) * mSpacing)

            if (position < mSpanCount + 1) { // top edge
                outRect.top = mSpacing;
            }
            outRect.bottom = mSpacing; // item bottom
        } else {
            // column * ((1f / mSpanCount) * mSpacing)
            outRect.left = column * mSpacing / mSpanCount;
            outRect.right = mSpacing
                    - (column + 1) * mSpacing
                    / mSpanCount; // mSpacing - (column + 1) * ((1f /    mSpanCount) * mSpacing)
            if (position >= mSpanCount) {
                outRect.top = mSpacing; // item top
            }
        }
    }
}
