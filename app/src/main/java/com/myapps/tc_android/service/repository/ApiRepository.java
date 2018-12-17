package com.myapps.tc_android.service.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.myapps.tc_android.controller.Utils;
import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.LoginInfo;
import com.myapps.tc_android.service.model.SignUpInfo;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.view.activities.SignUpActivity;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private static ApiRepository apiRepository;
    private ApiService apiService;

    public static String getBaseUrl() {
        return ApiService.BASE_URL;
    }

    private ApiRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);

    }

    public synchronized static ApiRepository getInstance() {
        if (apiRepository == null) {
            if (apiRepository == null) {
                apiRepository = new ApiRepository();
            }
        }
        return apiRepository;
    }

    public LiveData<User> loginUser(String username, String password) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        apiService.loginUser(new LoginInfo(username, password)).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getObject());
                } else {
                    Log.e("Login User Error", "Login failed with code: " + response.code());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                t.printStackTrace();
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<User> signUpUser(String username, String password, String identificationId, String email) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        SignUpInfo info = new SignUpInfo(username, password,
                identificationId, email);

        apiService.signUpUser(info).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getObject());
                } else {
                    Log.e("Sign Up User Error", "Sign Up failed with code: " + response.code());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                data.setValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    public MutableLiveData<List<Car>> getListCars() {
        final MutableLiveData<List<Car>> data = new MutableLiveData<>();
        apiService.getAllCars().enqueue(new Callback<ApiResponse<List<Car>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Car>>> call, @NonNull Response<ApiResponse<List<Car>>> response) {
                simulateDelay();
                if (response.isSuccessful()) {
                    data.setValue(response.body().getObject());
                } else {
                    Log.e("Get List Of Cars Error", "Get List Of Cars failed with code: " + response.code());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Car>>> call, @NonNull Throwable t) {
                data.setValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    public void sortListCars(final MutableLiveData<List<Car>> data, String field, int ascending) {
        apiService.sortCars(field, ascending).enqueue(new Callback<ApiResponse<List<Car>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Car>>> call, Response<ApiResponse<List<Car>>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getObject());
                } else {
                    Log.e("Sort List Of Cars Error", "Sort List Of Cars failed with code: " + response.code());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Car>>> call, Throwable t) {
                data.setValue(null);
                t.printStackTrace();
            }
        });

    }

    public LiveData<Car> getCar(int id) {
        final MutableLiveData<Car> data = new MutableLiveData<>();
        apiService.getCar(id).enqueue(new Callback<ApiResponse<Car>>() {
            @Override
            public void onResponse(Call<ApiResponse<Car>> call, Response<ApiResponse<Car>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getObject());
                } else {
                    Log.e("Get Car Error", "Get Car failed with code: " + response.code());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Car>> call, Throwable t) {
                data.setValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}