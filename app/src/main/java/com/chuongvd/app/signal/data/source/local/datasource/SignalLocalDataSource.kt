package com.chuongvd.app.signal.data.source.local.datasource

import android.arch.lifecycle.LiveData
import com.chuongvd.app.signal.data.database.AppDatabase
import com.chuongvd.app.signal.data.entity.SignalEntity

class SignalLocalDataSource private constructor(
    private val mDatabase: AppDatabase) : ISignalLocalDataSource {

  override val localSignals: LiveData<List<SignalEntity>>
    get() = mDatabase.signalDao().loadSignals()

  override fun saveSignal(signalEntity: SignalEntity) {
    mDatabase.signalDao().insertSignal(signalEntity)
  }

  override fun saveListSignal(list: List<SignalEntity>) {
    mDatabase.signalDao().updateSignal(list)
  }

  companion object {
    private var sInstance: SignalLocalDataSource? = null

    fun getInstance(database: AppDatabase): SignalLocalDataSource {
      if (sInstance == null) {
        synchronized(SignalLocalDataSource::class.java) {
          if (sInstance == null) {
            sInstance = SignalLocalDataSource(database)
          }
        }
      }
      return this.sInstance!!
    }
  }
}
