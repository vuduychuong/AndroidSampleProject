package com.chuongvd.app.signal.data.source.remote.service.rx

import com.chuongvd.app.signal.data.source.remote.service.BaseException
import io.reactivex.observers.DisposableObserver

abstract class RocketSubscriber<T> : DisposableObserver<T>() {

  private var data: T? = null

  override fun onStart() {
    super.onStart()
  }

  override fun onNext(t: T) {
    data = t
  }

  override fun onError(e: Throwable) {
    val exception: BaseException
    if (e is BaseException) {
      exception = e
    } else {
      exception = BaseException.toUnexpectedError(e)
    }

    onRequestFinish()
    onError(exception)
  }

  override fun onComplete() {
    onSuccess(data)
    onRequestFinish()
  }

  abstract fun onSuccess(`object`: T?)

  abstract fun onError(error: BaseException)

  /**
   * Runs after request complete or error
   */
  fun onRequestFinish() {

  }
}