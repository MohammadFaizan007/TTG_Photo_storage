package com.ttg_photo_storage.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import model.login.checkAssetID.AssetIDResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManualEntrySecondActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    //    @BindView(R.id.crnID_tv)
//    TextView crnID_tv;
    @BindView(R.id.assedID_et)
    EditText assedIDEt;
    @BindView(R.id.submit_btn)
    Button submit_btn;
    private String assetID_st = "";
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry_second);
        ButterKnife.bind(this);
        title.setText("Type Asset ID");
    }
//        crnID_tv.setText("CRN ID   : "+ PreferencesManager.getInstance(context).getCrnID());


    @OnClick({R.id.side_menu, R.id.submit_btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

            case R.id.submit_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        checkAssetId();
//                        goToActivity(ManualEntrySecondActivity.this, UploadPhotoActivity.class, null);

                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));

                    }
                }
                break;

        }

    }

    public void checkAssetId() {
        showLoading();
        Call<AssetIDResponse> assetIDCall = apiServices.checkID("checkuid", PreferencesManager.getInstance(context).getToken(), assedIDEt.getText().toString());
        assetIDCall.enqueue(new Callback<AssetIDResponse>() {
            @Override
            public void onResponse(Call<AssetIDResponse> call, Response<AssetIDResponse> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getStatus().equalsIgnoreCase("success")) {
                    if (response.body().getExists().equalsIgnoreCase("yes")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManualEntrySecondActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview_two, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        TextView cancel = dialogView.findViewById(R.id.cancel);
                        TextView crnNO = dialogView.findViewById(R.id.crnNO);
                        TextView reuse = dialogView.findViewById(R.id.reuse);
                        TextView reusetwo = dialogView.findViewById(R.id.reusetwo);
                        reuse.setVisibility(View.GONE);
                        crnNO.setVisibility(View.GONE);
                        reusetwo.setVisibility(View.VISIBLE);
                        crnNO.setText(PreferencesManager.getInstance(context).getUid());
                        heading.setText(R.string.dialog_heading);
                        body.setText(R.string.dialog_asset);
                        reusetwo.setText(R.string.dialog_reuse);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToActivity(ManualEntrySecondActivity.this, UploadPhotoActivity.class, null);
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                assedIDEt.setText("");

                            }
                        });

                    }else {
                        goToActivity(ManualEntrySecondActivity.this, UploadPhotoActivity.class, null);

                    }

                } else {
                    PreferencesManager.getInstance(context).clear();
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                    finishActivity(ManualEntrySecondActivity.this);
                    showToastS("Invalid  Credentials! Please Login Again");
                }

            }

            @Override
            public void onFailure(Call<AssetIDResponse> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private boolean Validation() {
        try {
//            if (assedIDEt.getText().toString().equalsIgnoreCase(PreferencesManager.getInstance(context).getUid())) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ManualEntrySecondActivity.this);
//                ViewGroup viewGroup = findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview_two, viewGroup, false);
//                builder.setView(dialogView);
//                TextView heading = dialogView.findViewById(R.id.heading);
//                TextView body = dialogView.findViewById(R.id.body);
//                TextView ok = dialogView.findViewById(R.id.buttonOk);
//                TextView cancel = dialogView.findViewById(R.id.cancel);
//                TextView crnNO = dialogView.findViewById(R.id.crnNO);
//                crnNO.setText(PreferencesManager.getInstance(context).getUid());
//                heading.setText(R.string.dialog_heading);
//                body.setText(R.string.dialog_body);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.setCanceledOnTouchOutside(false);
//                alertDialog.show();
//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        goToActivity(ManualEntrySecondActivity.this, UploadPhotoActivity.class, null);
//                    }
//                });
//
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//
//                    }
//                });
//                return false;
//            }
            assetID_st = assedIDEt.getText().toString().trim();
            PreferencesManager.getInstance(context).setUID(assetID_st);
            if (assetID_st.length() == 0) {
                assetID_st = "";
                showError("Please enter a valid asset ID", assedIDEt);
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




