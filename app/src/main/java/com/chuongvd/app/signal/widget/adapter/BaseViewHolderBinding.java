package com.chuongvd.app.signal.widget.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BaseViewHolderBinding<BINDINGVIEW extends ViewDataBinding, MODEL>
        extends RecyclerView.ViewHolder {

    public BINDINGVIEW mBinding;
    public BaseRecyclerViewAdapterBinding.OnRecyclerItemListener<MODEL> mListener;
    private MODEL data;

    public BaseViewHolderBinding(BINDINGVIEW binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public BaseViewHolderBinding(BINDINGVIEW binding,
            BaseRecyclerViewAdapterBinding.OnRecyclerItemListener<MODEL> listener) {
        super(binding.getRoot());
        mBinding = binding;
        mListener = listener;
    }

    public void bindData(MODEL MODEL) {
        data = MODEL;
        mBinding.getRoot().setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), data);
            }
        });
    }
}
