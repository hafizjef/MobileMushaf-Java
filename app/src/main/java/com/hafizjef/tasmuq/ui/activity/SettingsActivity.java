package com.hafizjef.tasmuq.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hafizjef.tasmuq.ui.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREFS_SWITCH = "enable_debug";
    public static final String BACKEND_URI = "backend_uri";
    public static final String EMAIL_ADDR = "email_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
