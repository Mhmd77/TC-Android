package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;

import retrofit2.Call;

public class AddCarInterceptor extends GenericRequestHandler<Car> {
    private Car car;

    public AddCarInterceptor(Car car) {
        this.car = car;
    }

    @Override
    protected Call<ApiResponse<Car>> makeRequest() {
        return ApiRepository.apiService.addCar(car);
    }
}
