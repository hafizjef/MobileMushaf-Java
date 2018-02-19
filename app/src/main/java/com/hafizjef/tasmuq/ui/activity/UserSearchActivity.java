package com.hafizjef.tasmuq.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.adapters.MushafAdapter;
import com.hafizjef.tasmuq.data.ApiProvider;
import com.hafizjef.tasmuq.data.RetrofitProvider;
import com.hafizjef.tasmuq.data.MushafApiService;
import com.hafizjef.tasmuq.model.MushafResponse;
import com.hafizjef.tasmuq.utils.AndroidUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserSearchActivity extends AppCompatActivity {

    private static final String TAG = "UserSearchActivity";

    private String serverUrl;
    private String emailAddress;
    private Boolean devMode;

    private RecyclerView.Adapter adapter;

    private RecyclerView recyclerView;

    private ProgressBar cProgress;
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        cProgress = findViewById(R.id.circleProgress);
        noData = findViewById(R.id.noDataTxt);

        Toolbar mToolbar = findViewById(R.id.usertoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = AndroidUtils.getPreferences();

        devMode = sharedPreferences.getBoolean(SettingsActivity.KEY_PREFS_SWITCH, false);
        serverUrl = sharedPreferences.getString(SettingsActivity.BACKEND_URI, "");
        emailAddress = sharedPreferences.getString(SettingsActivity.EMAIL_ADDR, "");

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                loadData();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void loadData() {
        try {
            noData.setVisibility(View.GONE);
            cProgress.setVisibility(View.VISIBLE);
            MushafApiService mushafApi = ApiProvider.getMushafApi();
            Observable<MushafResponse> mushafResponse = mushafApi.getMushaf(emailAddress);
            mushafResponse.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> cProgress.setVisibility(View.GONE))
                    .subscribe((data) -> {
                        if(data.getCount() == 0) {
                            noData.setVisibility(View.VISIBLE);
                        }
                        adapter = new MushafAdapter(data, getApplicationContext(), serverUrl);
                        recyclerView.setAdapter(adapter);
                        Log.d(TAG, String.valueOf(data.getImageModels().size()));
                    }, (error) -> {
                        Log.e(TAG, error.getMessage());
                        noData.setVisibility(View.VISIBLE);
                    });
        } catch (Exception err) {
            AndroidUtils.showToast(err.getMessage());
        }
    }
}
