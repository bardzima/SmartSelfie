package com.temobard.smartselfie.framework.tracker;

import com.google.android.gms.vision.MultiProcessor;
import com.temobard.smartselfie.data.sources.FaceDetector;
import com.temobard.smartselfie.domain.Face;
import com.temobard.smartselfie.domain.Frame;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Face detector class
 */
public class SelfieFaceDetector implements FaceDetector {

    private static final String TAG = "SelfieFaceDetector";

    private com.google.android.gms.vision.face.FaceDetector detector;

    @Inject
    public SelfieFaceDetector(com.google.android.gms.vision.face.FaceDetector detector) {
        this.detector = detector;
    }

    /**
     * @return Face observable with a frame when detected or null frame when not
     */
    public Observable<com.temobard.smartselfie.domain.Face> getFace() {
        return Observable.create(emitter -> detector.setProcessor(new MultiProcessor.Builder<>(
                new SelfieFaceTrackerFactory(new CameraFaceTracker.OnCameraFaceTrackerListener() {
                    @Override
                    public void onFaceUpdated(com.google.android.gms.vision.face.Face face) {
                        emitter.onNext(convertToDomainFace(face));
                    }

                    @Override
                    public void onFaceDisappeared() {
                        emitter.onNext(new Face(null));
                    }
                }))
                .build()));
    }

    private Face convertToDomainFace(com.google.android.gms.vision.face.Face face) {
        int left = (int) face.getPosition().x;
        int top = (int) face.getPosition().y;
        int right = (int) (left + face.getWidth());
        int bottom = (int) (top + face.getHeight());
        return new Face(new Frame(left, top, right, bottom), face.getEulerY(), face.getEulerZ());
    }
}