package com.chuongvd.app.signal.data.source.repository

import android.arch.lifecycle.LiveData
import com.chuongvd.app.signal.data.database.AppDatabase
import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.local.datasource.IUserLocalDataSource
import com.chuongvd.app.signal.data.source.local.datasource.UserLocalDataSource
import com.chuongvd.app.signal.data.source.remote.datasource.IUserRemoteDataSource
import com.chuongvd.app.signal.data.source.remote.datasource.UserRemoteDataSource
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest
import io.reactivex.Observable

class UserRepository private constructor(userRemoteDataSource: UserRemoteDataSource,
    userLocalDataSource: UserLocalDataSource) : IUserRemoteDataSource, IUserLocalDataSource {
  private val mUserRemoteDataSource: IUserRemoteDataSource
  private val mUserLocalDataSource: IUserLocalDataSource

  override val user: LiveData<UserEntity>
    get() = mUserLocalDataSource.user

  override val accessToken: String?
    get() = mUserLocalDataSource.accessToken

  init {
    mUserRemoteDataSource = userRemoteDataSource
    mUserLocalDataSource = userLocalDataSource
  }

  override fun login(request: LoginRequest): Observable<UserEntity> {
    return mUserRemoteDataSource.login(request)
  }

  override fun saveUser(user: UserEntity) {
    mUserLocalDataSource.saveUser(user)
  }

  companion object {

    private var sInstance: UserRepository? = null

    fun getInstance(database: AppDatabase): UserRepository {
      if (sInstance == null) {
        synchronized(UserRepository::class.java) {
          if (sInstance == null) {
            sInstance = UserRepository(UserRemoteDataSource.instance,
                UserLocalDataSource.getInstance(database))
          }
        }
      }
      return this.sInstance!!
    }
  }
}
