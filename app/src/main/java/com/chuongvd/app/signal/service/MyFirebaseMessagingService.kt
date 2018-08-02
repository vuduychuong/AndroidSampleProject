package com.chuongvd.app.signal.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
  override fun onMessageReceived(remoteMessage: RemoteMessage?) {
    val data = remoteMessage!!.data
    // TODO: 7/29/18 Handler data message
  }

  override fun onDeletedMessages() {
    super.onDeletedMessages()
  }

  override fun onNewToken(s: String?) {
    super.onNewToken(s)
    updateNotificationToken(s)
  }

  private fun updateNotificationToken(token: String?) {

  }
}
