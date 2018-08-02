package com.chuongvd.app.signal

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application.
 *
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors private constructor(
    private val mDiskIO: Executor = Executors.newSingleThreadExecutor(),
    private val mNetworkIO: Executor = Executors.newFixedThreadPool(3),
    private val mMainThread: Executor = MainThreadExecutor()) {

  fun diskIO(): Executor {
    return mDiskIO
  }

  fun networkIO(): Executor {
    return mNetworkIO
  }

  fun mainThread(): Executor {
    return mMainThread
  }

  private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
      mainThreadHandler.post(command)
    }
  }

  companion object {

    private var sInstance: AppExecutors? = null

    val instance: AppExecutors
      get() {
        if (sInstance == null) {
          synchronized(AppExecutors::class.java) {
            if (sInstance == null) {
              sInstance = AppExecutors()
            }
          }
        }
        return this.sInstance!!
      }
  }
}
