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
        this.locations = ApiRepository.getInstance().getLocations();
    }
    public MutableLiveData<List<String>> getLocations(){
        return this.locations;
    }
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
       public Factory(){}
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new RentViewModel();
        }
    }
}
