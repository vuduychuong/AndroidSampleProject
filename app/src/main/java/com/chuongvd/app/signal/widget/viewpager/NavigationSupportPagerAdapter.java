package com.chuongvd.app.signal.widget.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import java.util.List;

public class NavigationSupportPagerAdapter<ITEM_FRAGMENT extends ItemFragment>
        extends CustomPagerAdapter<ITEM_FRAGMENT> {
    private CustomBottomNavigation mCustomBottomNavigation;

    public NavigationSupportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public NavigationSupportPagerAdapter(FragmentManager fm,
            @NonNull List<ITEM_FRAGMENT> item_fragments) {
        super(fm, item_fragments);
    }

    public void setUpWithBottomNavigation(CustomBottomNavigation bottomNavigation) {
        mCustomBottomNavigation = bottomNavigation;
        refreshBottomNavigation(bottomNavigation);
    }

    private void refreshBottomNavigation(CustomBottomNavigation bottomNavigation) {
        if (bottomNavigation == null) return;
        for (ITEM_FRAGMENT item : getItemFragmentList()) {
            if (item instanceof ItemNavigationFragment) {
                bottomNavigation.addItem(((ItemNavigationFragment) item).getNavigationItem());
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        refreshBottomNavigation(mCustomBottomNavigation);
    }
}
