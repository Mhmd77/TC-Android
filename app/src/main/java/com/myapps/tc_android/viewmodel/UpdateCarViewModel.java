package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.utils.SingleLiveEvent;

public class UpdateCarViewModel extends ViewModel {
    private SingleLiveEvent<Boolean> liveEvent;

    public UpdateCarViewModel() {
        liveEvent = new SingleLiveEvent<>();
    }

    public SingleLiveEvent<Boolean> getLiveEvent() {
        return liveEvent;
    }

    public void updateCar(Car newCar, int carId) {
        ApiRepository.getInstance().updateCar(newCar, carId);
    }
}
