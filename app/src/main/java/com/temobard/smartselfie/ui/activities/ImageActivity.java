package com.temobard.smartselfie.ui.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.temobard.smartselfie.R;
import com.temobard.smartselfie.databinding.ActivitySelfieBinding;
import com.temobard.smartselfie.ui.viewmodels.CropViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.temobard.smartselfie.ui.constants.ActivityConstants.IMAGE_PATH_KEY;

public class ImageActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CropViewModel cropViewModel;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        //get viewmodels
        cropViewModel = ViewModelProviders.of(this, viewModelFactory).get(CropViewModel.class);

        if(getIntent().getExtras() != null) {
            imagePath = getIntent().getExtras().getString(IMAGE_PATH_KEY);
        }

        //initialize databinding
        ActivitySelfieBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_selfie);
        binding.setCropViewModel(cropViewModel);
        binding.setLifecycleOwner(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        cropViewModel.setSelfiePath(imagePath);
        startCropping();
    }

    public void startCropping() {
        CropImage.activity(Uri.fromFile(new File(imagePath)))
                .start(this);
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
        else if (item.getItemId() == R.id.imageCropItem) startCropping();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                cropViewModel.setSelfiePath(result.getUri().getPath());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Error while cropping", Toast.LENGTH_LONG).show();
            }
        }
    }
}
