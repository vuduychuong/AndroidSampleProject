package com.chuongvd.app.example.data.source.remote.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import com.chuongvd.app.example.utils.GsonUtils;
import java.net.HttpURLConnection;
import retrofit2.Response;

public class ApiResponse<R> {

    public <T> LiveData<ApiResponse<T>> convertData() {
        MediatorLiveData<ApiResponse<T>> result = new MediatorLiveData<>();
        if (this instanceof ApiSuccessResponse) {
            result.setValue(
                    new ApiSuccessResponse<>(((ApiSuccessResponse<BaseResponse<T>>) this).data.data,
                            ((ApiSuccessResponse) this).mPagingInfo.toString()));
        }
        if (this instanceof ApiEmptyResponse) {
            result.setValue(new ApiEmptyResponse<>());
        }
        if (this instanceof ApiErrorResponse) {
            result.setValue(new ApiErrorResponse<>(((ApiErrorResponse) this).getErrorMsg(),
                    ((ApiErrorResponse<R>) this).getCode()));
        }
        return result;
    }

    public static <R> ApiResponse<R> create(Response<R> response) {
        if (response.isSuccessful()) {
            R body = response.body();
            if (body == null || response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body, response.headers().get("x-pagination"));
            }
        } else {
            return new ApiErrorResponse<>(response.message(), response.code());
        }
    }

    public static <R> ApiErrorResponse<R> create(Throwable throwable) {
        return new ApiErrorResponse<>(
                throwable.getMessage() == null ? "unknow error" : throwable.getMessage());
    }

    public static class ApiErrorResponse<R> extends ApiResponse<R> {
        private String mErrorMsg;
        private int mCode;

        ApiErrorResponse(String errMsg) {
            mErrorMsg = errMsg;
        }

        ApiErrorResponse(String errMsg, int code) {
            mErrorMsg = errMsg;
            mCode = code;
        }

        String getErrorMsg() {
            return mErrorMsg;
        }

        public int getCode() {
            return mCode;
        }
    }

    static class ApiEmptyResponse<R> extends ApiResponse<R> {
    }

    public static class ApiSuccessResponse<R> extends ApiResponse<R> {
        public R data;
        PagingInfo mPagingInfo;

        ApiSuccessResponse(R body, String jsonPage) {
            data = body;
            if (jsonPage == null) {
                mPagingInfo = new PagingInfo();
                return;
            }
            mPagingInfo = GsonUtils.String2Object(jsonPage, PagingInfo.class);
        }
    }
}
