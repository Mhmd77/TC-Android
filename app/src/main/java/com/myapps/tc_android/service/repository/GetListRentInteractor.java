package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCar;

import java.util.List;

import retrofit2.Call;

public class GetListRentInteractor extends GenericRequestHandler<List<RentCar>> {
    @Override
    protected Call<ApiResponse<List<RentCar>>> makeRequest() {
        return ApiRepository.apiService.getRentUser();
    }
}
