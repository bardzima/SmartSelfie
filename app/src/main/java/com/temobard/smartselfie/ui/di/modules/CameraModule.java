package com.temobard.smartselfie.ui.di.modules;

import android.content.Context;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;
import com.temobard.smartselfie.data.sources.CameraController;
import com.temobard.smartselfie.framework.camera.SelfieCameraController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraModule {

    @Singleton
    @Provides
    CameraSource providesCameraSource(Context context, FaceDetector detector) {
        return new CameraSource.Builder(context, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30.0f)
                .build();
    }

    @Singleton
    @Provides
    CameraController providesCameraController(CameraSource cameraSource) {
        return new SelfieCameraController(cameraSource);
    }
}
