package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.service.repository.ApiRepository;

import java.util.List;

public class ListUserViewModel extends ViewModel {
    private MutableLiveData<List<User>> listCarsObservableData;

    public ListUserViewModel() {
        listCarsObservableData = ApiRepository.getInstance().getListUsers();
    }

    public LiveData<List<User>> getListCarsObservableData() {
        return listCarsObservableData;
    }
}
