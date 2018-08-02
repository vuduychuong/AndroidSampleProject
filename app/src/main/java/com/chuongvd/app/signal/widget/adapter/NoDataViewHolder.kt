package com.chuongvd.app.signal.widget.adapter

import android.content.Context
import android.text.TextUtils
import com.chuongvd.app.signal.R
import com.chuongvd.app.signal.databinding.ItemNoDataBinding

class NoDataViewHolder(val context: Context,
    binding: ItemNoDataBinding) : BaseViewHolderBinding<ItemNoDataBinding, String>(binding) {

  override fun bindData(data: String) {
    var text = data
    if (TextUtils.isEmpty(text)) {
      text = context.getString(R.string.no_data)
    }
    mBinding.setText(text)
  }
}