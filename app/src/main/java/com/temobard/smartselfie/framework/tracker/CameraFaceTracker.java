package com.temobard.smartselfie.framework.tracker;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Android's camera source face tracker
 */
class CameraFaceTracker extends Tracker<Face> {

    interface OnCameraFaceTrackerListener {
        void onFaceUpdated(Face face);
        void onFaceDisappeared();
    }

    private OnCameraFaceTrackerListener listener;

    CameraFaceTracker(OnCameraFaceTrackerListener listener) {
        this.listener = listener;
    }

    /**
     * Start tracking the detected face instance within the face overlay.
     */
    @Override
    public void onNewItem(int faceId, Face item) {
    }

    /**
     * Update the position/characteristics of the face within the overlay.
     */
    @Override
    public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
        if(listener != null)
            listener.onFaceUpdated(face);
    }

    /**
     * Hide the graphic when the corresponding face was not detected.  This can happen for
     * intermediate frames temporarily (e.g., if the face was momentarily blocked from
     * view).
     */
    @Override
    public void onMissing(FaceDetector.Detections<Face> detectionResults) {
        if(listener != null)
            listener.onFaceDisappeared();
    }

    /**
     * Called when the face is assumed to be gone for good. Remove the graphic annotation from
     * the overlay.
     */
    @Override
    public void onDone() {
        if(listener != null)
            listener.onFaceDisappeared();
    }
}