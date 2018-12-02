package com.chuongvd.app.example.data.source.remote.service;

import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.ERROR;
import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.LOADING;
import static com.chuongvd.app.example.data.source.remote.service.RequestStatus.SUCCESS;

public class Resource<T> {
    public @RequestStatus
    int status;
    public T data;
    public String message;
    public PagingInfo mPagingInfo;

    public Resource(int status, T data, String message, PagingInfo pagingInfo) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.mPagingInfo = pagingInfo;
    }

    static <T> Resource<T> success(T data, PagingInfo pagingInfo) {
        return new Resource<>(SUCCESS, data, null, pagingInfo);
    }

    static <T> Resource<T> error(String message, T data) {
        return new Resource<>(ERROR, data, message, null);
    }

    static <T> Resource<T> loading(T data) {
        return new Resource<>(LOADING, data, null, null);
    }
}
