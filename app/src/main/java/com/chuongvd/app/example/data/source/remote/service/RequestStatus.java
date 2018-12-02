package com.chuongvd.app.example.data.source.remote.service;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.ERROR;
import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.FIRST_LOADING;
import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.LOADING;
import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.SUCCESS;
import static java.lang.annotation.RetentionPolicy.SOURCE;

//
// Created by chuongvd on 9/30/18.
//

@Retention(SOURCE)
@IntDef({ SUCCESS, ERROR, LOADING, FIRST_LOADING })
public @interface RequestStatus {
    int SUCCESS = 1;
    int ERROR = 2;
    int LOADING = 3;
    int FIRST_LOADING = 4;
}