package com.temobard.smartselfie.ui.activities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.vision.CameraSource;
import com.temobard.smartselfie.R;
import com.temobard.smartselfie.databinding.ActivityMainBinding;
import com.temobard.smartselfie.ui.interfaces.PermissionInquirer;
import com.temobard.smartselfie.ui.managers.CameraManager;
import com.temobard.smartselfie.ui.viewmodels.CameraViewModel;
import com.temobard.smartselfie.ui.viewmodels.SelfieViewModel;
import com.temobard.smartselfie.ui.widgets.CameraContainerView;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.temobard.smartselfie.ui.constants.ActivityConstants.IMAGE_PATH_KEY;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    CameraSource cameraSource;

    protected ArrayList<PermissionInquirer> inquirers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        CameraViewModel cameraViewModel = ViewModelProviders.of(this, viewModelFactory).get(CameraViewModel.class);
        SelfieViewModel selfieViewModel = ViewModelProviders.of(this, viewModelFactory).get(SelfieViewModel.class);

        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setCameraViewModel(cameraViewModel);
        binding.setSelfieViewModel(selfieViewModel);
        binding.setLifecycleOwner(this);

        Lifecycle lifecycle = getLifecycle();
        lifecycle.addObserver(cameraViewModel);
        lifecycle.addObserver(selfieViewModel);

        CameraContainerView cameraView = findViewById(R.id.cameraView);
        inquirers.add(new CameraManager(this, cameraSource, cameraView, lifecycle));

        selfieViewModel.getSelfiePath().observe(this, selfiePath -> {
            Bundle bundle = new Bundle();
            bundle.putString(IMAGE_PATH_KEY, selfiePath);
            startActivity(new Intent(this, ImageActivity.class), bundle);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (PermissionInquirer inquirer : inquirers) {
            inquirer.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
