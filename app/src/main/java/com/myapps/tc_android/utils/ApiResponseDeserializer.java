package com.myapps.tc_android.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.myapps.tc_android.service.model.ApiResponse;

import java.lang.reflect.Type;

public class ApiResponseDeserializer implements JsonDeserializer<ApiResponse> {
    private static final String KEY_STATUS = "status";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_OBJECT = "object";

    @Override
    public ApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final String token = jsonObject.get(KEY_TOKEN) == null ? "" : jsonObject.get(KEY_TOKEN).getAsString();
        final String status = jsonObject.get(KEY_STATUS) == null ? "" : jsonObject.get(KEY_STATUS).getAsString();
        ApiResponse result = new ApiResponse();
        result.setStatus(status);
        result.setToken(token);
        result.parseObject(jsonObject.get(KEY_OBJECT));
        return result;
    }
}
