package com.temobard.smartselfie.ui.di.modules;

import android.content.Context;

import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.data.sources.FaceDetector;
import com.temobard.smartselfie.framework.tracker.SelfieFaceDetector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DetectorModule {

    @Singleton
    @Provides
    com.google.android.gms.vision.face.FaceDetector provideDetector(Context context) {
        com.google.android.gms.vision.face.FaceDetector detector = new com.google.android.gms.vision.face.FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                .setMode(com.google.android.gms.vision.face.FaceDetector.FAST_MODE)
                .setClassificationType(com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        return detector;
    }

    @Singleton
    @Provides
    FaceDetector providesFaceTracker(com.google.android.gms.vision.face.FaceDetector detector) {
        return new SelfieFaceDetector(detector);
    }

    @Singleton
    @Provides
    FramedFaceProcessor providesFramedFaceProcessor(FaceDetector tracker) {
        return new FramedFaceProcessor(tracker);
    }
}
