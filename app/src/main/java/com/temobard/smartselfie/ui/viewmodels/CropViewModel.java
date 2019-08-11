package com.temobard.smartselfie.ui.viewmodels;

import javax.inject.Inject;

public class CropViewModel extends BaseViewModel {

    @Inject
    public CropViewModel() {
    }

    private String selfiePathSetter;

    public String getSelfiePath() {
        return selfiePathSetter;
    }

    public void setSelfiePath(String path) {
        selfiePathSetter = path;
    }
}
