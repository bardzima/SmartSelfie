package com.temobard.smartselfie.ui.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.temobard.smartselfie.R;
import com.temobard.smartselfie.databinding.ActivitySelfieBinding;
import com.temobard.smartselfie.ui.managers.ImageCropManager;
import com.temobard.smartselfie.ui.viewmodels.CropViewModel;
import com.temobard.smartselfie.ui.viewmodels.CameraViewModel;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ImageActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ImageCropManager imageCropManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        //get viewmodels
        CropViewModel cropViewModel = ViewModelProviders.of(this, viewModelFactory).get(CropViewModel.class);

        String imagePath = ViewModelProviders.of(this, viewModelFactory).get(CameraViewModel.class)
                .getSelfiePath().getValue();

        //initialize databinding
        ActivitySelfieBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_selfie);
        binding.setCropViewModel(cropViewModel);
        binding.setLifecycleOwner(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageCropManager = new ImageCropManager(this, imagePath, cropViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_selfie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        else if (item.getItemId() == R.id.imageCropItem) imageCropManager.startCropping();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageCropManager.onActivityResult(requestCode, resultCode, data);
    }
}
