package com.temobard.smartselfie.data;

import com.temobard.smartselfie.data.sources.FaceDetector;
import com.temobard.smartselfie.domain.CameraFrame;
import com.temobard.smartselfie.domain.Frame;

import io.reactivex.Observable;

/**
 * The processor class to calculate if the face is within a frame for a certain camera
 */
public class FramedFaceProcessor {

    private static final int FACE_ANGLE_LIMIT = 10;
    private static final float FACE_BOUNDARY_TOLERANCE = 0.1F;

    private FaceDetector faceDetector;

    private Frame frame = new Frame();
    private CameraFrame cameraFrame;

    public FramedFaceProcessor(FaceDetector faceDetector) {
        this.faceDetector = faceDetector;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void setCameraFrame(CameraFrame cameraFrame) {
        this.cameraFrame = cameraFrame;
    }

    /**
     * Returns the observable for the face within frame detection
     * @return binary observable that indicates if the face is or is not within the frame
     */
    public Observable<Boolean> getFaceFramed() {
        return faceDetector.getFace().
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

    /**
     * Scale the face frame according to the camera frame size and location
     * @param faceFrame
     * @return the scaled face frame
     */
    private Frame getScaledFaceFrame(Frame faceFrame) {
        if (cameraFrame == null || faceFrame == null) return null;
        return cameraFrame.scale(faceFrame);
    }
}
