package com.chuongvd.app.signal.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class HomeViewModel internal constructor(application: Application) : AndroidViewModel(application) {


  class Factory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

      return HomeViewModel(mApplication) as T
    }
  }
}
