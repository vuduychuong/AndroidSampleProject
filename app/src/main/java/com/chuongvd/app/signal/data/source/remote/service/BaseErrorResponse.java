package com.chuongvd.app.signal.data.source.remote.service;

import android.text.TextUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BaseErrorResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("messageKey")
    @Expose
    String error;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isError() {
        return error != null;
    }

    public String getErrorMessage() {
        return error;
    }
}
