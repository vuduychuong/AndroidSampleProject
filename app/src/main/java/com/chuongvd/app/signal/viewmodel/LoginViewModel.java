package com.chuongvd.app.signal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;
import com.chuongvd.app.signal.AppExecutors;
import com.chuongvd.app.signal.BasicApp;
import com.chuongvd.app.signal.data.database.AppDatabase;
import com.chuongvd.app.signal.data.entity.UserEntity;
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest;
import com.chuongvd.app.signal.data.source.remote.service.BaseException;
import com.chuongvd.app.signal.data.source.remote.service.rx.RocketSubscriber;
import com.chuongvd.app.signal.data.source.repository.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends AndroidViewModel {
    private final LiveData<UserEntity> mUser;
    private UserRepository mUserRepository;
    private CompositeDisposable mCompositeDisposable;

    LoginViewModel(@NonNull Application application, UserRepository userRepository) {
        super(application);
        mUserRepository = userRepository;
        mCompositeDisposable = new CompositeDisposable();
        mUser = mUserRepository.getUser();
    }

    public void login(LoginRequest request) {
        Disposable disposable =
                mUserRepository.login(request).subscribeWith(new RocketSubscriber<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        if (userEntity == null) return;
                        Log.d("Login", userEntity.getAccessToken());
                        AppExecutors.getInstance()
                                .diskIO()
                                .execute(() -> mUserRepository.saveUser(userEntity));
                    }

                    @Override
                    public void onError(BaseException error) {
                        //Maybe error message Null
                    }
                });
        mCompositeDisposable.add(disposable);
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
            return (T) new LoginViewModel(mApplication,
                    UserRepository.getInstance(AppDatabase.getInstance(BasicApp.self())));
        }
    }
}
