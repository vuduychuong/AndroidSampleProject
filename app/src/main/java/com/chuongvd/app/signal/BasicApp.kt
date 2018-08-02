package com.chuongvd.app.signal

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Android Application class. Used for accessing singletons.
 */
class BasicApp : Application() {

  override fun onCreate() {
    super.onCreate()
    sSelf = this
  }

  companion object {

    @SuppressLint("StaticFieldLeak")
    private var sSelf: Context? = null

    val self: Context
      get() = sSelf!!
  }
}
