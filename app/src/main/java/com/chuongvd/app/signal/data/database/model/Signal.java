package com.chuongvd.app.signal.data.database.model;

public interface Signal {
    int getId();
    String getSymbol();
    long getDateIn();
    String getType();
    float getPriceIn();
    float getTakeProfitPrice();
    float getStopLossPrice();
    String getStatus();
}
