package com.chuongvd.app.signal.data.source.remote.service.rx

import com.chuongvd.app.signal.data.source.remote.response.BaseResponse
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

object SchedulerUtils {

  /**
   * Create a transformer to compose an Observable to subscribe on separate thread and observe it
   * in UI thread
   *
   * @param <T> Rule of Observable object
  </T> */
  fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
      observable.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }

  /**
   * Create a transformer to compose an Observable to subscribe on current thread and observe it
   * in this thread
   * Using in synchronous API request
   *
   * @param <T> Rule of Observable object
  </T> */
  fun <T> applyObservableCompute(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
      observable.subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }

  /**
   * Create a transformer to compose an Observable to subscribe on current thread and observe it
   * in main thread
   * Using in synchronous API request
   *
   * @param <T> Rule of Observable object
  </T> */
  fun <T> applyObservableMainThread(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
      observable.observeOn(AndroidSchedulers.mainThread())
    }
  }

  fun <T> applyFlowableMainThread(): FlowableTransformer<T, T> {
    return FlowableTransformer { flowable -> flowable.observeOn(AndroidSchedulers.mainThread()) }
  }

  fun <T> applyFlowableAsysnc(): FlowableTransformer<T, T> {
    return FlowableTransformer { flowable -> flowable.observeOn(AndroidSchedulers.mainThread()) }
  }

  fun <T> applyFlowableCompute(): FlowableTransformer<T, T> {
    return FlowableTransformer { flowable -> flowable.observeOn(AndroidSchedulers.mainThread()) }
  }

  /***
   * convert data from from baseResponse to T
   * @param <T>
   * @return
  </T> */
  fun <T> convertData(): Function<BaseResponse<T>, Observable<T>> {
    return Function { tBaseResponse ->
      if (tBaseResponse.data == null) {
        return@Function Observable.empty<T>()
      }
      Observable.just<T>(tBaseResponse.data)
    }
  }
}
