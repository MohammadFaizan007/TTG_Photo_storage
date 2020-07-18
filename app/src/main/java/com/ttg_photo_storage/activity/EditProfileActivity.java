package com.ttg_photo_storage.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.developers.imagezipper.ImageZipper;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.ttg_photo_storage.BuildConfig;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.FileUtils;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import model.login.editProfile.EditProfileResponse;
import model.login.viewProfile.ViewProfileResponse;
import model.login.viewShipDetails.ViewShipDetailsResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;


public class EditProfileActivity extends BaseActivity implements IPickCancel, IPickResult {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.profile_photo)
    CircleImageView profile_photo;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.phone_no)
    EditText phone_no;
    @BindView(R.id.save_btn)
    Button save_btn;
    @BindView(R.id.profile_photo_lt)
    RelativeLayout profile_photo_lt;
    private File Profilefile,ProfileFileCompress;
    private String user_name_st = "", phone_no_st = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        ButterKnife.bind(this);
        title.setText("Edit Profile");

//        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")){
//            change_btn.setVisibility(View.VISIBLE);
//        }else {
//            change_btn.setVisibility(View.GONE);
//        }
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

    @OnClick({R.id.save_btn, R.id.profile_photo_lt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getSave();
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;

            case R.id.profile_photo_lt:
                selection = SELECTION.PROFILE_IMAGE;
                showDialog();
                break;
        }
    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            switch (selection) {
                case PROFILE_IMAGE:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(1, 1)
                            .start(context);
                    break;
            }
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            switch (selection) {
                case PROFILE_IMAGE:
                    if (resultCode == RESULT_OK) {
                        Profilefile = FileUtils.getFile(context, result.getUri());
                        Bitmap bitmapImage = BitmapFactory.decodeFile(Profilefile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        profile_photo.setImageBitmap(scaled);
                        try {
                            ProfileFileCompress=new ImageZipper(EditProfileActivity.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(Profilefile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        ProfileFileCompress = Compressor.getDefault(this).compressToFile(Profilefile);
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;
            }
        }
    }

    /*Selection Images*/
    private enum SELECTION {
        PROFILE_IMAGE
    }

    private SELECTION selection;
    private PickImageDialog dialog;

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case PROFILE_IMAGE:
                pickSetup.setTitle("Choose Profile Image");
                break;
        }

        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(this);

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
                        setUserProfile(response.body());

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

    private void setUserProfile(ViewProfileResponse profile) {
        phone_no.setText(profile.getCurrentUser().getMobile());
        user_name.setText(profile.getCurrentUser().getName());
//        profile_photo.setBackground(null);
//        profile_photo.setImageDrawable(null);
        Glide.with(EditProfileActivity.this)
                .load(BuildConfig.BASE_URL_FORIMAGE + profile.getCurrentUser().getProfilePic())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(profile_photo);

    }

    private void getSave() {
        try {
            showLoading();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "adduser");
            RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), user_name_st);
            RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), phone_no_st);
            MultipartBody.Part profileBody = null;
            if (Profilefile != null) {
                RequestBody requestBodySignature = RequestBody.create(MediaType.parse("file/*"), ProfileFileCompress);
                profileBody = MultipartBody.Part.createFormData("profile_pic", ProfileFileCompress.getName(), requestBodySignature);
            }

            Call<EditProfileResponse> editProfile = apiServices.profileEdit(action, token,
                    user_name, mobile, profileBody);
            editProfile.enqueue(new Callback<EditProfileResponse>() {
                @Override
                public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        showToastS(response.body().getMsg());
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText(response.body().getMsg());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                goToActivityWithFinish(EditProfileActivity.this, MainContainer.class, null);
                            }
                        });

                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
                    }

                }

                @Override
                public void onFailure(Call<EditProfileResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            showMessage("Invalid Username or Password");
            e.printStackTrace();
        }
    }

    private boolean Validation() {
        try {
            user_name_st = user_name.getText().toString();
            phone_no_st = phone_no.getText().toString();

            if (user_name_st.length() == 0) {
                user_name_st = "";
                showError("Please enter User Name", user_name);
                return false;
            } else if (phone_no_st.length() == 0) {
                phone_no_st = "";
                showError("Please enter Mobile No", phone_no);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(context);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            requestFocus(editText);
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
