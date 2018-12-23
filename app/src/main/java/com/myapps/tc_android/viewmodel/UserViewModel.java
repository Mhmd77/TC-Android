package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.service.repository.ApiRepository;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> userObservableData;

    public UserViewModel() {
        userObservableData = new MutableLiveData<>();
    }

    public LiveData<User> getObservableUser() {
        return userObservableData;
    }

    public void loginUser(String username, String password) {
        ApiRepository.getInstance().loginUser(userObservableData, username, password);
    }

    public void signUpUser(String username, String password, String identificationId, String email) {
        ApiRepository.getInstance().signUpUser(userObservableData, username, password, identificationId, email);
    }
}
