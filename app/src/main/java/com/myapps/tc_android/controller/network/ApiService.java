package com.sqtehrancar.tehrancarandroid.contorller.network;

import com.sqtehrancar.tehrancarandroid.model.Car;
import com.sqtehrancar.tehrancarandroid.model.LoginInfo;
import com.sqtehrancar.tehrancarandroid.model.ResponseJson;
import com.sqtehrancar.tehrancarandroid.model.User;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @POST("/api/login")
    Call<User> loginUser(@Body LoginInfo user);

    @GET("/api/listCar")
    Call<List<Car>> getAllCars();

    @DELETE("/api/deleteCar/{id}")
    Call<ResponseJson> deleteCar(@Path("id") int id);

    @POST("/api/addCar")
    Call<Car> addCar(@Body Car car);
}