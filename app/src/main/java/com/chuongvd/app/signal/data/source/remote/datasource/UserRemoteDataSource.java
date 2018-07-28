package com.chuongvd.app.signal.data.source.remote.datasource;

import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper;
import com.chuongvd.app.signal.data.source.remote.service.rx.SchedulerUtils;
import com.chuongvd.app.signal.data.entity.UserEntity;
import io.reactivex.Observable;

public class UserRemoteDataSource implements IUserRemoteDataSource {
    private static UserRemoteDataSource sInstance;

    private UserRemoteDataSource() {

    }

    public static UserRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new UserRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Observable<UserEntity> login(LoginRequest loginRequest) {
        return RequestHelper.getRequest()
                .login(loginRequest)
                .flatMap(SchedulerUtils.convertData())
                .compose(SchedulerUtils.applyObservableAsync());
    }
}
