package com.chuongvd.app.signal.data.source.remote.datasource;

import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.entity.UserEntity;
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper;
import com.chuongvd.app.signal.data.source.remote.service.rx.SchedulerUtils;
import io.reactivex.Observable;
import java.util.List;

public class SignalRemoteDataSource implements ISignalRemoteDataSource {
    private static SignalRemoteDataSource sInstance;

    private SignalRemoteDataSource() {

    }

    public static SignalRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SignalRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Observable<List<SignalEntity>> getRemoteListSignal() {
        return RequestHelper.getRequest()
                .getSignals()
                .flatMap(SchedulerUtils.convertData())
                .compose(SchedulerUtils.applyObservableAsync());
    }
}
