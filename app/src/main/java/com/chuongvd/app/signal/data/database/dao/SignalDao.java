package com.chuongvd.app.signal.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.chuongvd.app.signal.data.entity.SignalEntity;
import java.util.List;

@Dao
public abstract class SignalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSignal(SignalEntity signal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertListSignal(List<SignalEntity> list);

    @Query("SELECT * FROM signals WHERE status = :status")
    public abstract LiveData<List<SignalEntity>> loadSignalByStatus(String status);

    @Query("SELECT * FROM signals")
    public abstract LiveData<List<SignalEntity>> loadSignals();

    @Transaction
    public void updateSignal(List<SignalEntity> list) {
        deleteAllSignal();
        insertListSignal(list);
    }

    @Query("DELETE FROM signals")
    public abstract void deleteAllSignal();
}
