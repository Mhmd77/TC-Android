package com.myapps.tc_android.service.repository;

import android.arch.lifecycle.LiveData;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.LoginInfo;
import com.myapps.tc_android.service.model.User;

import retrofit2.Call;

public class SignInInteractor extends GenericRequestHandler<User> {
    private String username;
    private String password;

    public SignInInteractor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Call<ApiResponse<User>> makeRequest() {
        return ApiRepository.apiService.loginUser(new LoginInfo(username, password));
    }
}
