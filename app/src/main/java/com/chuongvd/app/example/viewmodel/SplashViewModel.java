package com.chuongvd.app.example.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.chuongvd.app.example.data.entity.UserEntity;
import com.chuongvd.app.example.data.source.repository.UserRepository;

public class SplashViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance();
    }

    public boolean isValidUser() {
        UserEntity userEntity = mUserRepository.getUser();
        if (userEntity == null) return false;
        return !TextUtils.isEmpty(userEntity.getAccessToken());
    }
}
