package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.utils.SingleLiveEvent;

public class UpdateCarViewModel extends ViewModel {
    private SingleLiveEvent<Boolean> liveEvent;

    public UpdateCarViewModel(Car car, int carId) {
        liveEvent = ApiRepository.getInstance().updateCar(car, carId);
    }

    public SingleLiveEvent<Boolean> getLiveEvent() {
        return liveEvent;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Car car;
        private int carId;

        public Factory(Car car, int carId) {
            this.car = car;
            this.carId = carId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UpdateCarViewModel(car, carId);
        }
    }
}
