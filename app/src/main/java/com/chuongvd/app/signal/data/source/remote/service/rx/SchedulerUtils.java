package com.chuongvd.app.signal.data.source.remote.service.rx;

import android.support.annotation.NonNull;
import com.chuongvd.app.signal.data.source.remote.response.BaseResponse;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

public class SchedulerUtils {

    /**
     * Create a transformer to compose an Observable to subscribe on separate thread and observe it
     * in UI thread
     *
     * @param <T> Rule of Observable object
     */
    public static <T> ObservableTransformer<T, T> applyObservableAsync() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * Create a transformer to compose an Observable to subscribe on current thread and observe it
     * in this thread
     * Using in synchronous API request
     *
     * @param <T> Rule of Observable object
     */
    public static <T> ObservableTransformer<T, T> applyObservableCompute() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * Create a transformer to compose an Observable to subscribe on current thread and observe it
     * in main thread
     * Using in synchronous API request
     *
     * @param <T> Rule of Observable object
     */
    public static <T> ObservableTransformer<T, T> applyObservableMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> applyFlowableMainThread() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> flowable) {
                return flowable.observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> applyFlowableAsysnc() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> flowable) {
                return flowable.observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> applyFlowableCompute() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> flowable) {
                return flowable.observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /***
     * convert data from from baseResponse to T
     * @param <T>
     * @return
     */
    public static <T> Function<BaseResponse<T>, Observable<T>> convertData() {
        return tBaseResponse -> {
            if (tBaseResponse == null || tBaseResponse.getData() == null) {
                return Observable.empty();
            }
            return Observable.just(tBaseResponse.getData());
        };
    }
}
