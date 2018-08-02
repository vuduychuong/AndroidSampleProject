package com.chuongvd.app.signal.data.source.remote.request

class LoginRequest(private val appVersion: String, private val os: String,
    private val notificationToken: String, private val socialToken: String,
    private val source: String) : BaseRequest()
