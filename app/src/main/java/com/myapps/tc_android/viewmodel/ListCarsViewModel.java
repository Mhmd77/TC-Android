package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.repository.ApiRepository;

import java.util.List;

public class ListCarsViewModel extends ViewModel {
    private MutableLiveData<List<Car>> listCarsObservableData;

    public ListCarsViewModel(boolean isUserCar) {
        if (isUserCar) {
            this.listCarsObservableData = ApiRepository.getInstance().getListCarUser();
        } else {
            this.listCarsObservableData = ApiRepository.getInstance().getListCars();
        }
    }

    public LiveData<List<Car>> getListCarsObservableData() {
        return listCarsObservableData;
    }

    public void sortListOfCars(String field, int ascending) {
        ApiRepository.getInstance().sortListCars(listCarsObservableData, field, ascending);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private boolean isUserCar;

        public Factory(boolean isUserCar) {
            this.isUserCar = isUserCar;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ListCarsViewModel(isUserCar);
        }
    }
}
