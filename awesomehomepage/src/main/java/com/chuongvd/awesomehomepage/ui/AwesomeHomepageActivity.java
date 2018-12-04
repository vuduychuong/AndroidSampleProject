package com.chuongvd.awesomehomepage.ui;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import com.chuongvd.awesomehomepage.R;
import com.chuongvd.awesomehomepage.common.BaseDataBindingActivity;
import com.chuongvd.awesomehomepage.databinding.ActivityHomeBinding;
import com.chuongvd.awesomehomepage.listener.DrawerSupport;
import com.chuongvd.awesomehomepage.viewmodel.HomeActivityViewModel;

/**
 * Created on 9/2/18
 *
 * @author Chuongvd
 */
public abstract class AwesomeHomepageActivity
        extends BaseDataBindingActivity<ViewDataBinding, HomeActivityViewModel>
        implements DrawerSupport, NavigationView.OnNavigationItemSelectedListener {
    protected ActivityHomeBinding binding;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initData() {
        binding = (ActivityHomeBinding) mBinding;
        setEnableDrawer(isEnableDrawer());
        //
        //        AwesomeHomepageFragment fragment = new AwesomeHomepageFragment();
        //        getSupportFragmentManager().beginTransaction()
        //                .add(R.id.container, fragment, AwesomeHomepageFragment.class.getName())
        //                .commit();
        //        binding.drawerNavigation
    }

    protected int getNavigationMenu() {
        return 0;
    }

    protected int getNavigationHeader() {
        return 0;
    }

    protected abstract boolean isEnableDrawer();

    protected void stylingHeaderView(ViewDataBinding headerView) {
        // TODO: 9/2/18
    }

    protected void stylingNavigationView(NavigationView drawerNavigation) {
        // TODO: 9/2/18
    }

    protected void setEnableDrawer(boolean enable) {
        binding.drawer.setDrawerLockMode(
                enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (enable) {
            inflateNavigationMenu();
        } else {
            removeNavigationMenu();
        }
    }

    private void removeNavigationMenu() {
        binding.drawerNavigation.removeAllViews();
    }

    private void inflateNavigationMenu() {
        if (getNavigationHeader() != 0) {
            try {
                ViewDataBinding headerBinding =
                        DataBindingUtil.inflate(getLayoutInflater(), getNavigationHeader(), null,
                                false);
                binding.drawerNavigation.addHeaderView(headerBinding.getRoot());
                stylingHeaderView(headerBinding);
            } catch (Resources.NotFoundException e) {
                Log.w("", e);
            }
        }
        if (getNavigationMenu() == 0) {
            throw new UnsupportedOperationException(
                    "You need to implement and set resource for getNavigationMenu() if you set enableDrawer");
        }
        try {
            binding.drawerNavigation.inflateMenu(getNavigationMenu());
            binding.drawerNavigation.setNavigationItemSelectedListener(this);
            stylingNavigationView(binding.drawerNavigation);
        } catch (Resources.NotFoundException e) {
            if (isEnableDrawer()) {
                throw e;
            }
        }
    }

    @Override
    public void toggleDrawer() {
        if (binding.drawer.isDrawerOpen(Gravity.START)) {
            binding.drawer.closeDrawers();
        } else {
            binding.drawer.openDrawer(Gravity.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
