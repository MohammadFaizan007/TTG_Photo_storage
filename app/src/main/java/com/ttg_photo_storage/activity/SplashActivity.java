package com.ttg_photo_storage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.centerLogo)
    ImageView centerLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);

        new Handler().postDelayed(() -> {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                if (!PreferencesManager.getInstance(context).getUserid().equals("")) {
                    goToActivityWithFinish(context, MainContainer.class, null);
                } else {
                    goToActivityWithFinish(context, LoginActivity.class, null);
                }
            } else {
                createInfoDialog(context, "Alert", getString(R.string.alert_internet));
            }
        }, 2000);
    }



}
