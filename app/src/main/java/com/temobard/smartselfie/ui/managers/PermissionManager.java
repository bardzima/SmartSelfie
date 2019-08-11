package com.temobard.smartselfie.ui.managers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionManager {

    private static final String TAG = "PermissionManager";

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    private Activity activity;

    public PermissionManager(Activity activity) {
        this.activity = activity;
        if (!isPermissionGranted()) requestCameraPermission();
    }

    public boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(activity, permissions, RC_HANDLE_CAMERA_PERM);
    }

    public boolean onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == RC_HANDLE_CAMERA_PERM) {
            return grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
