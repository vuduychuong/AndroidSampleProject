package com.chuongvd.app.signal.data.source.local.datasource

import android.arch.lifecycle.LiveData
import com.chuongvd.app.signal.data.entity.UserEntity

interface IUserLocalDataSource {

  val user: LiveData<UserEntity>

  val accessToken: String?
  fun saveUser(user: UserEntity)

}
