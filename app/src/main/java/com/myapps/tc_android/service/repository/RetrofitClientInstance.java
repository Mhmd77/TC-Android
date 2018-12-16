package com.myapps.tc_android.service.repository;


import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static RetrofitClientInstance retrofitClientInstance;
    private ApiService apiService;
    
    public static String getBaseUrl() {
        return ApiService.BASE_URL;
    }

    private RetrofitClientInstance() {
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

    public synchronized static RetrofitClientInstance getInstance() {
        if (retrofitClientInstance == null) {
            if (retrofitClientInstance == null) {
                retrofitClientInstance = new RetrofitClientInstance();
            }
        }
        return retrofitClientInstance;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}