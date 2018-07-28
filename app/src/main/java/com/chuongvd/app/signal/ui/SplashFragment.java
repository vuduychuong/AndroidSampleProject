package com.chuongvd.app.signal.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.text.TextUtils;
import com.chuongvd.app.signal.R;
import com.chuongvd.app.signal.data.entity.UserEntity;
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper;
import com.chuongvd.app.signal.databinding.FragmentSplashBinding;
import com.chuongvd.app.signal.ui.auth.login.LoginActivity;
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment;
import com.chuongvd.app.signal.ui.main.MainActivity;
import com.chuongvd.app.signal.viewmodel.SplashViewModel;
import java.util.Objects;

public class SplashFragment
        extends BaseDataBindingFragment<FragmentSplashBinding, SplashViewModel> {
    private static final long DELAY_TIME = 2000;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initData() {
        SplashViewModel.Factory factory =
                new SplashViewModel.Factory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
    }

    @Override
    protected void subscribeToViewModel() {
        mViewModel.getUser().observe(this, userEntity -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (null == userEntity) {
                        //Go to Auth flow
                        goToLogin();
                        return;
                    }
                    if (isValidUser(userEntity)) {
                        //Go to Main flow
                        RequestHelper.setAccessToken(userEntity.getAccessToken());
                        goToMain();
                    } else {
                        //Go to Auth flow
                        goToLogin();
                    }
                }
            }, DELAY_TIME);
        });
    }

    private boolean isValidUser(UserEntity userEntity) {
        return !TextUtils.isEmpty(userEntity.getAccessToken());
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
