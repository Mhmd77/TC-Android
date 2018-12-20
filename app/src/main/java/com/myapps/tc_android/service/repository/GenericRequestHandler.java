package com.myapps.tc_android.service.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.myapps.tc_android.service.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Response;

public abstract class GenericRequestHandler<R> {
    abstract protected Call<ApiResponse<R>> makeRequest();

    public final MutableLiveData<R> doRequest() {
        final MutableLiveData<R> liveData = new MutableLiveData<>();
        makeRequest().enqueue(new ApiCallback<R>() {
            @Override
            protected void handleResponseData(R data) {
                liveData.setValue(data);
            }

            @Override
            protected void handleError(Response<ApiResponse<R>> response) {
                Log.e("Request Error", "Error failed with code: " + response.code() + " and message : " + response.message());
                liveData.setValue(null);
            }

            @Override
            protected void handleException(Exception t) {
                t.printStackTrace();
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public final void doRequest(final MutableLiveData<R> liveData) {
        makeRequest().enqueue(new ApiCallback<R>() {
            @Override
            protected void handleResponseData(R data) {
                liveData.setValue(data);
            }

            @Override
            protected void handleError(Response<ApiResponse<R>> response) {
                Log.e("Request Error", "Error failed with code: " + response.code() + " and message : " + response.message());
                liveData.setValue(null);
            }

            @Override
            protected void handleException(Exception t) {
                liveData.setValue(null);
            }
        });
    }
}
