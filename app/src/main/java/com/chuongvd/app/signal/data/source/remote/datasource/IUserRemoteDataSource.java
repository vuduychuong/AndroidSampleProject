package com.chuongvd.app.signal.data.source.remote.datasource;

import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.entity.UserEntity;
import io.reactivex.Observable;

public interface IUserRemoteDataSource {
    Observable<UserEntity> login(LoginRequest request);
}
