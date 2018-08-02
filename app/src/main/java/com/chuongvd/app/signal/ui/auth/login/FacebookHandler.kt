package com.chuongvd.app.signal.ui.auth.login

import android.content.Context
import com.chuongvd.app.signal.R
import java.util.Arrays

class FacebookHandler(private val mContext: Context) {

  val facebookPermission: List<String>
    get() = Arrays.asList(*mContext.resources.getStringArray(R.array.facebook_permission))

  /**
   * The photos must be less than 12MB in size
   * People need the native Facebook for Android app installed, version 7.0 or higher
   */
  fun shareImage() {
    //TODO: function share image to facebook's user
  }
}
