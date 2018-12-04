package com.chuongvd.app.example.ui.main;

import android.content.Context;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.ui.main.sample.SampleFragment;
import com.chuongvd.awesomehomepage.ui.BaseAwesomeHomepageFragment;
import com.chuongvd.awesomehomepage.widget.viewpager.CustomBottomNavigation;
import com.chuongvd.awesomehomepage.widget.viewpager.ItemNavigationFragment;
import com.chuongvd.awesomehomepage.widget.viewpager.NavigationSupportPagerAdapter;

public class MainFragment extends BaseAwesomeHomepageFragment<ItemNavigationFragment> {

    @Override
    public void initAdapter(NavigationSupportPagerAdapter<ItemNavigationFragment> adapter) {
        String title1 = "Item 1";
        adapter.addFragment(new ItemNavigationFragment(title1, R.drawable.ic_radio,
                SampleFragment.newInstance(title1)));
        String title2 = "Item 2";
        adapter.addFragment(new ItemNavigationFragment(title2, R.drawable.ic_radio,
                SampleFragment.newInstance(title2)));
        String title3 = "Item 3";
        adapter.addFragment(new ItemNavigationFragment(title3, R.drawable.ic_radio,
                SampleFragment.newInstance(title3)));
        String title4 = "Item 4";
        adapter.addFragment(new ItemNavigationFragment(title4, R.drawable.ic_radio,
                SampleFragment.newInstance(title4)));
        String title5 = "Item 5";
        adapter.addFragment(new ItemNavigationFragment(title5, R.drawable.ic_radio,
                SampleFragment.newInstance(title5)));
    }

    @Override
    protected void stylingBottomNavigation(CustomBottomNavigation bottomNavigation) {
        super.stylingBottomNavigation(bottomNavigation);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        mCustomBottomNavigation.setNotification("", position);
        return super.onTabSelected(position, wasSelected);
    }

    @Override
    public Context context() {
        return getContext();
    }
}
