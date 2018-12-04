package com.chuongvd.app.example.data.source.local.datasource;

import com.chuongvd.app.example.constant.Constants;
import com.chuongvd.app.example.data.database.AppDatabase;
import com.chuongvd.app.example.data.entity.UserEntity;
import com.chuongvd.app.example.data.source.local.PrefHelpers;

public class UserLocalDataSource {
    private static UserLocalDataSource sInstance;

    private final AppDatabase mDatabase;

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

    public UserEntity getUser() {
        return PrefHelpers.self().get(Constants.SharePref.USER, UserEntity.class);
    }

    public void saveUser(UserEntity profileInfo) {
        PrefHelpers.self().put(Constants.SharePref.USER, profileInfo);
    }

    public String getAccessToken() {
        return PrefHelpers.self().get(Constants.SharePref.ACCESS_TOKEN, String.class);
    }

    public void saveAccessToken(String token) {
        PrefHelpers.self().put(Constants.SharePref.ACCESS_TOKEN, token);
    }
}
