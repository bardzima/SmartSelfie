package com.temobard.smartselfie.ui.activities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.temobard.smartselfie.R;
import com.temobard.smartselfie.databinding.ActivityMainBinding;
import com.temobard.smartselfie.ui.managers.PermissionManager;
import com.temobard.smartselfie.ui.viewmodels.CameraViewModel;
import com.temobard.smartselfie.ui.viewmodels.FaceViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CameraViewModel cameraViewModel;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        FaceViewModel faceViewModel = ViewModelProviders.of(this, viewModelFactory).get(FaceViewModel.class);
        cameraViewModel = ViewModelProviders.of(this, viewModelFactory).get(CameraViewModel.class);

        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setFaceViewModel(faceViewModel);
        binding.setCameraViewModel(cameraViewModel);
        binding.setLifecycleOwner(this);

        Lifecycle lifecycle = getLifecycle();
        lifecycle.addObserver(faceViewModel);
        lifecycle.addObserver(cameraViewModel);

        permissionManager = new PermissionManager(this);

        cameraViewModel.getSelfiePath().observe(this, selfiePath -> {
            startActivity(new Intent(this, ImageActivity.class));
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(!permissionManager.onRequestPermissionsResult(requestCode, grantResults))
            Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionManager.isPermissionGranted()) cameraViewModel.resumeCamera();
    }

    @Override
    protected void onPause() {
        cameraViewModel.pauseCamera();
        super.onPause();
    }
}
