package com.myapps.tc_android.service.repository;


import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.utils.SingleLiveEvent;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private static ApiRepository apiRepository;
    protected static ApiService apiService;

    public static String getBaseUrl() {
        return ApiService.BASE_URL;
    }

    private ApiRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
                .addInterceptor(new AuthorizationInterceptor())
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

    public void loginUser(MutableLiveData<User> data, String username, String password) {
        SignInInteractor signIn = new SignInInteractor(username, password);
        signIn.doRequest(data);
    }

    public void signUpUser(MutableLiveData<User> data, String username, String password, String identificationId, String email) {
        SignUpInteractor signUp = new SignUpInteractor(username, password, identificationId, email);
        signUp.doRequest(data);
    }

    public void getListCars(MutableLiveData<List<Car>> data) {
        GetListCarInteractor getAllCars = new GetListCarInteractor(false);
        getAllCars.doRequest(data);
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

    public void getCar(MutableLiveData<Car> data, int id) {
        GetCarInteractor getCarInteractor = new GetCarInteractor(id);
        getCarInteractor.doRequest(data);
    }

    public MutableLiveData<Boolean> deleteCar(int carId) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        apiService.deleteCar(carId).enqueue(new ApiCallback<Object>() {
            @Override
            protected void handleResponseData(Object data) {
                Log.i("Delete Car", "Succussfully");
                liveData.setValue(true);
            }

            @Override
            protected void handleError(Response<ApiResponse<Object>> response) {
                Log.e("Request Error", "Error failed with code: " + response.code() + " and message : " + response.message());
                liveData.setValue(false);
            }

            @Override
            protected void handleException(Exception t) {
                liveData.setValue(false);
                t.printStackTrace();
            }
        });
        return liveData;
    }

    public MutableLiveData<Boolean> deleteUser(int userId) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        apiService.deleteUser(userId).enqueue(new ApiCallback<Object>() {
            @Override
            protected void handleResponseData(Object data) {
                Log.i("Delete User", "Succussfully");
                liveData.setValue(true);
            }

            @Override
            protected void handleError(Response<ApiResponse<Object>> response) {
                Log.e("Request Error", "Error failed with code: " + response.code() + " and message : " + response.message());
                liveData.setValue(false);
            }

            @Override
            protected void handleException(Exception t) {
                liveData.setValue(false);
                t.printStackTrace();
            }
        });
        return liveData;

    }

    public void addCar(MutableLiveData<Car> data, Car car) {
        AddCarInterceptor interceptor = new AddCarInterceptor(car);
        interceptor.doRequest(data);
    }

    public void uploadImage(final SingleLiveEvent<Boolean> liveEvent, MultipartBody.Part image, int carID) {
        apiService.uploadImage(image, carID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    liveEvent.setValue(true);
                } else {
                    liveEvent.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                liveEvent.setValue(false);
            }
        });
    }

    public SingleLiveEvent<Boolean> updateCar(Car car, int carId) {
        final SingleLiveEvent<Boolean> liveEvent = new SingleLiveEvent();
        apiService.updateCar(car, carId).enqueue(new ApiCallback<Car>() {
            @Override
            protected void handleResponseData(Car data) {
                liveEvent.setValue(true);
            }

            @Override
            protected void handleError(Response<ApiResponse<Car>> response) {
                liveEvent.setValue(false);
                Log.e("Request Error", "Error failed with code: " + response.code() + " and message : " + response.message());
            }

            @Override
            protected void handleException(Exception t) {
                liveEvent.setValue(false);
                t.printStackTrace();
            }
        });
        return liveEvent;
    }

    public void getListCarUser(MutableLiveData<List<Car>> data) {
        GetListCarInteractor getAllCars = new GetListCarInteractor(true);
        getAllCars.doRequest(data);
    }

    public void getListUsers(MutableLiveData<List<User>> data) {
        GetListUserInteractor interactor = new GetListUserInteractor();
        interactor.doRequest(data);
    }

    public void addRent(MutableLiveData<RentCar> data, RentCar rentCar) {
        AcceptRentCarInterceptor interceptor = new AcceptRentCarInterceptor(rentCar);
        interceptor.doRequest(data);
    }

    public void getLocations(MutableLiveData<List<String>> data) {
        GetLocationsInterceptor getLocations = new GetLocationsInterceptor();
        getLocations.doRequest(data);
    }

    public void getRentUser(MutableLiveData<RentCar> data) {
        GetRentInteractor getRentUser = new GetRentInteractor();
        getRentUser.doRequest(data);
    }


}
