package com.chuongvd.app.example.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.databinding.FragmentSplashBinding;
import com.chuongvd.app.example.ui.auth.login.LoginActivity;
import com.chuongvd.app.example.ui.common.DataBindingFragment;
import com.chuongvd.app.example.ui.main.MainActivity;
import com.chuongvd.app.example.viewmodel.SplashViewModel;
import com.chuongvd.app.example.viewmodel.ViewModelFactory;
import java.util.Objects;

public class SplashFragment extends DataBindingFragment<FragmentSplashBinding, SplashViewModel> {
    private static final long DELAY_TIME = 2000;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initListeners() {
        // TODO: 3/12/19
    }

    @Override
    protected void initData() {
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance())
                .get(SplashViewModel.class);
        new Handler().postDelayed(() -> {
            goToMain();
            //            if (mViewModel.isValidUser()) {
            //                goToMain();
            //            } else {
            //                goToLogin();
            //            }
        }, DELAY_TIME);
    }

    @Override
    protected void subscribeToViewModel() {
        // TODO: 12/1/18
    }

    private void goToLogin() {
        startActivity(LoginActivity.newIntance(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    private void goToMain() {
        startActivity(MainActivity.newIntance(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }
}
