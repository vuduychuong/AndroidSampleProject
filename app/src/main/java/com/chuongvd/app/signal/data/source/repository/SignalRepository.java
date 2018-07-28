package com.chuongvd.app.signal.data.source.repository;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.signal.BasicApp;
import com.chuongvd.app.signal.data.database.AppDatabase;
import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.source.local.datasource.ISignalLocalDataSource;
import com.chuongvd.app.signal.data.source.local.datasource.SignalLocalDataSource;
import com.chuongvd.app.signal.data.source.remote.datasource.ISignalRemoteDataSource;
import com.chuongvd.app.signal.data.source.remote.datasource.SignalRemoteDataSource;
import io.reactivex.Observable;
import java.util.List;

public class SignalRepository implements ISignalLocalDataSource, ISignalRemoteDataSource {

    private static SignalRepository sInstance;
    private ISignalRemoteDataSource mRemoteDataSource;
    private ISignalLocalDataSource mLocalDataSource;

    private SignalRepository(SignalRemoteDataSource remoteDataSource,
            SignalLocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static SignalRepository getInstance() {
        if (sInstance == null) {
            synchronized (SignalRepository.class) {
                if (sInstance == null) {
                    sInstance = new SignalRepository(SignalRemoteDataSource.getInstance(),
                            SignalLocalDataSource.getInstance(
                                    AppDatabase.getInstance(BasicApp.self())));
                }
            }
        }
        return sInstance;
    }

    @Override
    public void saveSignal(SignalEntity signalEntity) {
        mLocalDataSource.saveSignal(signalEntity);
    }

    @Override
    public void saveListSignal(List<SignalEntity> list) {
        mLocalDataSource.saveListSignal(list);
    }

    @Override
    public LiveData<List<SignalEntity>> getLocalSignals() {
        return mLocalDataSource.getLocalSignals();
    }

    @Override
    public Observable<List<SignalEntity>> getRemoteListSignal() {
        return mRemoteDataSource.getRemoteListSignal();
    }
}
