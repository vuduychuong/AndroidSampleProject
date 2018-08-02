package com.chuongvd.app.signal.data.source.remote.response

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
  @SerializedName("result")
  var data: T? = null
}
