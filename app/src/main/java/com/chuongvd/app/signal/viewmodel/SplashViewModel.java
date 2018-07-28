package com.chuongvd.app.signal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.chuongvd.app.signal.BasicApp;
import com.chuongvd.app.signal.data.database.AppDatabase;
import com.chuongvd.app.signal.data.entity.UserEntity;
import com.chuongvd.app.signal.data.source.repository.UserRepository;

public class SplashViewModel extends AndroidViewModel {
    private final LiveData<UserEntity> mUser;
    private UserRepository mUserRepository;

    public SplashViewModel(@NonNull Application application, UserRepository userRepository) {
        super(application);
        mUserRepository = userRepository;
        mUser = mUserRepository.getUser();
    }

    public LiveData<UserEntity> getUser() {
        return mUser;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SplashViewModel(mApplication,
                    UserRepository.getInstance(AppDatabase.getInstance(BasicApp.self())));
        }
    }
}
