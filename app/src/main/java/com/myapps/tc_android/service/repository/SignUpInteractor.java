package com.myapps.tc_android.service.repository;

import android.arch.lifecycle.LiveData;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.LoginInfo;
import com.myapps.tc_android.service.model.SignUpInfo;
import com.myapps.tc_android.service.model.User;

import retrofit2.Call;

public class SignUpInteractor extends GenericRequestHandler<User> {
    private String username;
    private String password;
    private String identificationId;
    private String email;

    public SignUpInteractor(String username, String password, String identificationId, String email) {
        this.username = username;
        this.password = password;
        this.identificationId = identificationId;
        this.email = email;
    }

    @Override
    protected Call<ApiResponse<User>> makeRequest() {
        return ApiRepository.apiService.signUpUser(new SignUpInfo(username, password, identificationId, email));
    }
}
