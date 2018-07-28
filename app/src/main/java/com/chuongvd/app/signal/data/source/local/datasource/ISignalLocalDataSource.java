package com.chuongvd.app.signal.data.source.local.datasource;

import android.arch.lifecycle.LiveData;
import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.entity.UserEntity;
import java.util.List;

public interface ISignalLocalDataSource {
    void saveSignal(SignalEntity signalEntity);

    void saveListSignal(List<SignalEntity> list);

    LiveData<List<SignalEntity>> getLocalSignals();

}
