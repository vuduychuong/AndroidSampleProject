package com.chuongvd.app.signal.data.database.model

interface User {
  val id: Int
  val name: String?
  val email: String?
  val phone: String?
  val socialId: String?
  val socialSource: String?
  val language: String?
  val type: String?
  val expiredDate: String?
  val notificationStatus: Boolean
  val refId: String?
  val yourRefId: String?
  val rocAmount: Float
}
