package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.repository.ApiRepository;

public class AcceptRentViewModel extends ViewModel {
    private final MutableLiveData<RentCar> rentObservableData;

    public AcceptRentViewModel() {
        this.rentObservableData = new MutableLiveData<>();
    }

    public LiveData<RentCar> getRentObservableData() {
        return rentObservableData;
    }

    public void rentCar(RentCar rentCar) {
        ApiRepository.getInstance().addRent(rentObservableData, rentCar);
    }
}
