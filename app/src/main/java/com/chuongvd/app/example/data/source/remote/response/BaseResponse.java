package com.chuongvd.app.example.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("result")
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
