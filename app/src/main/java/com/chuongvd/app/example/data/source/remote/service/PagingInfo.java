package com.chuongvd.app.example.data.source.remote.service;

import com.chuongvd.app.example.utils.GsonUtils;
import com.google.gson.annotations.SerializedName;

//
// Created by chuongvd on 9/10/18.
//
//
//{"totalCount":108,"pageSize":10,"currentPage":1,"totalPages":11}
public class PagingInfo {
    @SerializedName("totalCount")
    private int mTotalCount;
    @SerializedName("pageSize")
    private int mPageSize;
    @SerializedName("currentPage")
    private int mCurrentPage;
    @SerializedName("totalPages")
    private int mTotalPages;

    @Override
    public String toString() {
        return GsonUtils.Object2String(this);
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }
}
