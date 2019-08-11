package com.temobard.smartselfie.ui.helpers;

import android.graphics.Rect;
import com.google.android.gms.common.images.Size;
import com.temobard.smartselfie.domain.Frame;

public class CameraHelper {

    public static Frame getAdjustedCameraFrame(Size cameraSize, Rect viewFrame, boolean portrait) {
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

        final int layoutWidth = viewFrame.right - viewFrame.left;
        final int layoutHeight = viewFrame.bottom - viewFrame.top;

        // Fill the screen
        int camWidth, camHeight, camX, camY;
        float scale;
        if (portrait) {
            scale = ((float) layoutHeight / (float) height);
            camWidth = (int) (scale * width);
            camHeight = layoutHeight;
            camY = 0;
            camX = (layoutWidth - camWidth) / 2;
        } else {
            scale = (float) layoutWidth / (float) width;
            camWidth = layoutWidth;
            camHeight = (int) (scale * height);
            camX = 0;
            camY = (layoutHeight - camHeight) / 2;
        }

        return new Frame(camX, camY, camX + camWidth, camY + camHeight, scale);
    }
}
