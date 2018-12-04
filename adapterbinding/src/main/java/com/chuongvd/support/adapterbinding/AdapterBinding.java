package com.chuongvd.support.adapterbinding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.chuongvd.support.adapterbinding.databinding.ItemNoDataBinding;
import java.util.ArrayList;
import java.util.List;

public abstract class AdapterBinding<VIEWHOLDER extends ViewHolderBinding, MODEL>
        extends RecyclerView.Adapter<ViewHolderBinding> {

    public static final int TYPE_NO_DATA = 0;
    public static final int TYPE_NORMAL = 1;
    public String EMPTY_STRING = "";
    private Context mContext;
    public List<MODEL> mList;
    private LayoutInflater mLayoutInflater;
    protected OnRecyclerItemListener<MODEL> mItemListener;
    private boolean isFirst = true;
    private boolean enableShowNoData = false;

    protected abstract VIEWHOLDER getViewHolder(ViewGroup parent, LayoutInflater inflater);

    public AdapterBinding(Context context, List<MODEL> list) {
        mContext = context;
        mList = list;
    }

    public AdapterBinding(List<MODEL> list) {
        mList = list;
    }

    public AdapterBinding(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public AdapterBinding(Context context, OnRecyclerItemListener<MODEL> itemListener) {
        mContext = context;
        mList = new ArrayList<>();
        this.mItemListener = itemListener;
    }

    public void setEnableShowNoData(boolean enableShowNoData) {
        this.enableShowNoData = enableShowNoData;
    }

    public void add(MODEL MODEL) {
        getData().add(MODEL);
        isFirst = false;
        notifyItemChanged(getData().size() - 1);
    }

    public void add(List<MODEL> data) {
        int size = getData().size();
        getData().addAll(data);
        isFirst = false;
        if (size == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(size);
        }
    }

    public void addPreviousEnd(List<MODEL> data) {
        if (data.isEmpty()) return;
        isFirst = false;
        if (getData().isEmpty()) {
            getData().addAll(data);
            notifyDataSetChanged();
        } else {
            int size = getData().size();
            getData().addAll(data);
            notifyItemRangeInserted(size - 1, data.size());
        }
    }

    @Deprecated
    public void setData(List<MODEL> data) {
        if (data == null) return;
        getData().clear();
        getData().addAll(data);
        isFirst = false;
        notifyDataSetChanged();
    }

    @Deprecated
    public void setDataRefresh(List<MODEL> data) {
        if (data == null) return;
        getData().clear();
        getData().addAll(data);
        softUpdateListData(data);
    }

    public void remove(MODEL MODEL) {
        int position = -1;
        for (int i = 0; i < getData().size(); i++) {
            if (!MODEL.equals(getData().get(i))) {
                continue;
            }
            position = i;
            break;
        }

        if (position == -1) return;
        isFirst = false;
        removePosition(position);
    }

    public void removePosition(int position) {
        if (getData().size() < position) return;
        getData().remove(position);
        isFirst = false;
        notifyItemRemoved(position);
    }

    public boolean isEmpty() {
        return mList.size() == 0;
    }

    public void setItemListener(OnRecyclerItemListener<MODEL> listener) {
        mItemListener = listener;
        notifyDataSetChanged();
    }

    public void remove(List<MODEL> data) {
        getData().removeAll(data);
        isFirst = false;
        notifyDataSetChanged();
    }

    public void removeAll() {
        getData().clear();
        isFirst = false;
        notifyDataSetChanged();
    }

    @Deprecated
    public void refreshData(List<MODEL> data) {
        getData().clear();
        getData().addAll(data);
        isFirst = false;
        notifyDataSetChanged();
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public List<MODEL> getData() {
        return mList == null ? mList = new ArrayList<>() : mList;
    }

    protected LayoutInflater getLayoutInflater(Context context) {
        mContext = context;
        return mLayoutInflater == null ? mLayoutInflater = LayoutInflater.from(context)
                : mLayoutInflater;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolderBinding onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderBinding viewHolderBinding;
        if (viewType == TYPE_NO_DATA) {
            viewHolderBinding = getNoDataViewHolder(parent, getLayoutInflater(parent.getContext()));
        } else {
            viewHolderBinding = getViewHolder(parent, getLayoutInflater(parent.getContext()));
        }
        return viewHolderBinding;
    }

    public ViewHolderBinding getNoDataViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new NoDataViewHolder(ItemNoDataBinding.inflate(inflater, parent, false));
    }

    public void setEmptyString(String emptyString) {
        EMPTY_STRING = emptyString;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolderBinding holder, int position) {
        if (getData().size() > 0) {
            holder.bindData(mList.get(position));
        } else {
            if (!isFirst && enableShowNoData) {
                holder.bindData(EMPTY_STRING);
            }
            if (isFirst) {
                isFirst = false;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getData().size() == 0) {
            return TYPE_NO_DATA;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (getData().size() == 0 && enableShowNoData && !isFirst) {
            return 1;
        }
        return getData().size();
    }

    public void softUpdateListData(List<MODEL> newData) {
        isFirst = false;
        if (newData == null || newData.isEmpty()) {
            if (mList.size() == 0) return;
            int size = mList.size();
            mList.clear();
            notifyDataSetChanged();
            return;
        }
        if (mList.size() == 0) {
            mList.addAll(newData);
            notifyDataSetChanged();
            return;
        }
        int newSize = newData.size();
        int oldSize = mList.size();
        int minSize = Math.min(oldSize, newSize);
        int maxSize = Math.max(oldSize, newSize);
        for (int i = 0; i < minSize; i++) {
            MODEL data = newData.get(i);
            if (!data.equals(mList.get(i))) {
                mList.set(i, data);
                notifyItemChanged(i);
            }
        }
        for (int i = minSize; i < maxSize; i++) {
            if (newSize > oldSize) {
                mList.add(newData.get(i));
                notifyItemInserted(i);
            } else if (newSize < oldSize) {
                mList.remove(newSize);
                notifyItemRemoved(newSize);
            }
        }
    }

    public void updateBindableData(List<MODEL> data) {
        if (data == null) return;
        isFirst = false;
        int oldSize = mList.size();
        int newSize = data.size();
        if (newSize == 0) {
            mList.clear();
            notifyDataSetChanged();
            return;
        }
        if (oldSize == 0) {
            mList.addAll(data);
            notifyDataSetChanged();
            return;
        }
        if (newSize < oldSize) {
            for (int i = 0; i < newSize; i++) {
                MODEL model = mList.get(i);
                if (model instanceof BindableDataSupport) {
                    ((BindableDataSupport) model).updateBindableData(data.get(i));
                } else {
                    if (!data.get(i).equals(mList.get(i))) {
                        mList.set(i, data.get(i));
                        notifyItemChanged(i);
                    }
                }
            }
            for (int i = newSize; i < oldSize; i++) {
                mList.remove(newSize);
            }
            notifyItemRangeRemoved(newSize, oldSize - newSize);
            return;
        }
        if (newSize == oldSize) {
            for (int i = 0; i < newSize; i++) {
                MODEL model = mList.get(i);
                if (model instanceof BindableDataSupport) {
                    ((BindableDataSupport) model).updateBindableData(data.get(i));
                } else {
                    if (!data.get(i).equals(mList.get(i))) {
                        mList.set(i, data.get(i));
                        notifyItemChanged(i);
                    }
                }
            }
            return;
        }
        /*
          newSize > oldSize
         */
        for (int i = 0; i < oldSize; i++) {
            MODEL model = mList.get(i);
            if (model instanceof BindableDataSupport) {
                ((BindableDataSupport) model).updateBindableData(data.get(i));
            } else {
                if (!data.get(i).equals(mList.get(i))) {
                    mList.set(i, data.get(i));
                    notifyItemChanged(i);
                }
            }
        }
        for (int i = oldSize; i < newSize; i++) {
            mList.add(data.get(i));
        }
        notifyItemRangeInserted(oldSize, newSize - oldSize);
    }

    class NoDataViewHolder extends ViewHolderBinding<ItemNoDataBinding, String> {

        NoDataViewHolder(ItemNoDataBinding binding) {
            super(binding);
        }

        @Override
        public void bindData(String model) {
            if (TextUtils.isEmpty(model)) {
                model = getContext().getString(R.string.no_data);
            }
            mBinding.setText(model);
        }
    }

    public interface OnRecyclerItemListener<MODEL> {
        void onItemClick(int position, MODEL data);
    }
}
