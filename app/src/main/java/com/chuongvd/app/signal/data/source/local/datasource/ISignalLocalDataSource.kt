package com.chuongvd.app.signal.data.source.local.datasource

import android.arch.lifecycle.LiveData
import com.chuongvd.app.signal.data.entity.SignalEntity

interface ISignalLocalDataSource {

  val localSignals: LiveData<List<SignalEntity>>
  fun saveSignal(signalEntity: SignalEntity)

  fun saveListSignal(list: List<SignalEntity>)

}
