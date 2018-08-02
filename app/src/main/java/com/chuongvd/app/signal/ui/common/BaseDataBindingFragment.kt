package com.chuongvd.app.signal.ui.common

import android.arch.lifecycle.AndroidViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDataBindingFragment<B : ViewDataBinding, V : AndroidViewModel> : Fragment() {

  lateinit var mBinding: B

  protected lateinit var mViewModel: V

  abstract val contentViewLayoutId: Int

  //check is first show fragment
  var isFirstLoad = true

  protected abstract fun initData()

  protected abstract fun subscribeToViewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    mBinding = DataBindingUtil.inflate(inflater, contentViewLayoutId, container, false)
    return mBinding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onCreateFragment()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initData()
    subscribeToViewModel()
  }

  fun onCreateFragment() {

  }
}
