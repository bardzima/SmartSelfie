package com.temobard.smartselfie.framework.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.data.sources.CameraController;

import io.reactivex.Single;

public class SelfieCameraController implements CameraController {

    private CameraSource cameraSource;

    public SelfieCameraController(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
    }

    @Override
    public Single<Bitmap> snap() {
        return Single.create(emitter -> cameraSource.takePicture(null, bytes -> {
            emitter.onSuccess(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }));
    }
}
