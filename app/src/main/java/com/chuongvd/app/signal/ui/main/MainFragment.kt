package com.chuongvd.app.signal.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableInt
import android.view.View
import com.chuongvd.app.signal.R
import com.chuongvd.app.signal.constant.BottomNavigation
import com.chuongvd.app.signal.databinding.FragmentHomeBinding
import com.chuongvd.app.signal.listener.BottomNavigationListener
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment
import com.chuongvd.app.signal.viewmodel.HomeViewModel
import com.chuongvd.app.signal.widget.viewpager.CustomPagerAdapter

class MainFragment : BaseDataBindingFragment<FragmentHomeBinding, HomeViewModel>(), BottomNavigationListener {

  private var mPagerAdapter: CustomPagerAdapter? = null
  private val itemSelected = ObservableInt()

  override val contentViewLayoutId: Int
    get() = R.layout.fragment_home

  override fun initData() {
    val factory = HomeViewModel.Factory(activity!!.application)
    mViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
    mPagerAdapter = CustomPagerAdapter(childFragmentManager)
    // TODO: 7/29/18 Init PagerAdapter
    mBinding.setBottomNavigationListener(this)
    mBinding.setItemSelected(itemSelected)
    mBinding.setPagerAdapter(mPagerAdapter)
  }

  override fun subscribeToViewModel() {

  }

  override fun onClickHome(v: View) {
    if (itemSelected.get() == BottomNavigation.SIGNAL) return
    itemSelected.set(BottomNavigation.SIGNAL)
  }

  override fun onClickChart(v: View) {
    if (itemSelected.get() == BottomNavigation.CHART) return
    itemSelected.set(BottomNavigation.CHART)
  }

  override fun onClickInfo(v: View) {
    if (itemSelected.get() == BottomNavigation.INFOMATION) return
    itemSelected.set(BottomNavigation.INFOMATION)
  }

  override fun onClickProfile(v: View) {
    if (itemSelected.get() == BottomNavigation.PROFILE) return
    itemSelected.set(BottomNavigation.PROFILE)
  }
}
