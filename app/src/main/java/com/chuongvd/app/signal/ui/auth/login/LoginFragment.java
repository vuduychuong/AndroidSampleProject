package com.chuongvd.app.signal.ui.auth.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.chuongvd.app.signal.BuildConfig;
import com.chuongvd.app.signal.R;
import com.chuongvd.app.signal.data.entity.UserEntity;
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper;
import com.chuongvd.app.signal.databinding.FragmentLoginBinding;
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment;
import com.chuongvd.app.signal.ui.main.MainActivity;
import com.chuongvd.app.signal.viewmodel.LoginViewModel;
import java.util.Objects;

public class LoginFragment extends BaseDataBindingFragment<FragmentLoginBinding, LoginViewModel>
        implements View.OnClickListener, FacebookCallback<LoginResult> {
    private CallbackManager mFacebookCallbackManager;
    private FacebookHandler mFacebookHandler;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
        LoginViewModel.Factory factory = new LoginViewModel.Factory(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        mBinding.setOnClickLogin(this);
        mFacebookHandler = new FacebookHandler(getContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, this);
    }

    @Override
    protected void subscribeToViewModel() {
        mViewModel.getUser().observe(this, userEntity -> {
            //Check access Token
            if (null == userEntity) return;
            if (isValidUser(userEntity)) {
                //Go to Main flow
                RequestHelper.setAccessToken(userEntity.getAccessToken());
                goToMain();
            }
        });
    }

    private boolean isValidUser(UserEntity userEntity) {
        return null != userEntity.getAccessToken();
    }

    private void goToMain() {
        startActivity(MainActivity.newIntance(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        //FIXME: remove commend code below if you need to login with facebook
        //        LoginManager.getInstance()
        //                .logInWithReadPermissions(this, mFacebookHandler.getFacebookPermission());
        goToMain();
    }

    /**
     * Callback login using facebook
     */
    @Override
    public void onSuccess(LoginResult loginResult) {
        if (mFacebookHandler == null || loginResult == null) return;
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            String token;
            if (!task.isSuccessful()) {
                token = "";
            } else {
                token = task.getResult().getToken();
            }
            LoginRequest request =
                    new LoginRequest(BuildConfig.VERSION_NAME, BuildConfig.OS_TYPE, token,
                            loginResult.getAccessToken().getToken(), "FACEBOOK");
            mViewModel.login(request);
        });
    }

    @Override
    public void onCancel() {
        logoutFacebook();
    }

    @Override
    public void onError(FacebookException error) {
        logoutFacebook();
    }

    private void logoutFacebook() {
        LoginManager.getInstance().logOut();
    }
}
