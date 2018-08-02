package com.chuongvd.app.signal.widget.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

open class BaseViewHolderBinding<BINDINGVIEW : ViewDataBinding, MODEL> : RecyclerView.ViewHolder {

  var mBinding: BINDINGVIEW
  var mListener: BaseRecyclerViewAdapterBinding.OnRecyclerItemListener<MODEL>? = null
  private var data: MODEL? = null

  constructor(binding: BINDINGVIEW) : super(binding.root) {
    mBinding = binding
  }

  constructor(binding: BINDINGVIEW,
      listener: BaseRecyclerViewAdapterBinding.OnRecyclerItemListener<MODEL>) : super(
      binding.root) {
    mBinding = binding
    mListener = listener
  }

  open fun bindData(model: MODEL) {
    data = model
    mBinding.root.setOnClickListener { v ->
      if (mListener != null) {
        mListener!!.onItemClick(adapterPosition, model)
      }
    }
  }
}
