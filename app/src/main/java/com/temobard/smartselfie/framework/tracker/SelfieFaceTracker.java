package com.temobard.smartselfie.framework.tracker;

import android.util.Log;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.domain.Face;
import com.temobard.smartselfie.domain.Frame;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SelfieFaceTracker implements FaceTracker {

    private static final String TAG = "SelfieFaceTracker";

    private FaceDetector detector;

    @Inject
    public SelfieFaceTracker(FaceDetector detector) {
        this.detector = detector;
    }

    public Observable<com.temobard.smartselfie.domain.Face> getFace() {
        return Observable.create(emitter -> detector.setProcessor(new MultiProcessor.Builder<>(
                new SelfieFaceTrackerFactory(new CameraFaceTracker.OnCameraFaceTrackerListener() {
                    @Override
                    public void onFaceUpdated(com.google.android.gms.vision.face.Face face) {
                        Face face_domain = new Face(getFaceFrame(face), face.getEulerY(), face.getEulerZ());
                        emitter.onNext(face_domain);
                    }

                    @Override
                    public void onFaceDisappeared() {
                        emitter.onNext(new Face(null));
                    }
                }))
                .build()));
    }

    private Frame getFaceFrame(com.google.android.gms.vision.face.Face face) {

        Log.d(TAG, "EulerY = " + face.getEulerY());
        Log.d(TAG, "EulerZ = " + face.getEulerZ());
        Log.d(TAG, "Position = " + face.getPosition());


        int left = (int) face.getPosition().x;
        int top = (int) face.getPosition().y;
        int right = (int) (left + face.getWidth());
        int bottom = (int) (top + face.getHeight());

        return new Frame(left, top, right, bottom);
    }
}