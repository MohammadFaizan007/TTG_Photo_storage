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
import model.login.changepassword.ChangePasswordResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.et_old_pswd)
    EditText etOldPswd;
    @BindView(R.id.et_new_pswd)
    EditText etNewPswd;
    @BindView(R.id.et_confrm_pswd)
    EditText etConfrmPswd;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);
        ButterKnife.bind(this);
        title.setText("Change Password");
    }

    @OnClick({R.id.side_menu, R.id.btn_submit})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

            case R.id.btn_submit:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getChangedPassword();
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;



        }


    }

    private void getChangedPassword() {
        try {
            showLoading();
            Call<ChangePasswordResponse> changeCall = apiServices.change("adduser", PreferencesManager.getInstance(context).getToken(),etOldPswd.getText().toString().trim()
                    ,etNewPswd.getText().toString().trim());
            changeCall.enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        showToastS("Change Your Password Successfully. Please Login with new password");
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText("Change Your Password Successfully.\n Please Login with new password");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            }
                        });
                    } else {
                        showToastS("Invalid Credentials! Please enter valid Password");
                    }
                }

                @Override
                public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            showMessage("Invalid  Password");
            e.printStackTrace();
        }

    }

    private boolean Validation() {
        if (etOldPswd.getText().toString().length() == 0) {
            showError("Please enter old password", etOldPswd);
//            etOldPswd.setError("Please enter old password");
            return false;
        } else if (etNewPswd.getText().toString().equals(etOldPswd.getText().toString())) {
            showError("Old password and new password matched",etNewPswd);
            return false;
        } else if (!etNewPswd.getText().toString().equals(etConfrmPswd.getText().toString())) {
            showError("New password and confirm password not matched",etNewPswd);
//            etNewPswd.setError("Password not matched");
            return false;
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        try {
            Dialog error_dialog = new Dialog(this);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
