package com.hafizjef.tasmuq.ui.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.model.DetailResponse;
import com.hafizjef.tasmuq.ui.activity.SettingsActivity;
import com.hafizjef.tasmuq.utils.AndroidUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailFragment extends Fragment{

    ImageView imageView;
    String uri;

    public static DetailFragment newInstance(int page, DetailResponse detailResponse){
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        if (page < detailResponse.getOverlapList().size()){
            args.putString("VERSE", detailResponse.getVerseList().get(page));
        } else {
            int page2 = page/2;
            args.putString("OVERLAP", detailResponse.getOverlapList().get(page2));
        }
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = AndroidUtils.getPreferences();
        String serverUrl = sharedPreferences.getString(SettingsActivity.BACKEND_URI, "");
        super.onCreate(savedInstanceState);
        if(getArguments().get("VERSE") != null){
            uri = serverUrl + "files/data/verse/" + getArguments().getString("VERSE");
        } else {
            uri = serverUrl + "files/data/line/actual/" + getArguments().getString("OVERLAP");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        imageView = view.findViewById(R.id.imv);
        Glide.with(view)
                .load(uri)
                .into(imageView);
        return view;
    }
}
