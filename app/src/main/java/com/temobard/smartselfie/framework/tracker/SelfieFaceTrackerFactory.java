package com.temobard.smartselfie.framework.tracker;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

class SelfieFaceTrackerFactory implements MultiProcessor.Factory<Face> {

    private CameraFaceTracker.OnCameraFaceTrackerListener listener;

    SelfieFaceTrackerFactory(CameraFaceTracker.OnCameraFaceTrackerListener listener) {
        this.listener = listener;
    }

    @Override
    public Tracker<Face> create(Face face) {
        return new CameraFaceTracker(listener);
    }
}