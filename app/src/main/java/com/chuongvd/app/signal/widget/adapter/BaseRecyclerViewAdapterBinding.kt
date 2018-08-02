package com.chuongvd.app.signal.widget.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chuongvd.app.signal.databinding.ItemNoDataBinding
import java.util.*

abstract class BaseRecyclerViewAdapterBinding<VIEWHOLDER : BaseViewHolderBinding<*, MODEL>, MODEL> : RecyclerView.Adapter<BaseViewHolderBinding<*, MODEL>> {
  var EMPTY_STRING = ""
  var context: Context? = null
    private set
  var mList: MutableList<MODEL> = mutableListOf()
  private var mLayoutInflater: LayoutInflater? = null
  protected var mItemListener: OnRecyclerItemListener<MODEL>? = null
  private var isFirst = true
  private var enableShowNoData = false

  val isEmpty: Boolean
    get() = mList.size == 0

  var data: MutableList<MODEL>
    get() = mList
    set(data) {
      data.clear()
      data.addAll(data)
      notifyDataSetChanged()
      isFirst = false
    }

  protected abstract fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater): VIEWHOLDER

  constructor(context: Context, list: MutableList<MODEL>) {
    this.context = context
    mList = list
  }

  constructor(list: MutableList<MODEL>) {
    mList = list
  }

  constructor(context: Context) {
    this.context = context
    mList = ArrayList()
  }

  constructor(context: Context,
      itemListener: OnRecyclerItemListener<MODEL>) {
    this.context = context
    mList = ArrayList()
    this.mItemListener = itemListener
  }

  fun setEnableShowNoData(enableShowNoData: Boolean) {
    this.enableShowNoData = enableShowNoData
  }

  fun add(MODEL: MODEL) {
    data.add(MODEL)
    notifyItemChanged(data.size - 1)
    isFirst = false
  }

  fun add(list: List<MODEL>) {
    val size = this.data.size
    this.data.addAll(list)
    if (size == 0) {
      notifyDataSetChanged()
    } else {
      notifyItemInserted(size)
    }
    isFirst = false
  }

  fun addPreviousEnd(list: List<MODEL>) {
    if (list.isEmpty()) return
    if (this.data.isEmpty()) {
      this.data.addAll(list)
      notifyDataSetChanged()
    } else {
      val size = this.data.size
      data.addAll(list)
      notifyItemRangeInserted(size - 1, this.data.size)
    }
    isFirst = false
  }

  @Synchronized
  fun remove(MODEL: MODEL) {
    var position = -1
    for (i in 0 until data.size) {
      if (MODEL != data[i]) {
        continue
      }
      position = i
      break
    }

    if (position == -1) return
    removePosition(position)
    isFirst = false
  }

  @Synchronized
  fun removePosition(position: Int) {
    if (data.size < position) return
    data.removeAt(position)
    notifyItemChanged(position)
    isFirst = false
  }

  fun setItemListener(listener: OnRecyclerItemListener<MODEL>) {
    mItemListener = listener
    notifyDataSetChanged()
  }

  @Synchronized
  fun remove(list: List<MODEL>) {
    data.removeAll(list)
    notifyDataSetChanged()
    isFirst = false
  }

  @Synchronized
  fun removeAll() {
    data.clear()
    notifyDataSetChanged()
    isFirst = false
  }

  @Synchronized
  fun refreshData(list: List<MODEL>) {
    data.clear()
    data.addAll(list)
    notifyDataSetChanged()
    isFirst = false
  }

  fun setFirst(first: Boolean) {
    isFirst = first
  }

  private fun getLayoutInflater(context: Context): LayoutInflater {
    this.context = context
    if (mLayoutInflater == null)
      mLayoutInflater = LayoutInflater.from(context)
    else
      mLayoutInflater
    return mLayoutInflater!!
  }

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): BaseViewHolderBinding<*, MODEL> {
    val baseViewHolderBinding: BaseViewHolderBinding<*, MODEL>
    if (viewType == TYPE_NO_DATA) {
      baseViewHolderBinding = getNoDataViewHolder(parent,
          getLayoutInflater(parent.context)) as BaseViewHolderBinding<*, MODEL>
    } else {
      baseViewHolderBinding = getViewHolder(parent, getLayoutInflater(parent.context))
    }
    return baseViewHolderBinding
  }

  fun getNoDataViewHolder(parent: ViewGroup,
      inflater: LayoutInflater): NoDataViewHolder {
    return NoDataViewHolder(context!!, ItemNoDataBinding.inflate(inflater, parent, false))
  }

  fun setEmptyString(emptyString: String) {
    EMPTY_STRING = emptyString
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: BaseViewHolderBinding<*, MODEL>, position: Int) {
    if (data.size > 0) {
      holder.bindData(data.get(position))
    } else {
      if (!isFirst && enableShowNoData) {
        holder.bindData(EMPTY_STRING as MODEL)
      }
      if (isFirst) {
        isFirst = false
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (data.size == 0) {
      TYPE_NO_DATA
    } else TYPE_NORMAL
  }

  override fun getItemCount(): Int {
    return if (data.size == 0) {
      1
    } else data.size
  }

  fun softUpdateListData(newData: List<MODEL>?) {
    if (newData == null || newData.isEmpty()) {
      mList.clear()
      return
    }
    val newSize = newData.size
    val oldSize = mList.size
    val minSize = Math.min(oldSize, newSize)
    val maxSize = Math.max(oldSize, newSize)
    for (i in 0 until minSize) {
      val data = newData[i]
      if (data != mList[i]) {
        mList.removeAt(i)
        mList.add(i, data)
        notifyItemChanged(i)
      }
    }
    for (i in minSize until maxSize) {
      if (newSize > oldSize) {
        mList.add(newData[i])
        notifyItemRemoved(i)
      } else {
        mList.removeAt(newSize)
        notifyItemRemoved(newSize)
      }
    }
  }

  interface OnRecyclerItemListener<MODEL> {
    fun onItemClick(position: Int, data: MODEL)
  }

  companion object {

    val TYPE_NO_DATA = 0
    val TYPE_NORMAL = 1
  }
}
