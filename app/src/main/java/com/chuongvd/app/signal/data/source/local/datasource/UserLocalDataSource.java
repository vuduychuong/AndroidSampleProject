package com.chuongvd.app.signal.data.source.local.datasource;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.signal.AppExecutors;
import com.chuongvd.app.signal.data.database.AppDatabase;
import com.chuongvd.app.signal.data.entity.UserEntity;

public class UserLocalDataSource implements IUserLocalDataSource {
    private static UserLocalDataSource sInstance;

    private final AppDatabase mDatabase;
//    private AppExecutors mAppExecutors;

    private UserLocalDataSource(AppDatabase database) {
        mDatabase = database;
    }

    public static UserLocalDataSource getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (UserLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new UserLocalDataSource(database);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void saveUser(UserEntity user) {
        mDatabase.userDao().insertUser(user);
    }

    @Override
    public LiveData<UserEntity> getUser() {
        return mDatabase.userDao().loadUser();
    }

    @Override
    public String getAccessToken() {
        if (null == getUser().getValue()) return null;
        return getUser().getValue().getAccessToken();
    }
}
