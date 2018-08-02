package com.chuongvd.app.signal.widget.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

class CustomPagerAdapter : FragmentStatePagerAdapter {
  private val mItemFragmentList = ArrayList<ItemFragment>()

  val itemFragmentList: List<ItemFragment>
    get() = mItemFragmentList

  constructor(fm: FragmentManager) : super(fm) {}

  constructor(fm: FragmentManager, itemFragmentList: List<ItemFragment>) : super(fm) {
    mItemFragmentList.clear()
    mItemFragmentList.addAll(itemFragmentList)
  }

  override fun getItem(position: Int): Fragment {
    return mItemFragmentList[position].fragment
  }

  override fun getCount(): Int {
    return mItemFragmentList.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return mItemFragmentList[position].title
  }

  fun addFragment(itemFragment: ItemFragment): Int {
    mItemFragmentList.add(itemFragment)
    notifyDataSetChanged()
    return mItemFragmentList.size - 1
  }
}
