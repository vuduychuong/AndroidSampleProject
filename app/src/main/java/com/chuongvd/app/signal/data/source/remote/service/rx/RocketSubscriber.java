package com.chuongvd.app.signal.data.source.remote.service.rx;

import android.support.annotation.NonNull;
import com.chuongvd.app.signal.data.source.remote.service.BaseException;
import io.reactivex.observers.DisposableObserver;

public abstract class RocketSubscriber<T> extends DisposableObserver<T> {

    private T data;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(@NonNull T t) {
        data = t;
    }

    @Override
    public void onError(@NonNull Throwable e) {
        BaseException exception;
        if (e instanceof BaseException) {
            exception = (BaseException) e;
        } else {
            exception = BaseException.toUnexpectedError(e);
        }

        onRequestFinish();
        onError(exception);
    }

    @Override
    public void onComplete() {
        onSuccess(data);
        onRequestFinish();
    }

    public abstract void onSuccess(T object);

    public abstract void onError(BaseException error);

    /**
     * Runs after request complete or error
     **/
    public void onRequestFinish() {

    }
}