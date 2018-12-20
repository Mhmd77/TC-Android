package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;

import retrofit2.Call;

public class GetCarInteractor extends GenericRequestHandler<Car> {
    private int carId;

    public GetCarInteractor(int carId) {
        this.carId = carId;
    }

    @Override
    protected Call<ApiResponse<Car>> makeRequest() {
        return ApiRepository.apiService.getCar(carId);
    }
}
