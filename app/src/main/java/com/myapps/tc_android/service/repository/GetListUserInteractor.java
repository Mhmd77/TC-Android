package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.User;

import java.util.List;

import retrofit2.Call;

public class GetListUserInteractor extends GenericRequestHandler<List<User>> {
    @Override
    protected Call<ApiResponse<List<User>>> makeRequest() {
        return ApiRepository.apiService.getAllUsers();
    }
}
