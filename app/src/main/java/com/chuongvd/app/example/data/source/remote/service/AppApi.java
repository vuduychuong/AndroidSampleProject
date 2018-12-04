package com.chuongvd.app.example.data.source.remote.service;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.example.data.entity.UserEntity;
import com.chuongvd.app.example.data.source.remote.request.LoginRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AppApi {
    @POST("api/v1/signIn")
    LiveData<ApiResponse<BaseResponse<UserEntity>>> login(@Body LoginRequest request);
}
