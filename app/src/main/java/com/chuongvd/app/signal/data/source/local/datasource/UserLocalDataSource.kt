package com.chuongvd.app.signal.data.source.local.datasource

import android.arch.lifecycle.LiveData
import com.chuongvd.app.signal.data.database.AppDatabase
import com.chuongvd.app.signal.data.entity.UserEntity

class UserLocalDataSource
//    private AppExecutors mAppExecutors;

private constructor(private val mDatabase: AppDatabase) : IUserLocalDataSource {

  override val user: LiveData<UserEntity>
    get() = mDatabase.userDao().loadUser()

  override val accessToken: String?
    get() = if (null == user.value) null else user.value!!.accessToken

  override fun saveUser(user: UserEntity) {
    mDatabase.userDao().insertUser(user)
  }

  companion object {
    private var sInstance: UserLocalDataSource? = null

    fun getInstance(database: AppDatabase): UserLocalDataSource {
      if (sInstance == null) {
        synchronized(UserLocalDataSource::class.java) {
          if (sInstance == null) {
            sInstance = UserLocalDataSource(database)
          }
        }
      }
      return this.sInstance!!
    }
  }
}
