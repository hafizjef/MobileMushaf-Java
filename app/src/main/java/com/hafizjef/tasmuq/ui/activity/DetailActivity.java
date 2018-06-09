package com.hafizjef.tasmuq.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.data.ApiProvider;
import com.hafizjef.tasmuq.data.MushafApiService;
import com.hafizjef.tasmuq.model.DetailResponse;
import com.hafizjef.tasmuq.ui.fragment.DetailFragment;
import com.hafizjef.tasmuq.utils.AndroidUtils;
import com.hafizjef.tasmuq.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    Toolbar toolbar;
    ViewPager vp;
    FragmentPagerAdapter adapter;
    TextView mushafNegara, mushafPenerbit, mushafVersi, mushafTahunTerbit;
    TextView mushafTahunSah, mushafPenyalin, mushafMukaSurat, mushafNamaFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.detailToolbar);
        mushafNegara = findViewById(R.id.txtNegara);
        mushafPenerbit = findViewById(R.id.txtPenerbit);
        mushafVersi = findViewById(R.id.txtVersi);
        mushafTahunTerbit = findViewById(R.id.txtTahunPenerbit);
        mushafTahunSah = findViewById(R.id.txtTahunSah);
        mushafPenyalin = findViewById(R.id.txtPenyalin);
        mushafMukaSurat = findViewById(R.id.txtMukasurat);
        mushafNamaFail = findViewById(R.id.txtNamaFail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String uuid = bundle.getString(Constants.UUID_EXTRA);
        Log.d(TAG, uuid);

        loadData(uuid);

        vp = findViewById(R.id.viewPager);
    }

    @SuppressLint("CheckResult")
    private void loadData(String uuid) {
        try {
            MushafApiService mushafApi = ApiProvider.getMushafApi();
            Observable<DetailResponse> detailResponse = mushafApi.getDetails(uuid);
            detailResponse.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {})
                    .subscribe(data -> {
                        adapter = new MyPagerAdapter(getSupportFragmentManager(), data);
                        vp.setAdapter(adapter);
                        mushafNegara.setText(data.getResult().getNegara());
                        mushafNamaFail.setText(data.getResult().getNamaFail());
                        mushafMukaSurat.setText(data.getResult().getMukaSurat());
                        mushafTahunSah.setText(data.getResult().getTahunDisahkan());
                        mushafTahunTerbit.setText(data.getResult().getTahunPenerbitan());
                        mushafVersi.setText(data.getResult().getVersi());
                        mushafPenerbit.setText(data.getResult().getPenerbit());
                    });
        } catch (Exception err) {
            AndroidUtils.showToast(err.getMessage());
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private DetailResponse dr;

        MyPagerAdapter(FragmentManager fm, DetailResponse dr) {
            super(fm);
            this.dr = dr;
        }

        @Override
        public Fragment getItem(int position) {
            return DetailFragment.newInstance(position, dr);
        }

        @Override
        public int getCount() {
            return (dr.getOverlapList().size() + dr.getVerseList().size());
        }
    }

}
