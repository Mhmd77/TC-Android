package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;

import java.util.List;

import retrofit2.Call;

public class GetListCarInteractor extends GenericRequestHandler<List<Car>> {

    @Override
    protected Call<ApiResponse<List<Car>>> makeRequest() {
        return ApiRepository.apiService.getAllCars();
    }
}
