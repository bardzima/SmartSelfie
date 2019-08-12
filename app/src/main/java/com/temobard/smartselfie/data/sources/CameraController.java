package com.temobard.smartselfie.data.sources;

import android.graphics.Bitmap;

import io.reactivex.Single;

/**
 * Camera controller interface
 */
public interface CameraController {
    void start();
    void stop();
    void dispose();
    Single<Bitmap> snap();
}
