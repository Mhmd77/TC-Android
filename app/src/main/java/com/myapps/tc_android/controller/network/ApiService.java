package com.myapps.tc_android.controller.network;

import com.myapps.tc_android.model.LoginInfo;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    @POST("/api/v1/auth/login")
    Call<ApiResponse<User>> loginUser(@Body LoginInfo user);

    @POST("/api/v1/auth/signup")
    Call<ApiResponse<User>> signUpUser(@Body User user);

}