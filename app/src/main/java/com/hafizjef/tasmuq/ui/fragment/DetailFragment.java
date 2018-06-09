package com.hafizjef.tasmuq.ui.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.model.DetailResponse;
import com.hafizjef.tasmuq.ui.activity.SettingsActivity;
import com.hafizjef.tasmuq.utils.AndroidUtils;

public class DetailFragment extends Fragment{

    private static final String TAG = "DetailFragment";
    private static int page2 = 0;
    ImageView imageView;
    String uri;

    public static DetailFragment newInstance(int page, DetailResponse detailResponse){
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        if (page < detailResponse.getVerseList().size()){
            args.putString("VERSE", detailResponse.getVerseList().get(page));
        } else {
            try {
                if(page2 == detailResponse.getOverlapList().size()) {
                    page2 = 0;
                }
                args.putString("OVERLAP", detailResponse.getOverlapList().get(page2));
                page2++;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        detailFragment.setArguments(args);
        Log.d(TAG, "NEWINSTANCE");
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = AndroidUtils.getPreferences();
        String serverUrl = sharedPreferences.getString(SettingsActivity.BACKEND_URI, "");
        super.onCreate(savedInstanceState);
        if(getArguments().get("VERSE") != null){
            uri = serverUrl + "files/verse/" + getArguments().getString("VERSE");
        } else {
            uri = serverUrl + "files/line/actual/" + getArguments().getString("OVERLAP");
        }
        //Log.d(TAG, "URI: " + uri);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        imageView = view.findViewById(R.id.imv);
        Glide.with(view)
                .load(uri)
                .into(imageView);
        //Log.d(TAG, "Loaded into imgview: " + uri);
        return view;
    }
}
