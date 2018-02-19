package com.hafizjef.tasmuq.data;

import android.content.SharedPreferences;

import com.hafizjef.tasmuq.ui.activity.SettingsActivity;
import com.hafizjef.tasmuq.utils.AndroidUtils;

public class ApiProvider {

    public static MushafApiService getMushafApi(){
        SharedPreferences sharedPreferences = AndroidUtils.getPreferences();
        String serverUrl = sharedPreferences.getString(SettingsActivity.BACKEND_URI, "");
        return RetrofitProvider.getClient(serverUrl).create(MushafApiService.class);
    }
}
