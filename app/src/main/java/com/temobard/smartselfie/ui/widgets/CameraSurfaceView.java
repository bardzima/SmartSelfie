package com.temobard.smartselfie.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

public class CameraSurfaceView extends SurfaceView {

    private static final String TAG = "CameraView";

    private boolean startRequested;
    private boolean surfaceAvailable;
    private CameraSource cameraSource;

    public interface OnSurfaceUpdateListener {
        void onSurfaceUpdated();
    }

    public void setSurfaceUpdateListener(OnSurfaceUpdateListener surfaceUpdateListener) {
        this.surfaceUpdateListener = surfaceUpdateListener;
    }

    private OnSurfaceUpdateListener surfaceUpdateListener;

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        startRequested = false;
        surfaceAvailable = false;

        getHolder().addCallback(new SurfaceCallback());
    }

    public CameraSource getCameraSource() {
        return cameraSource;
    }

    public void start(CameraSource cameraSource) {
        if (cameraSource == null) return;

        this.cameraSource = cameraSource;

        startRequested = true;

        try {
            startIfReady();
        } catch (IOException ignored) {
        }
    }

    @SuppressLint("MissingPermission")
    private void startIfReady() throws IOException {
        if (startRequested && surfaceAvailable) {
            cameraSource.start(getHolder());
            startRequested = false;
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surface) {
            surfaceAvailable = true;
            try {
                startIfReady();
            } catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);
            }
            if(surfaceUpdateListener != null) surfaceUpdateListener.onSurfaceUpdated();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surface) {
            surfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if(surfaceUpdateListener != null) surfaceUpdateListener.onSurfaceUpdated();
        }
    }
}
