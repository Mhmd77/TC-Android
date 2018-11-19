package com.myapps.tc_android.controller.network;

import com.myapps.tc_android.model.LoginInfo;
import com.myapps.tc_android.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    @POST("/api/v1/auth/login")
    Call<LoginResponse> loginUser(@Body LoginInfo user);

}