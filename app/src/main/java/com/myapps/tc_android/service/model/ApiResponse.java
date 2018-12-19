package com.myapps.tc_android.service.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class ApiResponse<T> {
    @SerializedName("object")
    private T object;
    @SerializedName("token")
    private String token;
    @SerializedName("status")
    private String status;

    public String getToken() {
        return token;
    }

    public T getObject() {
        return object;
    }

    public String getStatus() {
        return status;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void parseObject(JsonElement json) {
        if (json == null) {
            setObject(null);
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.enableComplexMapKeySerialization().create();
        T parsedObject = gson.fromJson(json, new TypeToken<T>() {
        }.getType());
        setObject(parsedObject);
    }
}
