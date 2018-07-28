package com.chuongvd.app.signal.data.source.remote.service;

import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.source.remote.response.BaseResponse;
import com.chuongvd.app.signal.data.entity.UserEntity;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RocketApi {
    @POST("api/v1/signIn")
    Observable<BaseResponse<UserEntity>> login(@Body LoginRequest request);

    @GET("api/v1/signals")
    Observable<BaseResponse<List<SignalEntity>>> getSignals();
}
