package com.ttg_photo_storage.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ttg_photo_storage.BuildConfig;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import model.login.detailsWithoutCrn.ResponseShipmentDetails;
import model.login.viewShipDetails.ViewShipDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShipmentDetails extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.crn_no)
    TextView crn_no;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.logistics_company_name)
    TextView logistics_company_name;
    @BindView(R.id.packageQuality)
    TextView packageQuality;
    @BindView(R.id.no_of_staff)
    TextView no_of_staff;
    @BindView(R.id.no_of_boxes)
    TextView no_of_boxes;
    @BindView(R.id.noOfPallets)
    TextView noOfPallets;
    @BindView(R.id.noOfDevices)
    TextView noOfDevices;
    @BindView(R.id.no_of_vehicle)
    TextView no_of_vehicle;
    @BindView(R.id.vehicle_type)
    TextView vehicle_type;
    @BindView(R.id.vehicle_No)
    TextView vehicle_No;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone_no)
    TextView phone_no;
    @BindView(R.id.imge_signature)
    ImageView imge_signature;
    @BindView(R.id.tv_msge)
    TextView tv_msge;

    @BindView(R.id.logistics_details)
    LinearLayout logistics_details;
    @BindView(R.id.vehicle_details)
    LinearLayout vehicle_details;
    @BindView(R.id.supervisor_detals)
    LinearLayout supervisor_detals;
    @BindView(R.id.Comment_ll)
    LinearLayout Comment_ll;

    @BindView(R.id.tv_reason_for_rejection)
    TextView tv_reason_for_rejection;
    @BindView(R.id.reason_ll)
    LinearLayout reason_ll;
    @BindView(R.id.reason_message)
    TextView reason_message;


    @BindView(R.id.btn_images)
    Button btn_images;
    @BindView(R.id.btn_Download)
    Button btn_Download;
    String hash = "";
    String crn = "";
    String PDFURL = "";
    Unbinder unbinder;
    String formattedDate, dateStr, currentTimeDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ship_details);
        ButterKnife.bind(this);
        title.setText("Shipment Receipt");
        hash = getIntent().getStringExtra("hash_id");
        crn = getIntent().getStringExtra("crn");
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
        dateStr = curFormater.format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        formattedDate = sdf.format(cal.getTime());
        currentTimeDate = (dateStr + " " + formattedDate);

        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("client")) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getViewShipDetails();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getViewShipDetailsShip();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        }
    }

    @OnClick({R.id.side_menu, R.id.btn_images, R.id.btn_Download})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

            case R.id.btn_images:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    goToActivity(ViewShipmentDetails.this, ViewShipImagesActivity.class, null);
                } else {
                    showMessage(getResources().getString(R.string.alert_internet));
                }
                break;
            case R.id.btn_Download:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PDFURL)));

                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getViewShipDetails() {
        try {
            showLoading();
            Call<ViewShipDetailsResponse> profileCall = apiServices.viewShipDetails("getship", PreferencesManager.getInstance(context).getToken(), crn, hash, currentTimeDate);
            profileCall.enqueue(new Callback<ViewShipDetailsResponse>() {
                @Override
                public void onResponse(Call<ViewShipDetailsResponse> call, Response<ViewShipDetailsResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        setUserProfile(response.body());
                        if (response.body().getShipments().getIsReject().equalsIgnoreCase("yes")) {
                            logistics_details.setVisibility(View.GONE);
                            vehicle_details.setVisibility(View.GONE);
                            supervisor_detals.setVisibility(View.GONE);
                            Comment_ll.setVisibility(View.GONE);
                            btn_Download.setVisibility(View.GONE);
                            btn_images.setVisibility(View.GONE);
                            tv_reason_for_rejection.setVisibility(View.VISIBLE);
                            reason_ll.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Login Credential or Token");
                    }
                }

                @Override
                public void onFailure(Call<ViewShipDetailsResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            showMessage("Invalid Username or Password");
            e.printStackTrace();
        }
    }

    private void getViewShipDetailsShip() {
        try {
            showLoading();
            Call<ResponseShipmentDetails> profileCall = apiServices.viewShipDetailsShip("getship", PreferencesManager.getInstance(context).getToken(), hash, currentTimeDate);
            profileCall.enqueue(new Callback<ResponseShipmentDetails>() {
                @Override
                public void onResponse(Call<ResponseShipmentDetails> call, Response<ResponseShipmentDetails> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getAllshipments().getIsReject().equalsIgnoreCase("yes")) {
                            logistics_details.setVisibility(View.GONE);
                            vehicle_details.setVisibility(View.GONE);
                            supervisor_detals.setVisibility(View.GONE);
                            Comment_ll.setVisibility(View.GONE);
                            btn_Download.setVisibility(View.GONE);
                            btn_images.setVisibility(View.GONE);
                            tv_reason_for_rejection.setVisibility(View.VISIBLE);
                            reason_ll.setVisibility(View.VISIBLE);
                        }
                        setUserProfileShip(response.body());


                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Login Credential or Token");
                    }
                }

                @Override
                public void onFailure(Call<ResponseShipmentDetails> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            showMessage("Invalid Username or Password");
            e.printStackTrace();
        }
    }

    private void setUserProfile(ViewShipDetailsResponse client) {
        crn_no.setText(client.getShipments().getCrn());
        date.setText(client.getShipments().getShipDateFormatted());
        time.setText(client.getShipments().getShipTimeFormatted());
        logistics_company_name.setText(client.getShipments().getLogisticCompany());
        if (client.getShipments().getBoxCondition().equalsIgnoreCase("Poor")) {
            packageQuality.setTextColor(context.getResources().getColor(R.color.red));
        } else if (client.getShipments().getBoxCondition().equalsIgnoreCase("Fair")) {
            packageQuality.setTextColor(context.getResources().getColor(R.color.yellow));
        } else if (client.getShipments().getBoxCondition().equalsIgnoreCase("Good")) {
            packageQuality.setTextColor(context.getResources().getColor(R.color.success));
        }
        packageQuality.setText(client.getShipments().getBoxCondition());
        no_of_staff.setText(client.getShipments().getNoOfStaff());
        no_of_boxes.setText(client.getShipments().getNoOfBox());
        noOfPallets.setText(client.getShipments().getNoOfPallets());
        noOfDevices.setText(client.getShipments().getNoOfDevices());
        no_of_vehicle.setText(client.getShipments().getNoOfVahicle());
        vehicle_type.setText(client.getShipments().getVahicleType());
        name.setText(client.getShipments().getSupervisorName());
        phone_no.setText(client.getShipments().getSupervisorPhNo());
        vehicle_No.setText(client.getShipments().getVahicleNumber());
        Glide.with(context)
                .load(BuildConfig.BASE_URL_FORIMAGE + client.getShipments().getSupervisorSign())
                .into(imge_signature);
        tv_msge.setText(client.getShipments().getNote());
        reason_message.setText(client.getShipments().getNote());
        PDFURL = client.getShipments().getPdfurl();
    }

    private void setUserProfileShip(ResponseShipmentDetails profile) {
        crn_no.setText(profile.getAllshipments().getCrn());
        date.setText(profile.getAllshipments().getShipDateFormatted());
        time.setText(profile.getAllshipments().getShipTimeFormatted());
        logistics_company_name.setText(profile.getAllshipments().getLogisticCompany());
        if (profile.getAllshipments().getBoxCondition().equalsIgnoreCase("Poor")) {
            packageQuality.setTextColor(context.getResources().getColor(R.color.red));
        } else if (profile.getAllshipments().getBoxCondition().equalsIgnoreCase("Fair")) {
            packageQuality.setTextColor(context.getResources().getColor(R.color.yellow));
        } else if (profile.getAllshipments().getBoxCondition().equalsIgnoreCase("Good")) {
            packageQuality.setTextColor(context.getResources().getColor(R.color.success));
        }
        packageQuality.setText(profile.getAllshipments().getBoxCondition());
        no_of_staff.setText(profile.getAllshipments().getNoOfStaff());
        no_of_boxes.setText(profile.getAllshipments().getNoOfBox());
        noOfPallets.setText(profile.getAllshipments().getNoOfPallets());
        noOfDevices.setText(profile.getAllshipments().getNoOfDevices());
        no_of_vehicle.setText(profile.getAllshipments().getNoOfVahicle());
        vehicle_type.setText(profile.getAllshipments().getVahicleType());
        name.setText(profile.getAllshipments().getSupervisorName());
        phone_no.setText(profile.getAllshipments().getSupervisorPhNo());
        vehicle_No.setText(profile.getAllshipments().getVahicleNumber());
        Glide.with(context)
                .load(BuildConfig.BASE_URL_FORIMAGE + profile.getAllshipments().getSupervisorSign())
                .into(imge_signature);
        tv_msge.setText(profile.getAllshipments().getNote());
        reason_message.setText(profile.getAllshipments().getNote());
        PDFURL = profile.getAllshipments().getPdfurl();

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




