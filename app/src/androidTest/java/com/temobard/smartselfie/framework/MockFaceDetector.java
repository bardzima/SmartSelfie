package com.temobard.smartselfie.framework;

import com.temobard.smartselfie.data.sources.FaceDetector;
import com.temobard.smartselfie.domain.Face;
import com.temobard.smartselfie.domain.Frame;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class MockFaceDetector implements FaceDetector {
    private static Frame faceFrame = null;

    public static void setFaceFrame(Frame frame) {
        MockFaceDetector.faceFrame = frame;
    }

    @Override
    public Observable<Face> getFace() {
        return Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(aLong -> new Face(faceFrame));
    }
}
