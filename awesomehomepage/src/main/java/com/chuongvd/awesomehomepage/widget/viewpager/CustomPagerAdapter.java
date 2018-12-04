package com.chuongvd.awesomehomepage.widget.viewpager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter<ITEM_FRAGMENT extends ItemFragment>
        extends FragmentStatePagerAdapter {

    private List<ITEM_FRAGMENT> mItemFragmentList = new ArrayList<>();

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CustomPagerAdapter(FragmentManager fm, @NonNull List<ITEM_FRAGMENT> itemFragmentList) {
        super(fm);
        mItemFragmentList.clear();
        mItemFragmentList.addAll(itemFragmentList);
    }

    @Override
    public Fragment getItem(int position) {
        return mItemFragmentList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mItemFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mItemFragmentList.get(position).getTitle();
    }

    public List<ITEM_FRAGMENT> getItemFragmentList() {
        return mItemFragmentList;
    }

    public int addFragment(ITEM_FRAGMENT itemFragment) {
        mItemFragmentList.add(itemFragment);
        notifyDataSetChanged();
        return mItemFragmentList.size() - 1;
    }
}
