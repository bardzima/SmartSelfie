package com.temobard.smartselfie.di.modules;

import android.content.Context;

import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.data.sources.FaceDetector;
import com.temobard.smartselfie.framework.MockFaceDetector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockDetectorModule {

    @Singleton
    @Provides
    com.google.android.gms.vision.face.FaceDetector provideDetector(Context context) {
        return new com.google.android.gms.vision.face.FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                .setMode(com.google.android.gms.vision.face.FaceDetector.FAST_MODE)
                .setClassificationType(com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS)
                .build();
    }

    @Singleton
    @Provides
    FaceDetector providesFaceTracker() {
        return new MockFaceDetector();
    }

    @Singleton
    @Provides
    FramedFaceProcessor providesFramedFaceProcessor(FaceDetector tracker) {
        return new FramedFaceProcessor(tracker);
    }
}
