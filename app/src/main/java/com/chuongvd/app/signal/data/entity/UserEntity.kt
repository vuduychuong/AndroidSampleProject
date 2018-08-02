package com.chuongvd.app.signal.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.chuongvd.app.signal.data.database.model.User

@Entity(tableName = "user")
class UserEntity : User {
  override var id: Int = 0
  @PrimaryKey
  lateinit var accessToken: String
  override var name: String? = null
  override var email: String? = null
  override var phone: String? = null
  override var socialId: String? = null
  override var socialSource: String? = null
  override var language: String? = null
  override var type: String? = null
  override var expiredDate: String? = null
  override var notificationStatus: Boolean = false
  override var refId: String? = null
  override var yourRefId: String? = null
  override var rocAmount: Float = 0.toFloat()
}
