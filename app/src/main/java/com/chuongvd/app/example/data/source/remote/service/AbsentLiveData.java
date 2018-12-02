package com.chuongvd.app.example.data.source.remote.service;

import android.arch.lifecycle.LiveData;

public class AbsentLiveData<T> extends LiveData<T> {
    public AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        return new AbsentLiveData<>();
    }
}
