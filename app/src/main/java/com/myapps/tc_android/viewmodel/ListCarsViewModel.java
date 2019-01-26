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

    public ListCarsViewModel() {
        this.listCarsObservableData = new MutableLiveData<>();
    }

    public LiveData<List<Car>> getListCarsObservableData() {
        return listCarsObservableData;
    }

    public void getCars() {
        ApiRepository.getInstance().getListCars(listCarsObservableData);
    }

    public void sortListOfCars(String field, int ascending) {
        ApiRepository.getInstance().sortListCars(listCarsObservableData, field, ascending);
    }

    public void getCarsUser() {
        ApiRepository.getInstance().getListCarUser(listCarsObservableData);
    }
}
