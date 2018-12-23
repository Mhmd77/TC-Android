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
    private int carId;

    public CarViewModel(int carId) {
        this.carId = carId;
        carObservableData = ApiRepository.getInstance().getCar(carId);
    }

    public CarViewModel(Car car) {
        carObservableData = ApiRepository.getInstance().addCar(car);
    }

    public MutableLiveData<Car> getCarObservableData() {
        return carObservableData;
    }

    public void updateCar() {
        ApiRepository.getInstance().getCar(carObservableData, carId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private int carId;
        private Car car;

        public Factory(int carId) {
            this.carId = carId;
        }

        public Factory(Car car) {
            this.car = car;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (car == null) {
                return (T) new CarViewModel(carId);
            } else {
                return (T) new CarViewModel(car);
            }
        }
    }
}
