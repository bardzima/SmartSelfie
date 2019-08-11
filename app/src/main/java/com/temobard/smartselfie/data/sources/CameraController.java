package com.temobard.smartselfie.data.sources;

import android.graphics.Bitmap;

import io.reactivex.Single;

public interface CameraController {
    void start();
    void stop();
    Single<Bitmap> snap();
}
