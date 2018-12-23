package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


import com.myapps.tc_android.service.repository.ApiRepository;

import java.util.List;

public class RentViewModel extends ViewModel {
    private MutableLiveData<List<String>> locations;

    public RentViewModel() {
        this.locations = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getLocationsObservableData() {
        return locations;
    }

    public void getLocations() {
        ApiRepository.getInstance().getLocations(locations);
    }
}
