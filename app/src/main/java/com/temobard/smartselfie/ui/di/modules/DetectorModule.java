package com.temobard.smartselfie.ui.di.modules;

import android.content.Context;

import com.google.android.gms.vision.face.FaceDetector;
import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.framework.tracker.SelfieFaceTracker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DetectorModule {

    @Singleton
    @Provides
    FaceDetector provideDetector(Context context) {
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        return detector;
    }

    @Singleton
    @Provides
    FaceTracker providesFaceTracker(FaceDetector detector) {
        return new SelfieFaceTracker(detector);
    }

    @Singleton
    @Provides
    FramedFaceProcessor providesFramedFaceProcessor(FaceTracker tracker) {
        return new FramedFaceProcessor(tracker);
    }
}
