package com.temobard.smartselfie.ui.managers;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.ui.interfaces.CameraView;
import com.temobard.smartselfie.ui.interfaces.PermissionInquirer;

public class CameraManager implements LifecycleObserver, PermissionInquirer {

    private static final String TAG = "CameraManager";

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    private Activity activity;
    private CameraSource cameraSource;
    private CameraView cameraView;

    public CameraManager(
            Activity activity,
            CameraSource cameraSource,
            CameraView cameraView,
            Lifecycle lifecycle) {

        this.activity = activity;
        this.cameraView = cameraView;
        this.cameraSource = cameraSource;

        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (!isPermissionGranted()) requestCameraPermission();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        if (isPermissionGranted()) startCameraSource();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        cameraView.stop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;

    }

    private void requestCameraPermission() {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(activity, permissions, RC_HANDLE_CAMERA_PERM);
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            if (!cameraView.start(cameraSource)) {
                cameraSource.release();
                cameraSource = null;
                Toast.makeText(activity, "Error while starting the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == RC_HANDLE_CAMERA_PERM) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
