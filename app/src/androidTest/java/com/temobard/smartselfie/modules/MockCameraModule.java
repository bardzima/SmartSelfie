package com.temobard.smartselfie.modules;

import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.data.sources.CameraController;
import com.temobard.smartselfie.framework.camera.SelfieCameraController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockCameraModule {

    @Singleton
    @Provides
    CameraController providesCameraController(CameraSource cameraSource) {
        return new SelfieCameraController(cameraSource);
    }
}
