package com.temobard.smartselfie.framework.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.data.sources.CameraController;

import io.reactivex.Single;

/**
 * Android's camera controller class
 * Starts, stops, snaps, and disposes of camera source
 */
public class SelfieCameraController implements CameraController {

    private CameraSource cameraSource;

    public SelfieCameraController(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
    }

    @Override
    public void start() {
        // Do nothing here. CameraSource needs a SurfaceHolder to reflect the image.
    }

    @Override
    public void stop() {
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    @Override
    public void dispose() {
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    @Override
    public Single<Bitmap> snap() {
        return Single.create(emitter -> cameraSource.takePicture(null, bytes -> {
            emitter.onSuccess(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }));
    }
}
