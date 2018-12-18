package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;

import java.util.List;

import retrofit2.Call;

public class GetListCarInteractor extends GenericRequestHandler<List<Car>> {
    private boolean isUserCars;

    public GetListCarInteractor(boolean isUserCars) {
        this.isUserCars = isUserCars;
    }

    @Override
    protected Call<ApiResponse<List<Car>>> makeRequest() {
        if (isUserCars) {
            return ApiRepository.apiService.getUserCar();
        } else {
            return ApiRepository.apiService.getAllCars();
        }
    }
}
