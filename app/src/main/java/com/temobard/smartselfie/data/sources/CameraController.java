package com.temobard.smartselfie.data.sources;

import android.graphics.Bitmap;

import io.reactivex.Single;

public interface CameraController {
    Single<Bitmap> snap();
}
