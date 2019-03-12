package com.chuongvd.awesomehomepage.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.chuongvd.awesomehomepage.R;
import com.chuongvd.awesomehomepage.common.BaseDataBindingFragment;
import com.chuongvd.awesomehomepage.databinding.FragmentAwesomeHomepageBinding;
import com.chuongvd.awesomehomepage.viewmodel.AwesomeHomepageViewModel;
import com.chuongvd.awesomehomepage.widget.viewpager.CustomBottomNavigation;
import com.chuongvd.awesomehomepage.widget.viewpager.ItemFragment;
import com.chuongvd.awesomehomepage.widget.viewpager.ItemNavigationFragment;
import com.chuongvd.awesomehomepage.widget.viewpager.NavigationSupportPagerAdapter;

/**
 * Created on 9/2/18
 *
 * @author Chuongvd
 */
public abstract class BaseAwesomeHomepageFragment<ITEM_FRAGMENT extends ItemFragment>
        extends BaseDataBindingFragment<ViewDataBinding, AwesomeHomepageViewModel>
        implements AHBottomNavigation.OnTabSelectedListener {
    protected static final String ACTION_CHANGE_BADGE = "action_change_badge";
    protected static final String BADGE_POSITION = "badge_position";
    protected static final String BADGE_VALUE = "badge_value";

    protected CustomBottomNavigation mCustomBottomNavigation;

    protected NavigationSupportPagerAdapter<ITEM_FRAGMENT> mPagerAdapter;
    protected ObservableInt itemSelected = new ObservableInt();
    protected BroadcastReceiver mReceiver;
    protected IntentFilter mIntentFilter;
    protected FragmentAwesomeHomepageBinding binding;

    public static void sendBroadcastChangeBadge(Context context, int position, String value) {
        Intent intent = new Intent(ACTION_CHANGE_BADGE);
        intent.putExtra(BADGE_POSITION, position);
        intent.putExtra(BADGE_VALUE, value);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public abstract void initAdapter(NavigationSupportPagerAdapter<ITEM_FRAGMENT> adapter);

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_awesome_homepage;
    }

    @Override
    protected void initData() {
        mPagerAdapter = new NavigationSupportPagerAdapter<>(getChildFragmentManager());
        initAdapter(mPagerAdapter);
        binding = (FragmentAwesomeHomepageBinding) mBinding;
        binding.setItemSelected(itemSelected);
        binding.setPagerAdapter(mPagerAdapter);
        mPagerAdapter.setUpWithBottomNavigation(binding.bottomNavigation);
        binding.bottomNavigation.setOnTabSelectedListener(this);
        binding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        stylingBottomNavigation(binding.bottomNavigation);
        mIntentFilter = new IntentFilter(ACTION_CHANGE_BADGE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (binding == null) return;
                if (intent == null) return;
                int position = intent.getIntExtra(BADGE_POSITION, -1);
                String value = intent.getStringExtra(BADGE_VALUE);
                if (position == -1 || position > binding.bottomNavigation.getItemsCount()) {
                    return;
                }
                try {
                    int intValue = Integer.parseInt(value);
                    if (intValue > 9) value = "9+";
                } catch (NumberFormatException ignored) {

                }
                binding.bottomNavigation.setNotification(value, position);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() == null) return;
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mReceiver == null || getContext() == null) return;
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }

    protected void stylingBottomNavigation(CustomBottomNavigation bottomNavigation) {
        mCustomBottomNavigation = bottomNavigation;
        /*
        Modifier if you need
         */
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

    public NavigationSupportPagerAdapter<ITEM_FRAGMENT> getPagerAdapter() {
        return mPagerAdapter;
    }

    public ObservableInt getItemSelected() {
        return itemSelected;
    }
}
