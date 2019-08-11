package com.temobard.smartselfie.ui.interfaces;

import com.google.android.gms.vision.CameraSource;

public interface CameraView {
    boolean start(CameraSource cameraSource);
    void stop();
}
