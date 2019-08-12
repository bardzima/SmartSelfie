package com.temobard.smartselfie.framework;

import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.domain.Face;
import com.temobard.smartselfie.domain.Frame;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class MockFaceTracker implements FaceTracker {
    private static Frame faceFrame = null;

    public static void setFaceFrame(Frame frame) {
        MockFaceTracker.faceFrame = frame;
    }

    @Override
    public Observable<Face> getFace() {
        return Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(aLong -> new Face(faceFrame));
    }
}
