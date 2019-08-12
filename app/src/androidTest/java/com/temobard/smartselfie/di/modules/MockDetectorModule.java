package com.temobard.smartselfie.di.modules;

import android.content.Context;

import com.google.android.gms.vision.face.FaceDetector;
import com.temobard.smartselfie.data.FramedFaceProcessor;
import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.framework.MockFaceTracker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockDetectorModule {

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
    FaceTracker providesFaceTracker() {
        return new MockFaceTracker();
    }

    @Singleton
    @Provides
    FramedFaceProcessor providesFramedFaceProcessor(FaceTracker tracker) {
        return new FramedFaceProcessor(tracker);
    }
}
