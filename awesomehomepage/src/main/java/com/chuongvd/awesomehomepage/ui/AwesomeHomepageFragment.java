package com.chuongvd.awesomehomepage.ui;

import android.content.Context;
import com.chuongvd.awesomehomepage.widget.viewpager.ItemFragment;
import com.chuongvd.awesomehomepage.widget.viewpager.NavigationSupportPagerAdapter;

/**
 * Created on 9/2/18
 *
 * @author Chuongvd
 */
public class AwesomeHomepageFragment<ITEM_FRAGMENT extends ItemFragment> extends BaseAwesomeHomepageFragment<ITEM_FRAGMENT> {

    @Override
    public void initAdapter(NavigationSupportPagerAdapter<ITEM_FRAGMENT> adapter) {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    protected void initListeners() {

    }
}
