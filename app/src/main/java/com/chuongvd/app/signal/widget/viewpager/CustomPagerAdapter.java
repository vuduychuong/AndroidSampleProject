package com.chuongvd.app.signal.widget.viewpager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    private List<ItemFragment> mItemFragmentList = new ArrayList<>();

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CustomPagerAdapter(FragmentManager fm, @NonNull List<ItemFragment> itemFragmentList) {
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

    public List<ItemFragment> getItemFragmentList() {
        return mItemFragmentList;
    }

    public int addFragment(ItemFragment itemFragment) {
        mItemFragmentList.add(itemFragment);
        notifyDataSetChanged();
        return mItemFragmentList.size() - 1;
    }
}
