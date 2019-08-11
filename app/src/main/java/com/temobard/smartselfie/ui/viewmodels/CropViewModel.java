package com.temobard.smartselfie.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

public class CropViewModel extends BaseViewModel {

    @Inject
    public CropViewModel() {
    }

    private MutableLiveData<String> selfiePathSetter = new MutableLiveData<>();

    public LiveData<String> getSelfiePath() {
        return selfiePathSetter;
    }

    public void setSelfiePath(String path) {
        selfiePathSetter.setValue(path);
    }
}
