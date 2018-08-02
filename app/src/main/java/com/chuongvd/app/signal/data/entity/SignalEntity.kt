package com.chuongvd.app.signal.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.chuongvd.app.signal.data.database.model.Signal

@Entity(tableName = "signals")
class SignalEntity : Signal {
  @PrimaryKey
  override var id: Int = 0
  override var symbol: String? = null
  override var dateIn: Long = 0
  override var type: String? = null
  override var priceIn: Float = 0.toFloat()
  override var takeProfitPrice: Float = 0.toFloat()
  override var stopLossPrice: Float = 0.toFloat()
  override var status: String? = null
}
