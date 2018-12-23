package com.myapps.tc_android.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.utils.SingleLiveEvent;

import okhttp3.MultipartBody;

public class PostImageViewModel extends ViewModel {
    private SingleLiveEvent<Boolean> liveEvent;

    public PostImageViewModel() {
        this.liveEvent = new SingleLiveEvent<>();
    }

    public SingleLiveEvent<Boolean> getLiveEvent() {
        return liveEvent;
    }

    public void postImage(MultipartBody.Part image, int carId) {
        ApiRepository.getInstance().uploadImage(liveEvent, image, carId);
    }
}
