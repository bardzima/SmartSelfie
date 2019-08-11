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
import com.temobard.smartselfie.ui.interfaces.ActivityResultInquirer;
import com.temobard.smartselfie.ui.managers.SelfieManager;
import com.temobard.smartselfie.ui.viewmodels.CropViewModel;
import com.temobard.smartselfie.ui.viewmodels.SelfieViewModel;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ImageActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SelfieManager selfieManager;

    protected ArrayList<ActivityResultInquirer> inquirers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        //get viewmodels
        SelfieViewModel selfieViewModel = ViewModelProviders.of(this, viewModelFactory).get(SelfieViewModel.class);
        CropViewModel cropViewModel = ViewModelProviders.of(this, viewModelFactory).get(CropViewModel.class);

        //initialize databinding
        ActivitySelfieBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_selfie);
        binding.setCropViewModel(cropViewModel);
        binding.setLifecycleOwner(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        selfieManager = new SelfieManager(this, selfieViewModel.getSelfiePath().getValue(), cropViewModel);
        inquirers.add(selfieManager);
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
        else if (item.getItemId() == R.id.imageCropItem) selfieManager.startCropping();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (ActivityResultInquirer inquirer : inquirers) {
            inquirer.onActivityResult(requestCode, resultCode, data);
        }
    }
}
