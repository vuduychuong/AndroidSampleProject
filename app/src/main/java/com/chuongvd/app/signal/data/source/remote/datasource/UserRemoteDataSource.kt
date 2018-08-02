package com.chuongvd.app.signal.data.source.remote.datasource

import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper
import com.chuongvd.app.signal.data.source.remote.service.rx.SchedulerUtils
import io.reactivex.Observable

class UserRemoteDataSource private constructor() : IUserRemoteDataSource {

  override fun login(loginRequest: LoginRequest): Observable<UserEntity> {
    return RequestHelper.request
        .login(loginRequest)
        .flatMap(SchedulerUtils.convertData<UserEntity>())
        .compose(SchedulerUtils.applyObservableAsync<UserEntity>())
  }

  companion object {
    private var sInstance: UserRemoteDataSource? = null

    val instance: UserRemoteDataSource
      get() {
        if (sInstance == null) {
          sInstance = UserRemoteDataSource()
        }
        return this.sInstance!!
      }
  }
}
