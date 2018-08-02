package com.chuongvd.app.signal.data.source.remote.datasource

import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest
import io.reactivex.Observable

interface IUserRemoteDataSource {
  fun login(request: LoginRequest): Observable<UserEntity>
}
