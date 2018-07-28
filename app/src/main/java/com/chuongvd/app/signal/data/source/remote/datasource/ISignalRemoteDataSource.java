package com.chuongvd.app.signal.data.source.remote.datasource;

import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.entity.UserEntity;
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import io.reactivex.Observable;
import java.util.List;

public interface ISignalRemoteDataSource {
    Observable<List<SignalEntity>> getRemoteListSignal();
}
