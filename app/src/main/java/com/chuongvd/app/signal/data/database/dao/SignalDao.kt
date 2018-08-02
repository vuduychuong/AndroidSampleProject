package com.chuongvd.app.signal.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.chuongvd.app.signal.data.entity.SignalEntity

@Dao
abstract class SignalDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertSignal(signal: SignalEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertListSignal(list: List<SignalEntity>)

  @Query("SELECT * FROM signals WHERE status = :status")
  abstract fun loadSignalByStatus(status: String): LiveData<List<SignalEntity>>

  @Query("SELECT * FROM signals")
  abstract fun loadSignals(): LiveData<List<SignalEntity>>

  fun updateSignal(list: List<SignalEntity>) {
    deleteAllSignal()
    insertListSignal(list)
  }

  @Query("DELETE FROM signals")
  abstract fun deleteAllSignal()
}
