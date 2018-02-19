package com.hafizjef.tasmuq;

import android.app.Application;

import com.hafizjef.tasmuq.utils.AndroidUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidUtils.init(this);
    }
}
