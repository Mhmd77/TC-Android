package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.service.repository.ApiRepository;

public class UserViewModel extends ViewModel {
    private final LiveData<User> userObservableData;

    public UserViewModel(String username, String password) {
        userObservableData = ApiRepository.getInstance().loginUser(username, password);
    }

    public LiveData<User> getObservableUser() {
        return userObservableData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final String username;
        private final String password;

        public Factory(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new UserViewModel(username, password);
        }
    }
}
