package com.chuongvd.app.signal.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableInt;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.chuongvd.app.signal.BasicApp;
import com.chuongvd.app.signal.R;
import com.chuongvd.app.signal.databinding.FragmentHomeBinding;
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment;
import com.chuongvd.app.signal.ui.main.sample.SampleFragment;
import com.chuongvd.app.signal.viewmodel.HomeViewModel;
import com.chuongvd.app.signal.widget.viewpager.ItemNavigationFragment;
import com.chuongvd.app.signal.widget.viewpager.NavigationSupportPagerAdapter;

public class MainFragment extends BaseDataBindingFragment<FragmentHomeBinding, HomeViewModel>
        implements AHBottomNavigation.OnTabSelectedListener {

    private NavigationSupportPagerAdapter<ItemNavigationFragment> mPagerAdapter;
    private ObservableInt itemSelected = new ObservableInt();

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        HomeViewModel.Factory factory = new HomeViewModel.Factory(BasicApp.self());
        mViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        mPagerAdapter = new NavigationSupportPagerAdapter<>(getChildFragmentManager());
        // TODO: 7/29/18 Init PagerAdapter
        String title1 = "Menu 1";
        mPagerAdapter.addFragment(new ItemNavigationFragment(title1, R.drawable.ic_bar_chart,
                SampleFragment.newInstance(title1)));
        String title2 = "Menu 2";
        mPagerAdapter.addFragment(new ItemNavigationFragment(title2, R.drawable.ic_bar_chart,
                SampleFragment.newInstance(title2)));
        String title3 = "Menu 3";
        mPagerAdapter.addFragment(new ItemNavigationFragment(title3, R.drawable.ic_bar_chart,
                SampleFragment.newInstance(title3)));

        mBinding.setItemSelected(itemSelected);
        mBinding.setPagerAdapter(mPagerAdapter);
        mPagerAdapter.setUpWithBottomNavigation(mBinding.bottomNavigation);
        mBinding.bottomNavigation.setOnTabSelectedListener(this);
        mBinding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }

    @Override
    protected void subscribeToViewModel() {

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (wasSelected) return true;
        itemSelected.set(position);
        return true;
    }
}
