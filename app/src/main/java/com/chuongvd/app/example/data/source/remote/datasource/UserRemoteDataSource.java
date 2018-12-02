package com.chuongvd.app.example.data.source.remote.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.chuongvd.app.example.data.entity.UserEntity;
import com.chuongvd.app.example.data.source.remote.request.LoginRequest;
import com.chuongvd.app.example.data.source.remote.service.ApiResponse;
import com.chuongvd.app.example.data.source.remote.service.RequestHelper;

public class UserRemoteDataSource {
    private static UserRemoteDataSource sInstance;

    private UserRemoteDataSource() {

    }

    public static UserRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UserRemoteDataSource();
        }
        return sInstance;
    }

    public LiveData<ApiResponse<UserEntity>> login(LoginRequest loginRequest) {
        return Transformations.switchMap(RequestHelper.getRequest().login(loginRequest),
                ApiResponse::convertData);
    }
}
