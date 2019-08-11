package com.temobard.smartselfie.ui.widgets;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

public class CameraView extends ViewGroup {

    private static final String TAG = "CameraContainerView";
    private CameraSurfaceView cameraView;
    private CameraSource cameraSource;

    private Rect cameraFrame = new Rect();
    private Rect layoutRect = new Rect();

    private OnCameraSizeUpdateListener cameraSizeUpdateListener;

    public interface OnCameraSizeUpdateListener {
        void onCameraSizeUpdated(Rect cameraRect, float scale);
    }

    public void setOnCameraSizeUpdateListener(OnCameraSizeUpdateListener cameraSizeUpdateListener) {
        this.cameraSizeUpdateListener = cameraSizeUpdateListener;
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        cameraView = new CameraSurfaceView(context, attrs);
        addView(cameraView);

        cameraView.setSurfaceUpdateListener(() -> {
            calculateSize(cameraView.getCameraSource());
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutRect.left = left;
        layoutRect.right = right;
        layoutRect.top = top;
        layoutRect.bottom = bottom;

        calculateSize(cameraView.getCameraSource());
    }

    public void setCameraSource(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
    }

    public void setCameraStarted(Boolean started) {
        if(started != null && started) cameraView.start(cameraSource);
    }

    private void calculateSize(CameraSource cameraSource) {
        int width = 320;
        int height = 240;
        if (cameraSource != null) {
            Size size = cameraSource.getPreviewSize();
            if (size != null) {
                width = size.getWidth();
                height = size.getHeight();
            }
        }

        // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
        if (isPortraitMode()) {
            int tmp = width;
            width = height;
            height = tmp;
        }

        final int layoutWidth = layoutRect.right - layoutRect.left;
        final int layoutHeight = layoutRect.bottom - layoutRect.top;

        // Fill the screen
        int camWidth, camHeight, camX, camY;
        float scale;
        if (isPortraitMode()) {
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

        cameraView.layout(camX, camY, camWidth, camHeight);
        cameraFrame.left = camX;
        cameraFrame.top = camY;
        cameraFrame.right = camX + camWidth;
        cameraFrame.bottom = camY + camHeight;

        if (cameraSizeUpdateListener != null)
            cameraSizeUpdateListener.onCameraSizeUpdated(cameraFrame, scale);

        Log.d(TAG, "Layout width: " + layoutWidth + ", Layout height" + layoutHeight);
        Log.d(TAG, "Camera frame: " +
                String.format("%d, %d, %d, %d,",
                        cameraFrame.left,
                        cameraFrame.top,
                        cameraFrame.right,
                        cameraFrame.bottom));
        Log.d(TAG, "Scale: " + scale);
    }

    private boolean isPortraitMode() {
        return getContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;
    }
}
