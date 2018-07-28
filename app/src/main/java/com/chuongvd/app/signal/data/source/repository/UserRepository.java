package com.chuongvd.app.signal.data.source.repository;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.signal.data.source.local.datasource.IUserLocalDataSource;
import com.chuongvd.app.signal.data.source.local.datasource.UserLocalDataSource;
import com.chuongvd.app.signal.data.source.remote.datasource.IUserRemoteDataSource;
import com.chuongvd.app.signal.data.source.remote.datasource.UserRemoteDataSource;
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.database.AppDatabase;
import com.chuongvd.app.signal.data.entity.UserEntity;
import io.reactivex.Observable;

public class UserRepository implements IUserRemoteDataSource, IUserLocalDataSource {

    private static UserRepository sInstance;
    private IUserRemoteDataSource mUserRemoteDataSource;
    private IUserLocalDataSource mUserLocalDataSource;

    private UserRepository(UserRemoteDataSource userRemoteDataSource,
            UserLocalDataSource userLocalDataSource) {
        mUserRemoteDataSource = userRemoteDataSource;
        mUserLocalDataSource = userLocalDataSource;
    }

    public static UserRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (UserRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserRepository(UserRemoteDataSource.getInstance(),
                            UserLocalDataSource.getInstance(database));
                }
            }
        }
        return sInstance;
    }

    @Override
    public Observable<UserEntity> login(LoginRequest request) {
        return mUserRemoteDataSource.login(request);
    }

    @Override
    public void saveUser(UserEntity user) {
        mUserLocalDataSource.saveUser(user);
    }

    @Override
    public LiveData<UserEntity> getUser() {
        return mUserLocalDataSource.getUser();
    }

    @Override
    public String getAccessToken() {
        return mUserLocalDataSource.getAccessToken();
    }
}
