package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class ApiCallback<T> implements Callback<ApiResponse<T>> {
    @Override
    public void onResponse(Call<ApiResponse<T>> call, Response<ApiResponse<T>> response) {
        if (response.body() != null) {
            handleResponseData(response.body().getObject());
        } else {
            handleError(response);
        }
    }

    abstract protected void handleResponseData(T data);

    abstract protected void handleError(Response<ApiResponse<T>> response);

    abstract protected void handleException(Exception t);

    @Override
    public void onFailure(Call<ApiResponse<T>> call, Throwable t) {
        handleException((Exception) t);
    }
}