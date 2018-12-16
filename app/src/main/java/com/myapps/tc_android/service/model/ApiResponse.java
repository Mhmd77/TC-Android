package com.myapps.tc_android.service.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T>{
    @SerializedName("status")
    private String status;
    @SerializedName("object")
    private T object;

    public ApiResponse(String status, T user) {
        this.status = status;
        this.object = user;
    }

    public String getStatus() {
        return status;
    }

    public T getObject() {
        return object;
    }
}
