package com.chuongvd.app.signal.data.source.local.datasource;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.signal.data.entity.UserEntity;

public interface IUserLocalDataSource {
    void saveUser(UserEntity user);

    LiveData<UserEntity> getUser();

    String getAccessToken();

}
