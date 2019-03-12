package com.chuongvd.support.adapterbinding;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chuongvd on 1/8/19.
 */
public abstract class StatefulAdapterBinding<V extends SelectableViewHolder, M extends ItemSelectable>
        extends AdapterBinding<V, M> {

    protected ObservableBoolean selectedMode = new ObservableBoolean();
    protected int prevSelectedPosition;

    @State
    private int mState = State.LOADED;

    private final boolean mItemViewWillBeProvided;
    private final boolean mLoadingViewWillBeProvided;
    private final boolean mFailedViewWillBeProvided;
    private final boolean mEmptyViewWillBeProvided;

    private List<M> mOriginList = new ArrayList<>();
    private String mFilterText;
    private boolean mIsFilter;

    public StatefulAdapterBinding(Context context, StateParameters stateParameters) {
        this(context, stateParameters, null);
    }

    public StatefulAdapterBinding(Context context, StateParameters stateParameters,
            OnRecyclerItemListener<M> itemListener) {
        this(context, stateParameters, itemListener, false);
    }

    public StatefulAdapterBinding(Context context, StateParameters stateParameters,
            OnRecyclerItemListener<M> itemListener, boolean isSelectedMode) {
        super(context, itemListener);
        mItemViewWillBeProvided = stateParameters.itemViewWillBeProvided;
        mLoadingViewWillBeProvided = stateParameters.loadingViewWillBeProvided;
        mFailedViewWillBeProvided = stateParameters.failedViewWillBeProvided;
        mEmptyViewWillBeProvided = stateParameters.emptyViewWillBeProvided;
        selectedMode.set(isSelectedMode);
    }

    @Override
    public ViewHolderBinding onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderBinding viewHolderBinding;
        switch (viewType) {
            case State.LOADED:
                viewHolderBinding = getViewHolder(parent, getLayoutInflater(parent.getContext()));
                break;
            case State.LOADING:
                viewHolderBinding =
                        getLoadingViewHolder(getLayoutInflater(parent.getContext()), parent);
                break;
            case State.FAILED:
                viewHolderBinding =
                        getFailedViewHolder(getLayoutInflater(parent.getContext()), parent);
                break;
            case State.EMPTY:
                viewHolderBinding =
                        getNoDataViewHolder(parent, getLayoutInflater(parent.getContext()));
                break;
            default:
                throw new IllegalStateException("invalid state");
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
                    onSelectedItemChanged(position, (M) holder.data);
                }
            }
        });
    }

    protected void onSelectedItemChanged(int position, M data) {
        data.setSelected(!data.isSelected());
    }

    @Override
    public int getItemViewType(int position) {
        return mState;
    }

    @Override
    public int getItemCount() {
        switch (mState) {
            case State.EMPTY:
                return 1;
            case State.LOADED:
                return mList.size();
            case State.FAILED:
                return 1;
            case State.LOADING:
                return 1;
            default:
                throw new IllegalStateException("invalid state");
        }
    }

    @Override
    public void updateBindableData(List<M> data) {
        if (!mIsFilter && data != null) {
            mOriginList.clear();
            mOriginList.addAll(data);
        }
        if (!TextUtils.isEmpty(mFilterText) && data != null) {
            List<M> list = filterData(data, mFilterText);
            data.clear();
            data.addAll(list);
        }
        mIsFilter = false;
        super.updateBindableData(data);
    }

    protected List<M> filterData(List<M> data, String filterText) {
        return data;
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
            M data = getData().get(i);
            if (data.isSelected()) continue;
            if (data instanceof ItemDisable && ((ItemDisable) data).isDisable()) continue;
            if (mItemListener != null) {
                mItemListener.onItemClick(i, data);
            }
            data.setSelected(true);
        }
    }

    public void removeSelectedState() {
        int size = getData().size();
        for (int i = 0; i < size; i++) {
            M data = getData().get(i);
            if (!data.isSelected()) continue;
            if (data instanceof ItemDisable && ((ItemDisable) data).isDisable()) continue;
            if (mItemListener != null) {
                mItemListener.onItemClick(i, data);
            }
            data.setSelected(false);
        }
    }

    public void removeSelectedItems() {
        int size = getData().size();
        if (size == 0) return;
        for (int i = size - 1; i >= 0; i--) {
            M data = getData().get(i);
            if (data.isSelected()) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(i, data);
                }
                removePosition(i);
            }
        }
    }

    public int getSelectedCount() {
        int result = 0;
        for (M model : mList) {
            if (model.isSelected()) result++;
        }
        return result;
    }

    public List<M> getSelectedItems() {
        List<M> list = new ArrayList<>();
        for (M model : mList) {
            if (model.isSelected()) list.add(model);
        }
        return list;
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> list = new ArrayList<>();
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            M model = mList.get(i);
            if (model.isSelected()) {
                list.add(i);
            }
        }
        return list;
    }

    protected ViewHolderBinding getLoadingViewHolder(LayoutInflater layoutInflater,
            ViewGroup parent) {
        return null;
    }

    protected ViewHolderBinding getFailedViewHolder(LayoutInflater layoutInflater,
            ViewGroup parent) {
        return null;
    }

    public void setState(@State int state) {
        if (state == getState()) return;
        switch (state) {
            case State.LOADING:
                if (!mLoadingViewWillBeProvided) {
                    throw new IllegalStateException(
                            "Resource id for 'loading mState' should be provided or 'mLoadingViewWillBeProvided' should be set");
                }
                break;
            case State.FAILED:
                if (!mFailedViewWillBeProvided) {
                    throw new IllegalStateException(
                            "Resource id for 'failed mState' should be provided or 'mFailedViewWillBeProvided' should be set");
                }
                break;
            case State.EMPTY:
                if (!mEmptyViewWillBeProvided) {
                    throw new IllegalStateException(
                            "Resource id for 'empty mState' should be provided or 'mEmptyViewWillBeProvided' should be set");
                }
                break;
            default:
                break;
        }
        this.mState = state;
        notifyDataSetChanged();
    }

    public int getState() {
        return mState;
    }

    public boolean isItemViewWillBeProvided() {
        return mItemViewWillBeProvided;
    }

    public boolean isLoadingViewWillBeProvided() {
        return mLoadingViewWillBeProvided;
    }

    public boolean isFailedViewWillBeProvided() {
        return mFailedViewWillBeProvided;
    }

    public boolean isEmptyViewWillBeProvided() {
        return mEmptyViewWillBeProvided;
    }

    public void filterCurrentData(String filterText) {
        mFilterText = filterText.trim();
        mIsFilter = true;
        updateBindableData(new ArrayList<>(mOriginList));
    }
}
