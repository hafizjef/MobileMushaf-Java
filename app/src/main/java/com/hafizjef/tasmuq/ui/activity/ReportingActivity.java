package com.hafizjef.tasmuq.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.utils.AndroidUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import mehdi.sakout.fancybuttons.FancyButton;

public class ReportingActivity extends AppCompatActivity {

    private Uri mCropImageUri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FancyButton mSend = findViewById(R.id.btn_Send);

        imageView = findViewById(R.id.imgAttach);


        mSend.setOnClickListener(v -> sendReport());
        imageView.setOnClickListener(v -> CropImage.activity()
                .setInitialCropWindowPaddingRatio(0)
                .start(ReportingActivity.this));
    }

    private void sendReport(){
        try {
            AndroidUtils.showToast("HTTP Error: Server Unreachable");
        } catch (Exception err) {
            AndroidUtils.showToast(err.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Glide.with(this)
                        .load(result.getUri())
                        .into(imageView);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                AndroidUtils.showToast(error.getMessage());
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

}
