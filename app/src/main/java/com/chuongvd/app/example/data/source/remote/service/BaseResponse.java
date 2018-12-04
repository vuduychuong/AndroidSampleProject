package com.chuongvd.app.example.data.source.remote.service;

public class BaseResponse<T> {
    public String error;
    public String status;
    public String message;
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
