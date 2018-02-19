package com.hafizjef.tasmuq.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Callback;
import com.afollestad.bridge.MultipartForm;
import com.afollestad.bridge.ProgressCallback;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.utils.AndroidUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private String serverUrl;
    private String emailAddress;
    private Boolean devMode;

    private Uri mCropImageUri;
    private ProgressBar mUploadProgress;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Instantiate UI Components
        FancyButton mUploadButton = findViewById(R.id.btn_verify);
        FancyButton mReportButton = findViewById(R.id.btn_report);
        FancyButton mSearchButton = findViewById(R.id.btn_search);

        mUploadProgress = findViewById(R.id.upload_progress);

        mReportButton.setOnClickListener(view -> {
            Intent reportIntent = new Intent(MainActivity.this, ReportingActivity.class);
            startActivity(reportIntent);
        });

        mUploadButton.setOnClickListener(view -> {
            if(serverUrl.equalsIgnoreCase("") || emailAddress.equalsIgnoreCase("")) {
                Toast.makeText(MainActivity.this, "Server URL & Email Address cannot be empty! Please Update Your Setting", Toast.LENGTH_LONG).show();
            } else {
                CropImage.activity()
                        .setInitialCropWindowPaddingRatio(0)
                        .start(MainActivity.this);
            }
        });

        mSearchButton.setOnClickListener(view -> {
            Intent reportIntent = new Intent(MainActivity.this, UserSearchActivity.class);
            startActivity(reportIntent);
        });

        SharedPreferences sharedPreferences = AndroidUtils.getPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        devMode = sharedPreferences.getBoolean(SettingsActivity.KEY_PREFS_SWITCH, false);
        serverUrl = sharedPreferences.getString(SettingsActivity.BACKEND_URI, "");
        emailAddress = sharedPreferences.getString(SettingsActivity.EMAIL_ADDR, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return true;
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
                Uri resultUri = result.getUri();
                Toast.makeText(MainActivity.this, "Uploading Image...", Toast.LENGTH_LONG).show();
                try {
                    uploadImage(resultUri);
                } catch (IOException | BridgeException e) {
                    e.printStackTrace();
                    Log.d("MainClass.e", e.getMessage());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    private void uploadImage(Uri imageFile) throws IOException, BridgeException {

        mUploadProgress.setVisibility(View.VISIBLE);

        Bridge.config()
                .logging(devMode);

        MultipartForm form = new MultipartForm()
                .add("email", emailAddress)
                .add("image", new File(imageFile.getPath()));

        request = Bridge
                .post(serverUrl + "upload")
                .body(form)
                .uploadProgress(new ProgressCallback() {
                    @Override
                    public void progress(Request request, int current, int total, int percent) {
                        mUploadProgress.setProgress(percent);
                    }
                })
                .request(new Callback() {
                    @Override
                    public void response(@NotNull Request request, @Nullable Response response, @Nullable BridgeException e) {
                        if (e != null) {
                            // Reset progress bar on ERROR
                            mUploadProgress.setVisibility(View.INVISIBLE);
                            mUploadProgress.setProgress(0);

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            mUploadProgress.setVisibility(View.INVISIBLE);
                            mUploadProgress.setProgress(0);
                            Toast.makeText(MainActivity.this, "Image successfully uploaded", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        devMode = sharedPreferences.getBoolean(SettingsActivity.KEY_PREFS_SWITCH, false);
        serverUrl = sharedPreferences.getString(SettingsActivity.BACKEND_URI, "");
        emailAddress = sharedPreferences.getString(SettingsActivity.EMAIL_ADDR, "");

        if(!Patterns.WEB_URL.matcher(serverUrl).matches()) {
            AndroidUtils.showToast("URL invalid, please check the backend URL");
            sharedPreferences
                    .edit()
                    .putString(SettingsActivity.BACKEND_URI, String.valueOf(R.string.server_pref_default))
                    .apply();
            return;
        }

        if (!serverUrl.endsWith("/")) {
            sharedPreferences.edit()
                    .putString(SettingsActivity.BACKEND_URI, serverUrl + "/")
                    .apply();
        }

        Log.d("DEDED", serverUrl);
        AndroidUtils.showToast("Settings Saved");
    }
}
