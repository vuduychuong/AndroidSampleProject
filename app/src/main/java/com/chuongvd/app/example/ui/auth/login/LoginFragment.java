package com.chuongvd.app.example.ui.auth.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.chuongvd.app.example.BuildConfig;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.data.source.remote.request.LoginRequest;
import com.chuongvd.app.example.data.source.remote.service.RequestStatus;
import com.chuongvd.app.example.data.source.repository.UserRepository;
import com.chuongvd.app.example.databinding.FragmentLoginBinding;
import com.chuongvd.app.example.ui.common.DataBindingFragment;
import com.chuongvd.app.example.ui.main.MainActivity;
import com.chuongvd.app.example.viewmodel.LoginViewModel;
import com.chuongvd.app.example.viewmodel.ViewModelFactory;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.Objects;

public class LoginFragment extends DataBindingFragment<FragmentLoginBinding, LoginViewModel>
        implements View.OnClickListener, FacebookCallback<LoginResult> {
    private CallbackManager mFacebookCallbackManager;
    private FacebookHandler mFacebookHandler;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initListeners() {
        // TODO: 3/12/19
    }

    @Override
    protected void initData() {
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance())
                .get(LoginViewModel.class);
        mBinding.setOnClickLogin(this);
        mFacebookHandler = new FacebookHandler(getContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, this);
    }

    @Override
    protected void subscribeToViewModel() {
        mViewModel.getUser().observe(this, response -> {
            if (response == null) return;
            if (response.status == RequestStatus.LOADING) return;
            if (response.data == null) return;
            if (TextUtils.isEmpty(response.data.getAccessToken())) return;
            UserRepository.getInstance().saveAccessToken(response.data.getAccessToken());
            goToMain();
        });
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
        //FIXME: remove commend code below if you need to setLoginRequest with facebook
        //        LoginManager.getInstance()
        //                .logInWithReadPermissions(this, mFacebookHandler.getFacebookPermission());
        goToMain();
    }

    /**
     * Callback setLoginRequest using facebook
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
            mViewModel.setLoginRequest(request);
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
