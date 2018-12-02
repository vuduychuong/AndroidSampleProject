package com.chuongvd.app.example.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.chuongvd.app.example.data.database.model.User;

@Entity(tableName = "user")
public class UserEntity implements User {
    private int id;
    @PrimaryKey
    @NonNull
    private String accessToken;
    private String name;
    private String email;
    private String phone;
    private String socialId;
    private String socialSource;
    private String language;
    private String type;
    private String expiredDate;
    private boolean notificationStatus;
    private String refId;
    private String yourRefId;
    private float rocAmount;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getSocialId() {
        return socialId;
    }

    @Override
    public String getSocialSource() {
        return socialSource;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getExpiredDate() {
        return expiredDate;
    }

    @Override
    public boolean getNotificationStatus() {
        return notificationStatus;
    }

    @Override
    public String getRefId() {
        return refId;
    }

    @Override
    public String getYourRefId() {
        return yourRefId;
    }

    @Override
    public float getRocAmount() {
        return rocAmount;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public void setSocialSource(String socialSource) {
        this.socialSource = socialSource;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setNotificationStatus(boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setYourRefId(String yourRefId) {
        this.yourRefId = yourRefId;
    }

    public void setRocAmount(float rocAmount) {
        this.rocAmount = rocAmount;
    }
}
