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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.ttg_photo_storage.BuildConfig;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import model.login.viewProfile.ViewProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.NONE;

public class ViewProfileActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.prof_image)
    ImageView prof_image;
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
    @BindView(R.id.btn_edit)
    Button btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        ButterKnife.bind(this);
        title.setText("My Profile");


        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")){
            change_btn.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.VISIBLE);
        }else if(PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            change_btn.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.VISIBLE);
        }else{
            change_btn.setVisibility(View.GONE);
            btn_edit.setVisibility(View.GONE);
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

    @OnClick({R.id.change_btn, R.id.btn_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_btn:
                goToActivity(ViewProfileActivity.this, ChangePassword.class, null);
                break;
            case R.id.btn_edit:
                goToActivity(ViewProfileActivity.this, EditProfileActivity.class, null);
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
                        PreferencesManager.getInstance(context).setProfilePic(response.body().getCurrentUser().getProfilePic());
                        country.setText(response.body().getCurrentUser().getCountry());
                        phone_no.setText(response.body().getCurrentUser().getMobile());
                        email_id.setText(response.body().getCurrentUser().getEmail());
                        user_name.setText(response.body().getCurrentUser().getName());
                        Glide.with(context).load(BuildConfig.BASE_URL_FORIMAGE + response.body().getCurrentUser().getProfilePic())
                                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.user_new)
                                        .error(R.drawable.user_new))
                                .into(prof_image);
//                        Glide.with(ViewProfileActivity.this)
//                                .load(BuildConfig.BASE_URL_FORIMAGE+response.body().getCurrentUser().getProfilePic())
//                                .skipMemoryCache(true)
//                                .diskCacheStrategy(AUTOMATIC)
//                                .into(prof_image);


//                        Glide.with(getApplicationContext())
//                                .load(response.body().getCurrentUser().getProfilePic())
//                                .apply(new RequestOptions()
//                                        .diskCacheStrategy(NONE).placeholder(R.drawable.photo_view)
//                                        .error(R.drawable.photo_view))
//                                .into(prof_image);

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

    @Override
    protected void onResume() {
        super.onResume();
//        getUserData();
    }
}
