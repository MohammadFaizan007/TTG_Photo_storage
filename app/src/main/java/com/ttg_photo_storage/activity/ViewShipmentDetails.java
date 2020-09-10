package com.ttg_photo_storage.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.ttg_photo_storage.BuildConfig;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import id.zelory.compressor.Compressor;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import model.login.detailsWithoutCrn.ResponseShipmentDetails;
import model.login.shipUpload.ShipUploadResponse;
import model.login.shipUpload.updateShip.UpdateShipResponse;
import model.login.viewShipDetails.ViewShipDetailsResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShipmentDetails extends BaseActivity {
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
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
    @BindView(R.id.logistics_waybil)
    TextView logistics_waybil;
    @BindView(R.id.boxSeal)
    TextView boxSeal;
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
    @BindView(R.id.logistics_tv)
    TextView logistics_tv;
    @BindView(R.id.vehicle_tv)
    TextView vehicle_tv;
    @BindView(R.id.supervisor_tv)
    TextView supervisor_tv;
    @BindView(R.id.logistics_img)
    ImageView logistics_img;
    @BindView(R.id.vehicle_img)
    ImageView vehicle_img;
    @BindView(R.id.supervisor_imge)
    ImageView supervisor_imge;

    @BindView(R.id.reason_ll)
    LinearLayout reason_ll;
    @BindView(R.id.reason_message)
    TextView reason_message;
    @BindView(R.id.declair_et)
    EditText declair_et;
    @BindView(R.id.checkbox_remember)
    CheckBox checkbox_remember;


    @BindView(R.id.btn_images)
    Button btn_images;
    @BindView(R.id.btn_Download)
    Button btn_Download;
    @BindView(R.id.btn_images_reject)
    Button btn_images_reject;
    @BindView(R.id.btn_Download_reject)
    Button btn_Download_reject;
    String hash = "";
    String crn = "";
    String PDFURL = "";
    Unbinder unbinder;
    String formattedDate, dateStr, currentTimeDate;
    File IMAGE_SIGNATUREFile;
    ProgressDialog pd;

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Uploading Signature...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setMax(100);
        pd.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ship_details_new);
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
            checkbox_remember.setClickable(false);
            checkbox_remember.setFocusable(false);
            checkbox_remember.setCursorVisible(false);
            checkbox_remember.setFocusableInTouchMode(false);
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getViewShipDetails();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            checkbox_remember.setEnabled(true);
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getViewShipDetailsShip();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        }


    }

    @OnClick({R.id.side_menu, R.id.btn_images, R.id.btn_images_reject, R.id.btn_Download, R.id.btn_Download_reject, R.id.imge_signature, R.id.checkbox_remember})
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
            case R.id.btn_images_reject:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    goToActivity(ViewShipmentDetails.this, ViewShipImagesActivity.class, null);
                } else {
                    showMessage(getResources().getString(R.string.alert_internet));
                }
                break;
            case R.id.btn_Download:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PDFURL)));

                break;
            case R.id.btn_Download_reject:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PDFURL)));
                break;
            case R.id.imge_signature:
                if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
                    checkPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            STORAGE_PERMISSION_CODE);
                } else {
                    imge_signature.setEnabled(false);
                }
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
                            tv_reason_for_rejection.setVisibility(View.VISIBLE);
                            reason_ll.setVisibility(View.VISIBLE);
                            logistics_tv.setVisibility(View.GONE);
                            vehicle_tv.setVisibility(View.GONE);
                            supervisor_tv.setVisibility(View.GONE);
                            logistics_img.setVisibility(View.GONE);
                            vehicle_img.setVisibility(View.GONE);
                            supervisor_imge.setVisibility(View.GONE);
                            btn_images.setVisibility(View.GONE);
                            btn_Download.setVisibility(View.GONE);
                            btn_images_reject.setVisibility(View.VISIBLE);
                            btn_Download_reject.setVisibility(View.VISIBLE);
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
                            tv_reason_for_rejection.setVisibility(View.VISIBLE);
                            reason_ll.setVisibility(View.VISIBLE);
                            logistics_tv.setVisibility(View.GONE);
                            vehicle_tv.setVisibility(View.GONE);
                            supervisor_tv.setVisibility(View.GONE);
                            logistics_img.setVisibility(View.GONE);
                            vehicle_img.setVisibility(View.GONE);
                            supervisor_imge.setVisibility(View.GONE);

                            btn_images.setVisibility(View.GONE);
                            btn_Download.setVisibility(View.GONE);
                            btn_images_reject.setVisibility(View.VISIBLE);
                            btn_Download_reject.setVisibility(View.VISIBLE);
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
        logistics_waybil.setText(client.getShipments().getLogistic_waybill());
        boxSeal.setText(client.getShipments().getBox_seal());
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
        if (client.getShipments().getDeclrTick().equalsIgnoreCase("yes")) {
            checkbox_remember.setChecked(true);
        } else {
            checkbox_remember.setChecked(false);
        }
    }

    private void setUserProfileShip(ResponseShipmentDetails profile) {
        crn_no.setText(profile.getAllshipments().getCrn());
        date.setText(profile.getAllshipments().getShipDateFormatted());
        time.setText(profile.getAllshipments().getShipTimeFormatted());
        logistics_company_name.setText(profile.getAllshipments().getLogisticCompany());
        logistics_waybil.setText(profile.getAllshipments().getLogistic_waybill());
        boxSeal.setText(profile.getAllshipments().getBox_seal());
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
        if (profile.getAllshipments().getDeclrTick().equalsIgnoreCase("yes")) {
            checkbox_remember.setChecked(true);
        } else {
            checkbox_remember.setChecked(false);
        }

    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                    new String[]{permission},
                    requestCode);
        } else {
            if (checkbox_remember.isChecked()) {
                signatureDialog();
            } else {
                showError("Please tick check box", declair_et);

            }
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkbox_remember.isChecked()) {
                    signatureDialog();
                } else {
                    showError("Please tick check box", declair_et);
//                    showToastS("Please tick check box");

                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkbox_remember.isChecked()) {
                    signatureDialog();
                } else {
                    showError("Please tick check box", declair_et);
//                    showToastS("Please tick check box");

                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void signatureDialog() {
        PhotoEditor mPhotoEditor;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setCancelable(true);
        Button clear = (Button) dialog.findViewById(R.id.clear);
        Button save_btn = (Button) dialog.findViewById(R.id.save_btn);
        Button mCancel = (Button) dialog.findViewById(R.id.cancel);
        PhotoEditorView mPhotoEditorView = (PhotoEditorView) dialog.findViewById(R.id.photoEditorView);
        mPhotoEditorView.getSource().setImageResource(R.drawable.white_image);
        mPhotoEditor = new PhotoEditor.Builder(context, mPhotoEditorView)
                .setPinchTextScalable(true)
                .build();
        mPhotoEditor = new PhotoEditor.Builder(getApplicationContext(), mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                .build(); // build photo editor sdk
        mPhotoEditor.setBrushDrawingMode(true);
        mPhotoEditor.setBrushColor(R.color.red);
        mPhotoEditor.setBrushSize(5);
        dialog.show();
        Bitmap bitMap = BitmapFactory.decodeResource(getResources(), R.id.photoEditorView);
        File mFile1 = Environment.getExternalStorageDirectory();
        String fileName = "img1.png";
        File signatureFile = new File(mFile1, fileName);
        try {
            FileOutputStream outStream;
            outStream = new FileOutputStream(signatureFile);
            outStream.flush();
            outStream.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sdPath = mFile1.getAbsolutePath().toString() + "/" + fileName;
        PhotoEditor finalMPhotoEditor = mPhotoEditor;
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finalMPhotoEditor.saveAsFile(sdPath, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String signaturePath) {
                        File signatureFile = new File(signaturePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(signatureFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imge_signature.setImageBitmap(scaled);
//                        try {
//                            IMAGE_SIGNATUREFile=new ImageZipper(getActivity())
//                                    .setQuality(90)
//                                    .setMaxWidth(520)
//                                    .setMaxHeight(720)
//                                    .compressToFile(signatureFile);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        IMAGE_SIGNATUREFile = Compressor.getDefault(context).compressToFile(signatureFile);
                        updateSignature(hash);

//                        PreferencesManager.getInstance(context).setSignatureImage(IMAGE_SIGNATUREFile.getAbsolutePath());

                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        PhotoEditor finalMPhotoEditor1 = mPhotoEditor;
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalMPhotoEditor1.undo();
            }
        });


    }

    public void updateSignature(String key) {
        try {
            showProgressDialog();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "addship");
            RequestBody hash = RequestBody.create(MediaType.parse("text/plain"), key);
            RequestBody declr_tick = RequestBody.create(MediaType.parse("text/plain"), "yes");
            MultipartBody.Part signatureBody = null;

            if (IMAGE_SIGNATUREFile != null) {
                RequestBody requestBodySignature = RequestBody.create(MediaType.parse("file15/*"), IMAGE_SIGNATUREFile);
                signatureBody = MultipartBody.Part.createFormData("supervisor_sign", IMAGE_SIGNATUREFile.getName(), requestBodySignature);

            }
            RequestBody update = RequestBody.create(MediaType.parse("text/plain"), "true");


            Log.i("token>>>", token.toString());
            Log.i("addpost>>", action.toString());
            Call<UpdateShipResponse> photocall = apiServices.ShipSignatureUpload(token, action, hash, declr_tick, signatureBody, update);
            photocall.enqueue(new Callback<UpdateShipResponse>() {
                @Override
                public void onResponse(Call<UpdateShipResponse> call, Response<UpdateShipResponse> response) {
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewShipmentDetails.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText("Signature Updated Successfully");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                goToActivityWithFinish(ViewShipmentDetails.this, MainContainer.class, null);
                            }
                        });


                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
                    }

                }


                @Override
                public void onFailure(Call<UpdateShipResponse> call, Throwable t) {

                }
            });


        } catch (
                Exception e) {
            showMessage("Something went wrong please check token");
            e.printStackTrace();
        }

    }
//    private boolean Validation() {
//        try {
//            if (!checkbox_remember.isChecked()){
//                showToastS("Please tick check box");
//                return false;
//            }
//
////            if (userName_st.length() == 0) {
////                userName_st = "";
////                showError("Please enter Email ID", usernameEt);
////                return false;
////            }
////            if (password_st.length() == 0) {
////                password_st = "";
////                showError(getResources().getString(R.string.enter_pswd_err), passwordEt);
////                return false;
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

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




