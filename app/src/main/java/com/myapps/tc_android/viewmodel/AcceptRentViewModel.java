package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.repository.ApiRepository;

public class AcceptRentViewModel extends ViewModel {
    private final MutableLiveData<RentCar> rentObservableData;

    public AcceptRentViewModel(RentCar rentCar) {
        this.rentObservableData = ApiRepository.getInstance().addRent(rentCar);
    }

    public LiveData<RentCar> getRentObservableData() {
        return rentObservableData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private RentCar rentCar;

        public Factory(RentCar rentCar) {
            this.rentCar = rentCar;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AcceptRentViewModel(rentCar);
        }
    }
}
