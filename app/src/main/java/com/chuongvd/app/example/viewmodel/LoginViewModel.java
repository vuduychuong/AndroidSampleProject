package com.chuongvd.app.example.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import com.chuongvd.app.example.data.entity.UserEntity;
import com.chuongvd.app.example.data.source.remote.request.LoginRequest;
import com.chuongvd.app.example.data.source.remote.service.AbsentLiveData;
import com.chuongvd.app.example.data.source.remote.service.Resource;
import com.chuongvd.app.example.data.source.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;
    private final LiveData<Resource<UserEntity>> mUser;
    private final MutableLiveData<LoginRequest> mLoginRequest = new MutableLiveData<>();

    LoginViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance();
        mUser = Transformations.switchMap(mLoginRequest, query -> {
            if (query == null) return new AbsentLiveData<>();
            return mUserRepository.login(query);
        });
    }

    public void setLoginRequest(LoginRequest request) {
        mLoginRequest.setValue(request);
    }

    public LiveData<Resource<UserEntity>> getUser() {
        return mUser;
    }
}
