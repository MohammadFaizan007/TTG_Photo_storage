package com.ttg_photo_storage.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.login.viewProfile.ViewProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.email_id)
    TextView email_id;
    @BindView(R.id.phone_no)
    TextView phone_no;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.change_btn)
    Button change_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        ButterKnife.bind(this);
        title.setText("My Profile");
        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")){
            change_btn.setVisibility(View.VISIBLE);
        }else {
            change_btn.setVisibility(View.GONE);
        }
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getUserData();
        } else {
            showMessage(getResources().getString(R.string.alert_internet));
        }
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }

    @OnClick({R.id.change_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_btn:
                goToActivity(ViewProfileActivity.this,ChangePassword.class,null);
                break;
        }
    }

    private void getUserData() {
        try {
            showLoading();
            Call<ViewProfileResponse> profileCall = apiServices.profile("getuser", PreferencesManager.getInstance(context).getToken());
            profileCall.enqueue(new Callback<ViewProfileResponse>() {
                @Override
                public void onResponse(Call<ViewProfileResponse> call, Response<ViewProfileResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        country.setText(response.body().getCurrentUser().getCountry());
                        phone_no.setText(response.body().getCurrentUser().getMobile());
                        email_id.setText(response.body().getCurrentUser().getEmail());
                        user_name.setText(response.body().getCurrentUser().getName());

                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Login Credential or Token");
                    }
                }
                @Override
                public void onFailure(Call<ViewProfileResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            showMessage("Invalid Username or Password");
            e.printStackTrace();
        }
    }
}
