package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.repository.ApiRepository;

public class CarViewModel extends ViewModel {
    private final MutableLiveData<Car> carObservableData;

    public CarViewModel() {
        carObservableData = new MutableLiveData<>();
    }

    public LiveData<Car> getCarObservableData() {
        return carObservableData;
    }

    public void addCar(Car car) {
        ApiRepository.getInstance().addCar(carObservableData, car);
    }
}
