package com.chuongvd.support.adapterbinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class ViewHolderBinding<BINDINGVIEW extends ViewDataBinding, MODEL>
        extends RecyclerView.ViewHolder {

    public BINDINGVIEW mBinding;
    public AdapterBinding.OnRecyclerItemListener<MODEL> mListener;
    protected MODEL data;

    public ViewHolderBinding(BINDINGVIEW binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public ViewHolderBinding(BINDINGVIEW binding,
            AdapterBinding.OnRecyclerItemListener<MODEL> listener) {
        super(binding.getRoot());
        mBinding = binding;
        mListener = listener;
    }

    public void bindData(MODEL model) {
        data = model;
        mBinding.getRoot().setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), data);
            }
        });
    }
}
