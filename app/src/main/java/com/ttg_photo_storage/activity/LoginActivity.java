package com.ttg_photo_storage.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.login.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.username_et)
    EditText usernameEt;
    @BindView(R.id.password_et)
    TextInputEditText passwordEt;
    @BindView(R.id.sign_in_btn)
    Button signInBtn;
    private String userName_st = "";
    private String password_st = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.sign_in_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_in_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getLogin(userName_st, password_st);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;
        }
    }


    private void getLogin(String username, String pswd) {
        try {
            showLoading();
            Call<LoginResponse> loginCall = apiServices.login("login", username, pswd,"android");
            loginCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if(response.body().getStatus().equalsIgnoreCase("success")){
                        if (response.body().getUser().getType().equalsIgnoreCase("staff")
                                || response.body().getUser().getType().equalsIgnoreCase("client")||
                                response.body().getUser().getType().equalsIgnoreCase("ship")) {
                            PreferencesManager.getInstance(context).setNAME(response.body().getUser().getName());
                            PreferencesManager.getInstance(context).setType(response.body().getUser().getType());
                            PreferencesManager.getInstance(context).setToken(response.body().getUser().getToken());
                            PreferencesManager.getInstance(context).setUserid(response.body().getUser().getId());
                            PreferencesManager.getInstance(context).setCountry(response.body().getUser().getCountry());
                            PreferencesManager.getInstance(context).setMobile(response.body().getUser().getMobile());
                            PreferencesManager.getInstance(context).setTime(response.body().getUser().getTime());
                            PreferencesManager.getInstance(context).setEmail(response.body().getUser().getEmail());
                            PreferencesManager.getInstance(context).setProfilePic(response.body().getUser().getProfilePic());
                            goToActivityWithFinish(LoginActivity.this, MainContainer.class, null);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }else{

                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    LoginActivity.this);
                            builder.setTitle("Alert !");
                            builder.setCancelable(true);
                            builder.setMessage(getResources().getString(R.string.alert_login));
                            builder.setNegativeButton(
                                    "Ok",
                                    (dialog, id) -> {
                                        PreferencesManager.getInstance(context).clear();
                                        dialog.cancel();
                                    });

                            AlertDialog alert11 = builder.create();
                            alert11.show();

//                            showMessage(getResources().getString(R.string.alert_login));
                        }

                    } else {
                        showToastS( "Invalid Login Credentials!");
                    }
                }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            showMessage("Invalid Username or Password");
            e.printStackTrace();
        }
    }


    private boolean Validation() {
        try {
            userName_st = usernameEt.getText().toString().trim();
            password_st = passwordEt.getText().toString().trim();

            if (userName_st.length() == 0) {
                userName_st = "";
                showError("Please enter Email ID", usernameEt);
                return false;
            }
            if (password_st.length() == 0) {
                password_st = "";
                showError(getResources().getString(R.string.enter_pswd_err), passwordEt);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
