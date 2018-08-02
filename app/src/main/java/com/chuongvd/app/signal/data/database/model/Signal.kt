package com.chuongvd.app.signal.data.database.model

interface Signal {
  val id: Int
  val symbol: String?
  val dateIn: Long
  val type: String?
  val priceIn: Float
  val takeProfitPrice: Float
  val stopLossPrice: Float
  val status: String?
}
