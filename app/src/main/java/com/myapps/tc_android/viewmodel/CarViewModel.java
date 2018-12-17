package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.repository.ApiRepository;

public class CarViewModel extends ViewModel {
    final LiveData<Car> carObservableData;

    public CarViewModel(int carId) {
        carObservableData = ApiRepository.getInstance().getCar(carId);
    }

    public LiveData<Car> getCarObservableData() {
        return carObservableData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private int carId;

        public Factory(int carId) {
            this.carId = carId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CarViewModel(carId);
        }
    }
}
