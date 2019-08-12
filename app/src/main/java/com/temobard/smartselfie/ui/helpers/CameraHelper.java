package com.temobard.smartselfie.ui.helpers;

import com.google.android.gms.common.images.Size;
import com.temobard.smartselfie.domain.CameraFrame;
import com.temobard.smartselfie.domain.Frame;

public class CameraHelper {

    public static CameraFrame getAdjustedCameraFrame(Size cameraSize, Frame viewFrame, boolean portrait) {
        int width = 320;
        int height = 240;

        if(cameraSize != null) {
            width = cameraSize.getWidth();
            height = cameraSize.getHeight();
        }

        // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
        if (portrait) {
            int tmp = width;
            width = height;
            height = tmp;
        }

        final int viewWidth = viewFrame.getRight() - viewFrame.getLeft();
        final int viewHeight = viewFrame.getBottom() - viewFrame.getTop();

        // Fill the screen
        int camWidth, camHeight, camX, camY;
        float scale;
        if (portrait) {
            scale = ((float) viewHeight / (float) height);
            camWidth = (int) (scale * width);
            camHeight = viewHeight;
            camY = 0;
            camX = (viewWidth - camWidth) / 2;
        } else {
            scale = (float) viewWidth / (float) width;
            camWidth = viewWidth;
            camHeight = (int) (scale * height);
            camX = 0;
            camY = (viewHeight - camHeight) / 2;
        }

        return new CameraFrame(camX, camY, camX + camWidth, camY + camHeight, scale);
    }
}
