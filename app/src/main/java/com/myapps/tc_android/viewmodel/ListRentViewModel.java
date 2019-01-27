package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.repository.ApiRepository;

import java.util.List;

public class ListRentViewModel extends ViewModel {
    private MutableLiveData<List<RentCar>> listRentsObservableData;

    public ListRentViewModel() {this.listRentsObservableData = new MutableLiveData<>();}

    public LiveData<List<RentCar>> getRentObservableData() {
        return listRentsObservableData;
    }

    public void getRentUser() {
        ApiRepository.getInstance().getRentUser(listRentsObservableData);
    }
}
