package com.chuongvd.app.signal.ui.main.sample;

import android.os.Bundle;
import com.chuongvd.app.signal.R;
import com.chuongvd.app.signal.databinding.FragmentSampleBinding;
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment;

public class SampleFragment
        extends BaseDataBindingFragment<FragmentSampleBinding, SampleViewModel> {
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
    protected void initData() {
        if (getArguments() != null) {
            mBinding.setText(getArguments().getString(KEY_TEXT));
        }
    }

    @Override
    protected void subscribeToViewModel() {

    }
}
