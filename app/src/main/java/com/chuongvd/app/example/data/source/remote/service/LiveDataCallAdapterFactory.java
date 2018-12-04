package com.chuongvd.app.example.data.source.remote.service;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import static retrofit2.CallAdapter.Factory;

public class LiveDataCallAdapterFactory extends Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations,
            @NonNull Retrofit retrofit) {
        if (Factory.getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        Type rawObservableType = Factory.getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (!ParameterizedType.class.isInstance(observableType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}
