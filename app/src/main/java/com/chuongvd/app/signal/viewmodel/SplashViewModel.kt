package com.chuongvd.app.signal.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.chuongvd.app.signal.BasicApp
import com.chuongvd.app.signal.data.database.AppDatabase
import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.repository.UserRepository

class SplashViewModel(application: Application,
    private val mUserRepository: UserRepository) : AndroidViewModel(application) {
  val user: LiveData<UserEntity>

  init {
    user = mUserRepository.user
  }

  class Factory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

      return SplashViewModel(mApplication,
          UserRepository.getInstance(AppDatabase.getInstance(BasicApp.self))) as T
    }
  }
}
