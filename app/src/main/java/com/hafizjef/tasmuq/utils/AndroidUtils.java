package com.hafizjef.tasmuq.utils;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.hafizjef.tasmuq.R;

public class AndroidUtils {
    public static final String TAG = "AndroidUtils";

    @SuppressLint("StaticFieldLeak")
    private static Application application;
    private static ConnectivityManager connectivityManager;

    public static void init(Application application) {
        if (AndroidUtils.application == null) {
            AndroidUtils.application = application;

            connectivityManager = (ConnectivityManager)
                    application.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    public static Context getAppContext(){
        return application;
    }

    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    public static SharedPreferences getPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    public static SharedPreferences getPreferences(String name) {
        return application.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void openIntent(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getAppContext().getPackageManager()) != null) {
            getAppContext().startActivity(intent);
        } else {
            openIntentFailed();
        }
    }

    private static void openIntentFailed() {
        Toast.makeText(getAppContext(), R.string.failed_intent, Toast.LENGTH_LONG).show();
    }

    public static void showToast(String message) {
        Toast.makeText(application, message, Toast.LENGTH_LONG).show();
    }

    public static ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public static boolean isConnected(int type) {
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(type);
        return networkInfo != null && networkInfo.isConnected();
    }
}
