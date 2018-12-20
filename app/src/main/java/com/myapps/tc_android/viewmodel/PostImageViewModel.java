package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.utils.SingleLiveEvent;

import okhttp3.MultipartBody;

public class PostImageViewModel extends ViewModel {
    private SingleLiveEvent<Boolean> liveEvent;

    public PostImageViewModel(MultipartBody.Part image, int carId) {
        this.liveEvent = ApiRepository.getInstance().uploadImage(image, carId);
    }

    public SingleLiveEvent<Boolean> getLiveEvent() {
        return liveEvent;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private int carId;
        private MultipartBody.Part image;

        public Factory(MultipartBody.Part image, int carId) {
            this.carId = carId;
            this.image = image;
        }

        @NonNull
        @Override

        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PostImageViewModel(image, carId);
        }
    }
}
