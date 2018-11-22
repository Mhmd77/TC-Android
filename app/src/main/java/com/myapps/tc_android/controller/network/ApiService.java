package com.myapps.tc_android.controller.network;

import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.model.LoginInfo;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.SignUpInfo;
import com.myapps.tc_android.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService<C> {


    @POST("/api/v1/auth/login")
    Call<ApiResponse<User>> loginUser(@Body LoginInfo user);

    @GET("/api/v1/cars")
    Call<ApiResponse<List<Car>>> getAllCars();

    @DELETE("/api/v1/cars/{id}")
    Call<ApiResponse<Object>> deleteCar(@Path("id") int id);

    @POST("/api/v1/cars")
    Call<Car> addCar(@Body Car car);

    @POST("/api/v1/auth/signup")
    Call<ApiResponse<User>> signUpUser(@Body SignUpInfo user);

}