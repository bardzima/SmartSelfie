package com.temobard.smartselfie.ui.widgets;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.domain.CameraFrame;
import com.temobard.smartselfie.domain.Frame;
import com.temobard.smartselfie.ui.helpers.CameraHelper;

public class CameraView extends ViewGroup {

    private static final String TAG = "CameraContainerView";
    private CameraSurfaceView cameraSurfaceView;
    private CameraSource cameraSource;

    private Frame layoutFrame = new Frame();

    private OnCameraSizeUpdateListener cameraSizeUpdateListener;

    public interface OnCameraSizeUpdateListener {
        void onCameraSizeUpdated(CameraFrame frame);
    }

    public void setOnCameraSizeUpdateListener(OnCameraSizeUpdateListener cameraSizeUpdateListener) {
        this.cameraSizeUpdateListener = cameraSizeUpdateListener;
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        cameraSurfaceView = new CameraSurfaceView(context, attrs);
        addView(cameraSurfaceView);

        cameraSurfaceView.setSurfaceUpdateListener(() -> {
            adjustCameraSurfaceViewSize(cameraSurfaceView.getCameraSource());
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutFrame.setFrame(left, top, right, bottom);
//        layoutFrame.setLeft(left);
//        layoutFrame.setRight(right);
//        layoutFrame.setTop(top);
//        layoutFrame.setBottom(bottom);

        adjustCameraSurfaceViewSize(cameraSurfaceView.getCameraSource());
    }

    public void setCameraSource(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
    }

    public void setCameraStarted(Boolean started) {
        if (started != null && started) cameraSurfaceView.start(cameraSource);
    }

    private void adjustCameraSurfaceViewSize(CameraSource cameraSource) {
        Size camSize = (cameraSource == null) ? null : cameraSource.getPreviewSize();
        CameraFrame camFrame = CameraHelper.getAdjustedCameraFrame(camSize, layoutFrame, isPortraitMode());

        cameraSurfaceView.layout(
                camFrame.getLeft(),
                camFrame.getTop(),
                camFrame.getRight(),
                camFrame.getBottom());

        if (cameraSizeUpdateListener != null)
            cameraSizeUpdateListener.onCameraSizeUpdated(camFrame);
    }

    private boolean isPortraitMode() {
        return getContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;
    }
}
