package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.RentCar;

import retrofit2.Call;

public class AcceptRentCarInterceptor extends GenericRequestHandler {
    private RentCar rentCar;

    public AcceptRentCarInterceptor(RentCar rentCar) {
        this.rentCar = rentCar;
    }

    @Override
    protected Call<ApiResponse<RentCar>> makeRequest() {
        return ApiRepository.apiService.addRent(rentCar);
    }
}
