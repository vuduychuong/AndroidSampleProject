package com.chuongvd.app.signal.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

class GridSpacingItemDecoration(context: Context,
    private val mSpanCount: Int, @DimenRes dimens: Int,
    private val mIncludeEdge: Boolean,
    private val hasHeader: Boolean) : RecyclerView.ItemDecoration() {
  private var mSpacing: Int = 0

  init {
    try {
      this.mSpacing = context.resources.getDimensionPixelSize(dimens)
    } catch (e: Resources.NotFoundException) {
      Log.e(this.javaClass.simpleName, e.message)
    }

  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
      state: RecyclerView.State?) {
    val position = parent.getChildAdapterPosition(view) // item position
    if (position == 0) return
    val column = (position - 1) % mSpanCount // item column

    if (mIncludeEdge) {
      outRect.left = mSpacing - column * mSpacing / mSpanCount // mSpacing - column * ((1f / mSpanCount) * mSpacing)
      outRect.right = (column + 1) * mSpacing / mSpanCount // (column + 1) * ((1f / mSpanCount) * mSpacing)

      if (position < mSpanCount + 1) { // top edge
        outRect.top = mSpacing
      }
      outRect.bottom = mSpacing // item bottom
    } else {
      // column * ((1f / mSpanCount) * mSpacing)
      outRect.left = column * mSpacing / mSpanCount
      outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount // mSpacing - (column + 1) * ((1f /    mSpanCount) * mSpacing)
      if (position >= mSpanCount) {
        outRect.top = mSpacing // item top
      }
    }
  }
}
