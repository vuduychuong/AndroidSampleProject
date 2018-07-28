package com.chuongvd.app.signal.ui.common;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDataBindingFragment<B extends ViewDataBinding, V extends AndroidViewModel>
        extends Fragment {

    public B mBinding;

    protected V mViewModel;

    public abstract int getContentViewLayoutId();

    protected abstract void initData();

    protected abstract void subscribeToViewModel();

    //check is first show fragment
    public boolean isFirstLoad = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getContentViewLayoutId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        subscribeToViewModel();
    }

    public void onCreateFragment() {

    }
}
