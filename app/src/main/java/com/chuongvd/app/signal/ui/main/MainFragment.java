package com.chuongvd.app.signal.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableInt;
import android.view.View;
import com.chuongvd.app.signal.R;
import com.chuongvd.app.signal.constant.BottomNavigation;
import com.chuongvd.app.signal.databinding.FragmentHomeBinding;
import com.chuongvd.app.signal.listener.BottomNavigationListener;
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment;
import com.chuongvd.app.signal.viewmodel.HomeViewModel;
import com.chuongvd.app.signal.widget.viewpager.CustomPagerAdapter;

public class MainFragment extends BaseDataBindingFragment<FragmentHomeBinding, HomeViewModel>
        implements BottomNavigationListener {

    private CustomPagerAdapter mPagerAdapter;
    private ObservableInt itemSelected = new ObservableInt();

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        HomeViewModel.Factory factory = new HomeViewModel.Factory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        mPagerAdapter = new CustomPagerAdapter(getChildFragmentManager());
        // TODO: 7/29/18 Init PagerAdapter
        mBinding.setBottomNavigationListener(this);
        mBinding.setItemSelected(itemSelected);
        mBinding.setPagerAdapter(mPagerAdapter);
    }

    @Override
    protected void subscribeToViewModel() {

    }

    @Override
    public void onClickHome(View v) {
        if (itemSelected.get() == BottomNavigation.SIGNAL) return;
        itemSelected.set(BottomNavigation.SIGNAL);
    }

    @Override
    public void onClickChart(View v) {
        if (itemSelected.get() == BottomNavigation.CHART) return;
        itemSelected.set(BottomNavigation.CHART);
    }

    @Override
    public void onClickInfo(View v) {
        if (itemSelected.get() == BottomNavigation.INFOMATION) return;
        itemSelected.set(BottomNavigation.INFOMATION);
    }

    @Override
    public void onClickProfile(View v) {
        if (itemSelected.get() == BottomNavigation.PROFILE) return;
        itemSelected.set(BottomNavigation.PROFILE);
    }
}
