package com.chuongvd.app.signal.ui.common

import android.arch.lifecycle.AndroidViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseDataBindingActivity<B : ViewDataBinding, V : AndroidViewModel> : AppCompatActivity() {

  protected lateinit var mBinding: B

  protected lateinit var mViewModel: V

  abstract val contentViewLayoutId: Int

  abstract fun initData()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView(this, contentViewLayoutId)
    initData()
  }
}
