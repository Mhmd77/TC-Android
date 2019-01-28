package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.repository.ApiRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Car>> listSearchObservableData;

    public SearchViewModel() {
        this.listSearchObservableData = new MutableLiveData<>();
    }

    public LiveData<List<Car>> getListSearchObservableData() {
        return listSearchObservableData;
    }

    public void searchCars(String query) {
        ApiRepository.getInstance().searchCars(listSearchObservableData, query);
    }
}
