package com.chuongvd.app.signal.data.source.repository

import android.arch.lifecycle.LiveData
import com.chuongvd.app.signal.BasicApp
import com.chuongvd.app.signal.data.database.AppDatabase
import com.chuongvd.app.signal.data.entity.SignalEntity
import com.chuongvd.app.signal.data.source.local.datasource.ISignalLocalDataSource
import com.chuongvd.app.signal.data.source.local.datasource.SignalLocalDataSource
import com.chuongvd.app.signal.data.source.remote.datasource.ISignalRemoteDataSource
import com.chuongvd.app.signal.data.source.remote.datasource.SignalRemoteDataSource
import io.reactivex.Observable

class SignalRepository private constructor(remoteDataSource: SignalRemoteDataSource,
    localDataSource: SignalLocalDataSource) : ISignalLocalDataSource, ISignalRemoteDataSource {
  private val mRemoteDataSource: ISignalRemoteDataSource
  private val mLocalDataSource: ISignalLocalDataSource

  override val localSignals: LiveData<List<SignalEntity>>
    get() = mLocalDataSource.localSignals

  override val remoteListSignal: Observable<List<SignalEntity>>
    get() = mRemoteDataSource.remoteListSignal

  init {
    mRemoteDataSource = remoteDataSource
    mLocalDataSource = localDataSource
  }

  override fun saveSignal(signalEntity: SignalEntity) {
    mLocalDataSource.saveSignal(signalEntity)
  }

  override fun saveListSignal(list: List<SignalEntity>) {
    mLocalDataSource.saveListSignal(list)
  }

  companion object {

    private var sInstance: SignalRepository? = null

    val instance: SignalRepository
      get() {
        if (sInstance == null) {
          synchronized(SignalRepository::class.java) {
            if (sInstance == null) {
              sInstance = SignalRepository(SignalRemoteDataSource.instance,
                  SignalLocalDataSource.getInstance(
                      AppDatabase.getInstance(BasicApp.self)))
            }
          }
        }
        return this.sInstance!!
      }
  }
}
