package com.chuongvd.app.signal.data.source.remote.service

import com.chuongvd.app.signal.data.entity.SignalEntity
import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest
import com.chuongvd.app.signal.data.source.remote.response.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RocketApi {

  @get:GET("api/v1/signals")
  val signals: Observable<BaseResponse<List<SignalEntity>>>

  @POST("api/v1/signIn")
  fun login(@Body request: LoginRequest): Observable<BaseResponse<UserEntity>>
}
