package com.chuongvd.app.signal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.chuongvd.app.signal.BasicApp;
import com.chuongvd.app.signal.data.database.AppDatabase;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application mApplication;
    private final AppDatabase mDatabase;

    public ViewModelFactory(Application application) {
        mApplication = application;
        mDatabase = AppDatabase.getInstance(BasicApp.self());
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }
}
