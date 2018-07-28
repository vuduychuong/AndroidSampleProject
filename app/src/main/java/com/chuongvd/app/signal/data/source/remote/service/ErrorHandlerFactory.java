package com.chuongvd.app.signal.data.source.remote.service;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ErrorHandlerFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory mInstance;

    private ErrorHandlerFactory() {
        mInstance = RxJava2CallAdapterFactory.create();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, mInstance.get(type, annotations, retrofit));
    }

    public static CallAdapter.Factory create() {
        return new ErrorHandlerFactory();
    }

    private class RxCallAdapterWrapper<R> implements CallAdapter<R, Observable<?>> {

        final Retrofit mRetrofit;
        final CallAdapter<R, Observable<?>> mWrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, Observable<?>> callAdapter) {

            this.mRetrofit = retrofit;
            this.mWrapped = callAdapter;
        }

        @Override
        public Type responseType() {
            return mWrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Observable<?> adapt(Call<R> call) {
            return ((Observable) mWrapped.adapt(call)).onErrorResumeNext(
                    new Function<Throwable, ObservableSource>() {
                        @Override
                        public ObservableSource apply(@NonNull Throwable throwable)
                                throws Exception {
                            if (throwable == null) {
                                return Observable.error(throwable);
                            }
                            return Observable.error(convertToBaseException(throwable));
                        }
                    });
        }

        private BaseException convertToBaseException(Throwable throwable) {
            if (throwable instanceof BaseException) {
                return (BaseException) throwable;
            } else if (throwable instanceof IOException) {
                return BaseException.toNetworkError(throwable);
            } else if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                if (response.errorBody() == null) {
                    return BaseException.toHttpError(response);
                }
                try {
                    String errorResponse = response.errorBody().string();
                    BaseErrorResponse baseErrorResponse = deserializeErrorBody(errorResponse);
                    if (baseErrorResponse != null && baseErrorResponse.isError()) {
                        //Get error data from Server
                        return BaseException.toServerError(baseErrorResponse);
                    } else {
                        //Get error data cause http connection
                        return BaseException.toHttpError(response, baseErrorResponse);
                    }
                } catch (IOException e) {
                    Log.e(this.getClass().getSimpleName(), e.getMessage());
                }
            }
            return BaseException.toUnexpectedError(throwable);
        }

        private BaseErrorResponse deserializeErrorBody(String errorString) {
            try {
                return new Gson().fromJson(errorString, BaseErrorResponse.class);
            } catch (JsonSyntaxException e) {
                return null;
            }
        }
    }
}