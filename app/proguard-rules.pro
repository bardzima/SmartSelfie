package com.temobard.smartselfie.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.temobard.smartselfie.ui.viewmodels.FaceViewModelimport com.temobard.smartselfie.ui.viewmodels.CameraViewModel;
import com.temobard.smartselfie.ui.widgets.CameraContainerView;
import com.temobard.smartselfie.ui.widgets.FaceFrameView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMainBinding extends ViewDataBinding {
  @NonNull
  public