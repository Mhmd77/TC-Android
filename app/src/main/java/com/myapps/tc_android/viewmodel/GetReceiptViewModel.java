package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.repository.ApiRepository;

public class GetRentViewModel extends ViewModel {
    private MutableLiveData<RentCar> RentsObservableData;

    public GetRentViewModel() {
        this.RentsObservableData = new MutableLiveData<>();
    }

    public LiveData<RentCar> getRentObservableData() {
        return RentsObservableData;
    }

    public void getRentUser() {
        ApiRepository.getInstance().getRentUser(RentsObservableData);
    }
}

