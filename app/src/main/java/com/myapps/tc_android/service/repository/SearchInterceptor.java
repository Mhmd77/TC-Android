package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;

import java.util.List;

import retrofit2.Call;

public class SearchInterceptor extends GenericRequestHandler {
    private String query;

    public SearchInterceptor(String query) {
        this.query = query;
    }

    @Override
    protected Call<ApiResponse<List<Car>>> makeRequest() {
        return ApiRepository.apiService.searchCars(query);
    }
}
