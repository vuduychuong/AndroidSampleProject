package com.chuongvd.support.adapterbinding;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/3/18
 *
 * @author Chuongvd
 */
public abstract class SelectableAdapter<VIEWHOLDER extends SelectableViewHolder, MODEL extends ItemSelectable>
        extends AdapterBinding<VIEWHOLDER, MODEL> {

    protected ObservableBoolean selectedMode = new ObservableBoolean();
    protected int prevSelectedPosition;

    public SelectableAdapter(Context context, OnRecyclerItemListener<MODEL> itemListener,
            boolean isSelectedMode) {
        super(context, itemListener);
        selectedMode.set(isSelectedMode);
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

    @Override
    public void onBindViewHolder(ViewHolderBinding holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mBinding.getRoot().setOnClickListener(v -> {
            if (holder.mListener != null) {
                holder.mListener.onItemClick(position, holder.data);
                if (selectedMode.get()) {
                    prevSelectedPosition = position;
                    onSelectedItemChanged(position, (MODEL) holder.data);
                }
            }
        });
    }

    protected void onSelectedItemChanged(int position, MODEL data) {
        data.setSelected(!data.isSelected());
    }

    @Override
    public void softUpdateListData(List<MODEL> newData) {
        super.softUpdateListData(newData);
        if (prevSelectedPosition < getItemCount()) selectedItem(prevSelectedPosition);
    }

    public int getPrevSelectedPosition() {
        return prevSelectedPosition;
    }

    public boolean isSelectedMode() {
        return selectedMode.get();
    }

    public void setSelectedMode(boolean selectedMode) {
        this.selectedMode.set(selectedMode);
        removeSelectedState();
    }

    public void selectedItem(int position) {
        if (getData().size() <= 0 || position >= getData().size()) return;
        mList.get(position).setSelected(true);
    }

    public void selectedAll() {
        if (getData().size() <= 0) return;
        for (int i = 0; i < getData().size(); i++) {
            getData().get(i).setSelected(true);
        }
    }

    public void removeSelectedState() {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (!mList.get(i).isSelected()) continue;
            mList.get(i).setSelected(false);
        }
    }

    public void removeSelectedItems() {
        for (int i = getData().size() - 1; i >= 0; i--) {
            if (getData().get(i).isSelected()) {
                removePosition(i);
            }
        }
    }

    public void removeItem(int position) {
        if (position < 0 || position >= mList.size()) return;
        mList.remove(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        int result = 0;
        for (MODEL model : mList) {
            if (model.isSelected()) result++;
        }
        return result;
    }

    public List<MODEL> getSelectedItems() {
        List<MODEL> list = new ArrayList<>();
        for (MODEL model : mList) {
            if (model.isSelected()) list.add(model);
        }
        return list;
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> list = new ArrayList<>();
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            MODEL model = mList.get(i);
            if (model.isSelected()) {
                list.add(i);
            }
        }
        return list;
    }
}