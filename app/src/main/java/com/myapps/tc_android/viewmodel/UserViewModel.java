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

    public UserViewModel(String username, String password, String identificationId, String email) {
        userObservableData = ApiRepository.getInstance().signUpUser(username, password, identificationId, email);
    }

    public LiveData<User> getObservableUser() {
        return userObservableData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final String username;
        private final String password;
        private final String identificationId;
        private final String email;

        public Factory(String username, String password) {
            this.username = username;
            this.password = password;
            this.identificationId = "";
            this.email = "";
        }

        public Factory(String username, String password, String identificationId, String email) {
            this.username = username;
            this.password = password;
            this.identificationId = identificationId;
            this.email = email;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (identificationId.length() == 0 || email.length() == 0) {
                return (T) new UserViewModel(username, password);
            } else {
                return (T) new UserViewModel(username, password, identificationId, email);
            }
        }
    }
}
