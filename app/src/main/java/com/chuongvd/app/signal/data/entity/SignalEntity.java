package com.chuongvd.app.signal.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.chuongvd.app.signal.data.database.model.Signal;

@Entity(tableName = "signals")
public class SignalEntity implements Signal {
    @PrimaryKey
    @NonNull
    private int id;
    private String symbol;
    private long dateIn;
    private String type;
    private float priceIn;
    private float takeProfitPrice;
    private float stopLossPrice;
    private String status;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public long getDateIn() {
        return dateIn;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public float getPriceIn() {
        return priceIn;
    }

    @Override
    public float getTakeProfitPrice() {
        return takeProfitPrice;
    }

    @Override
    public float getStopLossPrice() {
        return stopLossPrice;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDateIn(long dateIn) {
        this.dateIn = dateIn;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPriceIn(float priceIn) {
        this.priceIn = priceIn;
    }

    public void setTakeProfitPrice(float takeProfitPrice) {
        this.takeProfitPrice = takeProfitPrice;
    }

    public void setStopLossPrice(float stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
