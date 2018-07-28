package com.chuongvd.app.signal.data.source.remote.request;

public class LoginRequest extends BaseRequest {
    private String appVersion;
    private String os;
    private String notificationToken;
    private String socialToken;
    private String source;

    public LoginRequest(String appVersion, String os, String notificationToken, String socialToken,
            String source) {
        this.appVersion = appVersion;
        this.os = os;
        this.notificationToken = notificationToken;
        this.socialToken = socialToken;
        this.source = source;
    }
}
