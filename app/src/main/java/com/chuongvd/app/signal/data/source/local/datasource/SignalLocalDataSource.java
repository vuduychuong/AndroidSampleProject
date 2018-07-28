package com.chuongvd.app.signal.data.source.local.datasource;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.signal.data.database.AppDatabase;
import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.entity.UserEntity;
import java.util.List;

public class SignalLocalDataSource implements ISignalLocalDataSource {
    private static SignalLocalDataSource sInstance;

    private final AppDatabase mDatabase;

    private SignalLocalDataSource(AppDatabase database) {
        mDatabase = database;
    }

    public static SignalLocalDataSource getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (SignalLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new SignalLocalDataSource(database);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void saveSignal(SignalEntity signalEntity) {
        mDatabase.signalDao().insertSignal(signalEntity);
    }

    @Override
    public void saveListSignal(List<SignalEntity> list) {
        mDatabase.signalDao().updateSignal(list);
    }

    @Override
    public LiveData<List<SignalEntity>> getLocalSignals() {
        return mDatabase.signalDao().loadSignals();
    }
}
