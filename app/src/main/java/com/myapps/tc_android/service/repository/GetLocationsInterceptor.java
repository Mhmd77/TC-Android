package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;

import java.util.List;

import retrofit2.Call;

public class GetLocationsInterceptor extends GenericRequestHandler {
    @Override
    protected Call<ApiResponse<List<String>>> makeRequest() {
        return ApiRepository.apiService.getLocations();
    }

}
