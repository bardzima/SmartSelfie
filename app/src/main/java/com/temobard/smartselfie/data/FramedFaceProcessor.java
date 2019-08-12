package com.temobard.smartselfie.data;

import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.domain.CameraFrame;
import com.temobard.smartselfie.domain.Frame;

import io.reactivex.Observable;

public class FramedFaceProcessor {

    private static final int FACE_ANGLE_LIMIT = 10;
    private static final float FACE_BOUNDARY_TOLERANCE = 0.1F;

    private FaceTracker faceTracker;

    private Frame frame = new Frame();
    private CameraFrame cameraFrame;

    public FramedFaceProcessor(FaceTracker faceTracker) {
        this.faceTracker = faceTracker;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void setCameraFrame(CameraFrame cameraFrame) {
        this.cameraFrame = cameraFrame;
    }

    public Observable<Boolean> getFaceFramed() {
        return faceTracker.getFace().
                map(face -> {
                    if (frame == null) {
                        return false;
                    } else {
                        Frame scaledFaceFrame = getScaledFaceFrame(face.getBoundary());
                        return scaledFaceFrame != null && scaledFaceFrame.isInsideFrame(frame, FACE_BOUNDARY_TOLERANCE) &&
                                face.getEulerY() < FACE_ANGLE_LIMIT && face.getEulerY() > -FACE_ANGLE_LIMIT &&
                                face.getEulerZ() < FACE_ANGLE_LIMIT && face.getEulerZ() > -FACE_ANGLE_LIMIT;
                    }
                });
    }

    private Frame getScaledFaceFrame(Frame faceFrame) {
        if (cameraFrame == null || faceFrame == null) return null;
        return cameraFrame.scale(faceFrame);
    }
}
