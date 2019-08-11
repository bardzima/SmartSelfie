package com.temobard.smartselfie.data;

import com.temobard.smartselfie.data.sources.FaceTracker;
import com.temobard.smartselfie.domain.Frame;

import io.reactivex.Observable;

public class FramedFaceProcessor {

    private static final int FACE_ANGLE_LIMIT = 10;
    private static final float FACE_BOUNDARY_TOLERANCE = 0.1F;

    private FaceTracker faceTracker;

    private Frame frame;
    private Frame cameraFrame;
    private float scale;

    public FramedFaceProcessor(FaceTracker faceTracker) {
        this.faceTracker = faceTracker;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void setCameraFrame(Frame cameraFrame, float scale) {
        this.cameraFrame = cameraFrame;
        this.scale = scale;
    }

    public Observable<Boolean> getFaceFramed() {
        return faceTracker.getFace().
                map(face -> {
                    if (frame == null) return false;
                    else {
                        Frame faceFrame = getTrueFaceFrame(face.getBoundary());
                        return faceFrame != null && faceFrame.isInsideFrame(frame, FACE_BOUNDARY_TOLERANCE) &&
                                face.getEulerY() < FACE_ANGLE_LIMIT && face.getEulerY() > -FACE_ANGLE_LIMIT &&
                                face.getEulerZ() < FACE_ANGLE_LIMIT && face.getEulerZ() > -FACE_ANGLE_LIMIT;
                    }
                });
    }

    private Frame getTrueFaceFrame(Frame faceFrame) {
        if(cameraFrame == null || faceFrame == null) return null;
        return faceFrame.translate(cameraFrame, scale);
    }
}
