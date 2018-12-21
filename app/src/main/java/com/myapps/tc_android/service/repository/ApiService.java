package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.LoginInfo;
import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.model.SignUpInfo;
import com.myapps.tc_android.service.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "http://172.17.3.62:5000/";
    String imageApi = "api/v1/images/";

    @POST("/api/v1/users/login")
    Call<ApiResponse<User>> loginUser(@Body LoginInfo user);

    @GET("/api/v1/cars")
    Call<ApiResponse<List<Car>>> getAllCars();

    @GET("/api/v1/users")
    Call<ApiResponse<List<User>>> getAllUsers();

    @DELETE("/api/v1/users/{id}")
    Call<ApiResponse<Object>> deleteUser(@Path("id") int id);

    @DELETE("/api/v1/cars/{id}")
    Call<ApiResponse<Object>> deleteCar(@Path("id") int id);

    @PUT("/api/v1/cars/{id}")
    Call<ApiResponse<Car>> updateCar(@Body Car car, @Path("id") int id);

    @GET("/api/v1/cars/{id}")
    Call<ApiResponse<Car>> getCar(@Path("id") int id);

    @POST("/api/v1/cars")
    Call<ApiResponse<Car>> addCar(@Body Car car);

    @POST("/api/v1/users/signup")
    Call<ApiResponse<User>> signUpUser(@Body SignUpInfo user);

    @GET("/api/v1/sort/cars/{field}/{ascending}")
    Call<ApiResponse<List<Car>>> sortCars(@Path("field") String field, @Path("ascending") int ascending);

    @Multipart
    @POST("/api/v1/images/{carID}")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image, @Path("carID") int carID);

    @GET("/api/v1/cars/user")
    Call<ApiResponse<List<Car>>> getUserCar();

    @POST("/api/v1/rent")
    Call<ApiResponse<RentCar>> addRent(@Body RentCar rentCar);
}
