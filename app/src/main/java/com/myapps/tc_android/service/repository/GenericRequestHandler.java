package com.myapps.tc_android.service.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class GenericRequestHandler<R> {
    abstract protected Call<ApiResponse<R>> makeRequest();

    public final void doRequest(final MutableLiveData<R> liveData) {
        makeRequest().enqueue(new ApiCallback<R>() {
            @Override
            protected void handleResponseData(R data) {
                liveData.setValue(data);
            }

            @Override
            protected void handleError(Response<ApiResponse<R>> response) {
                try {
                    String errorResponse = response.errorBody().string();
                    String error = parseError(errorResponse);
                    Toast.makeText(MyApplication.getAppContext(), error, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("Request Error", "Error failed with code: " + response.code() + " and message : " + response.message());
                liveData.setValue(null);
            }

            @Override
            protected void handleException(Exception t) {
                liveData.setValue(null);
            }
        });
    }

    private String parseError(String errorResponse) throws JSONException {
        JSONObject jsonElement = new JSONObject(errorResponse);
        return jsonElement.getString("status");
    }
}
