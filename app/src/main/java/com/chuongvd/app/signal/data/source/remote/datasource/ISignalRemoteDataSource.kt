package com.chuongvd.app.signal.data.source.remote.datasource

import com.chuongvd.app.signal.data.entity.SignalEntity
import io.reactivex.Observable

interface ISignalRemoteDataSource {
  val remoteListSignal: Observable<List<SignalEntity>>
}
