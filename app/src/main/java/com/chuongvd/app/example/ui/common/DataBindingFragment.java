package com.chuongvd.app.example.ui.common;

import android.arch.lifecycle.AndroidViewModel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.utils.NetworkUtils;
import com.chuongvd.awesomehomepage.common.BaseDataBindingFragment;

public abstract class DataBindingFragment<B extends ViewDataBinding, V extends AndroidViewModel>
        extends BaseDataBindingFragment<B, V> {
    protected BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onConnectionChanged(NetworkUtils.isConnectingToInternet(context));
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context().registerReceiver(mNetworkReceiver, filter);
    }

    @Override
    public void onDestroy() {
        if (mNetworkReceiver != null) {
            context().unregisterReceiver(mNetworkReceiver);
        }
        super.onDestroy();
    }

    /**
     * Handler when network connection changed
     * TODO: Change with {@link ConnectivityManager#registerNetworkCallback}
     */
    @Deprecated
    protected void onConnectionChanged(boolean connectingToInternet) {
        // TODO: 10/20/18
    }

    protected boolean isConnectNetwork() {
        if (context() == null) return false;
        if (NetworkUtils.isConnectingToInternet(context())) return true;
        Toast.makeText(context(), R.string.error_network, Toast.LENGTH_SHORT).show();
        return false;
    }
}
