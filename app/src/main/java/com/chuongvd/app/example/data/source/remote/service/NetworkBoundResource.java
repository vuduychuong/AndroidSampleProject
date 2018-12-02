package com.chuongvd.app.example.data.source.remote.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import com.chuongvd.app.example.AppExecutors;
import com.chuongvd.app.example.data.source.remote.service.ApiResponse.ApiEmptyResponse;
import com.chuongvd.app.example.data.source.remote.service.ApiResponse.ApiErrorResponse;
import com.chuongvd.app.example.data.source.remote.service.ApiResponse.ApiSuccessResponse;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private AppExecutors mAppExecutors;
    private boolean mIsSaveLocal;
    private boolean mIsClearOldLocalData;
    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors, boolean isSaveLocal) {
        this(appExecutors, isSaveLocal, false);
    }

    public NetworkBoundResource(AppExecutors appExecutors, boolean isSaveLocal,
            boolean isClearOldLocalData) {
        mAppExecutors = appExecutors;
        mIsSaveLocal = isSaveLocal;
        mIsClearOldLocalData = isClearOldLocalData;
        result.setValue(Resource.loading(null));
        if (mIsSaveLocal) {
            LiveData<ResultType> dbSource = loadFromDb();
            result.addSource(dbSource, resultType -> {
                result.removeSource(dbSource);
                if (shouldFetchData(resultType)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, rType -> setValue(Resource.success(rType, null)));
                }
            });
        } else {
            fetchFromNetwork(AbsentLiveData.create());
        }
    }

    private void setValue(Resource<ResultType> resource) {
        if (result.getValue() != resource) result.setValue(resource);
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, resultType -> setValue(Resource.loading(resultType)));

        result.addSource(apiResponse, response -> {
            result.removeSource(dbSource);
            result.removeSource(apiResponse);

            if (response instanceof ApiSuccessResponse) {
                if (mIsClearOldLocalData) {
                    mAppExecutors.diskIO().execute(this::clearOldLocalData);
                }
                if (mIsSaveLocal) {
                    mAppExecutors.diskIO().execute(() -> {
                        saveCallResult(processResponse((ApiSuccessResponse<RequestType>) response));
                        mAppExecutors.mainThread().execute(() -> {
                            setValue(Resource.success(
                                    ((ApiSuccessResponse<ResultType>) response).data,
                                    ((ApiSuccessResponse) response).mPagingInfo));
                            result.addSource(loadFromDb(), resultType -> setValue(
                                    Resource.success(resultType,
                                            ((ApiSuccessResponse) response).mPagingInfo)));
                        });
                    });
                } else {
                    setValue(Resource.success(((ApiSuccessResponse<ResultType>) response).data,
                            ((ApiSuccessResponse) response).mPagingInfo));
                }
            }

            if (response instanceof ApiEmptyResponse) {
                mAppExecutors.mainThread()
                        .execute(() -> result.addSource(loadFromDb(),
                                resultType -> setValue(Resource.success(resultType, null))));
            }

            if (response instanceof ApiErrorResponse) {
                onFetchFailed();
                result.addSource(dbSource, resultType -> setValue(
                        Resource.error(((ApiErrorResponse) response).getErrorMsg(), resultType)));
            }
        });
    }

    protected void clearOldLocalData() {
        // TODO: 12/1/18
    }

    protected void onFetchFailed() {
        // TODO: 12/1/18
    }

    protected void saveCallResult(RequestType requestType) {
        // TODO: 12/1/18
    }

    private RequestType processResponse(ApiSuccessResponse<RequestType> response) {
        return response.data;
    }

    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    protected abstract boolean shouldFetchData(ResultType resultType);

    protected LiveData<ResultType> loadFromDb() {
        return new AbsentLiveData<>();
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
