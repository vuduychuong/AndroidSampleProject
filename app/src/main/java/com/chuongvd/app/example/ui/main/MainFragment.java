package com.chuongvd.app.example.ui.main;

import android.content.Context;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.databinding.FragmentMainBinding;
import com.chuongvd.app.example.ui.common.DataBindingFragment;
import com.chuongvd.app.example.viewmodel.MainViewModel;

public class MainFragment extends DataBindingFragment<FragmentMainBinding, MainViewModel> {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    protected void initListeners() {
        // TODO: 3/12/19
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void subscribeToViewModel() {

    }
}
