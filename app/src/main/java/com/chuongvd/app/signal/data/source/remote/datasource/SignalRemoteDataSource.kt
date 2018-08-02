package com.chuongvd.app.signal.data.source.remote.datasource

import com.chuongvd.app.signal.data.entity.SignalEntity
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper
import com.chuongvd.app.signal.data.source.remote.service.rx.SchedulerUtils
import io.reactivex.Observable

class SignalRemoteDataSource private constructor() : ISignalRemoteDataSource {

  override val remoteListSignal: Observable<List<SignalEntity>>
    get() = RequestHelper.request
        .signals
        .flatMap(SchedulerUtils.convertData<List<SignalEntity>>())
        .compose(SchedulerUtils.applyObservableAsync<List<SignalEntity>>())

  companion object {
    private var sInstance: SignalRemoteDataSource? = null

    val instance: SignalRemoteDataSource
      get() {
        if (sInstance == null) {
          sInstance = SignalRemoteDataSource()
        }
        return this.sInstance!!
      }
  }
}
