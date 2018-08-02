package com.chuongvd.app.signal.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.chuongvd.app.signal.AppExecutors
import com.chuongvd.app.signal.BasicApp
import com.chuongvd.app.signal.data.database.AppDatabase
import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest
import com.chuongvd.app.signal.data.source.remote.service.BaseException
import com.chuongvd.app.signal.data.source.remote.service.rx.RocketSubscriber
import com.chuongvd.app.signal.data.source.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel internal constructor(application: Application,
    private val mUserRepository: UserRepository) : AndroidViewModel(application) {
  val user: LiveData<UserEntity>
  private val mCompositeDisposable: CompositeDisposable

  init {
    mCompositeDisposable = CompositeDisposable()
    user = mUserRepository.user
  }

  fun login(request: LoginRequest) {
    val disposable = mUserRepository.login(
        request).subscribeWith(object : RocketSubscriber<UserEntity>() {
      override fun onSuccess(userEntity: UserEntity?) {
        if (userEntity == null) return
        Log.d("Login", userEntity.accessToken)
        AppExecutors.instance
            .diskIO()
            .execute({ mUserRepository.saveUser(userEntity) })
      }

      override fun onError(error: BaseException) {
        //Maybe error message Null
      }
    })
    mCompositeDisposable.add(disposable)
  }

  class Factory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

      return LoginViewModel(mApplication,
          UserRepository.getInstance(AppDatabase.getInstance(BasicApp.self))) as T
    }
  }
}
