package com.chuongvd.app.signal.data.source.remote.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseErrorResponse {

  @SerializedName("code")
  var code: String? = null

  @SerializedName("messageKey")
  @Expose
  var errorMessage: String? = null
    internal set

  val isError: Boolean
    get() = errorMessage != null
}
