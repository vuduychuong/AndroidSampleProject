package com.chuongvd.app.example.data.source.repository;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.example.AppExecutors;
import com.chuongvd.app.example.data.database.AppDatabase;
import com.chuongvd.app.example.data.entity.UserEntity;
import com.chuongvd.app.example.data.source.local.datasource.UserLocalDataSource;
import com.chuongvd.app.example.data.source.remote.datasource.UserRemoteDataSource;
import com.chuongvd.app.example.data.source.remote.request.LoginRequest;
import com.chuongvd.app.example.data.source.remote.service.ApiResponse;
import com.chuongvd.app.example.data.source.remote.service.NetworkBoundResource;
import com.chuongvd.app.example.data.source.remote.service.Resource;

public class UserRepository {

    private static UserRepository sInstance;
    private UserRemoteDataSource mUserRemoteDataSource;
    private UserLocalDataSource mUserLocalDataSource;
    private final AppExecutors mAppExecutors;

    private UserRepository(UserRemoteDataSource userRemoteDataSource,
            UserLocalDataSource userLocalDataSource) {
        mUserRemoteDataSource = userRemoteDataSource;
        mUserLocalDataSource = userLocalDataSource;
        mAppExecutors = AppExecutors.getInstance();
    }

    public static UserRepository getInstance() {
        if (sInstance == null) {
            synchronized (UserRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserRepository(UserRemoteDataSource.getInstance(),
                            UserLocalDataSource.getInstance(AppDatabase.getInstance()));
                }
            }
        }
        return sInstance;
    }

    public LiveData<Resource<UserEntity>> login(LoginRequest request) {
        return new NetworkBoundResource<UserEntity, UserEntity>(mAppExecutors, true) {
            @Override
            protected void saveCallResult(UserEntity userEntity) {

            }

            @Override
            protected LiveData<ApiResponse<UserEntity>> createCall() {
                return null;
            }

            @Override
            protected boolean shouldFetchData(UserEntity userEntity) {
                return false;
            }

            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return null;
            }
        }.asLiveData();
    }

    public void saveUser(UserEntity user) {
        mUserLocalDataSource.saveUser(user);
    }

    public UserEntity getUser() {
        return mUserLocalDataSource.getUser();
    }

    public String getAccessToken() {
        return mUserLocalDataSource.getAccessToken();
    }

    public void saveAccessToken(String accessToken) {
        mUserLocalDataSource.saveAccessToken(accessToken);
    }
}
