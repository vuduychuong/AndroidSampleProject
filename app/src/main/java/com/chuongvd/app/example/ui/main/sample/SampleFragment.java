package com.chuongvd.app.example.ui.main.sample;

import android.content.Context;
import android.os.Bundle;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.databinding.FragmentSampleBinding;
import com.chuongvd.app.example.ui.common.DataBindingFragment;

public class SampleFragment
        extends DataBindingFragment<FragmentSampleBinding, SampleViewModel> {
    private static final String KEY_TEXT = "text";

    public static SampleFragment newInstance(String text) {
        Bundle data = new Bundle();
        data.putString(KEY_TEXT, text);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_sample;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            mBinding.setText(getArguments().getString(KEY_TEXT));
        }
    }

    @Override
    protected void subscribeToViewModel() {

    }
}
