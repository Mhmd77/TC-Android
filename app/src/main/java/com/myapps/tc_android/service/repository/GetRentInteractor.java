package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.RentCar;


import retrofit2.Call;

public class GetRentInteractor extends GenericRequestHandler<RentCar> {
    @Override
    protected Call<ApiResponse<RentCar>> makeRequest() {
        return ApiRepository.apiService.getRentUser();
    }
}
