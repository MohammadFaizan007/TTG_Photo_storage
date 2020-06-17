package com.ttg_photo_storage.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderActivityTwo;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import model.login.checkAssetID.AssetIDResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetIDScanActivity extends BaseActivity implements View.OnClickListener , BarcodeReaderFragment.BarcodeReaderListener {
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tapToScan)
    LinearLayout tapToScan;
    @BindView(R.id.tapToManually)
    LinearLayout tapToManually;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assetid);
        ButterKnife.bind(this);
        title.setText("Scan Asset ID");
//        crnID_tv.setText("CRN ID   : "+PreferencesManager.getInstance(context).getCrnID());

    }
    @OnClick({R.id.side_menu, R.id.tapToManually,R.id.tapToScan})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

            case R.id.tapToManually:
                goToActivity(AssetIDScanActivity.this,ManualEntrySecondActivity.class, null);
                break;
            case R.id.tapToScan:
                FragmentManager supportFragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.fm_container);
                if (fragmentById != null) {
                    fragmentTransaction.remove(fragmentById);
                }
                fragmentTransaction.commitAllowingStateLoss();
                launchBarCodeActivity();
                break;


        }


    }
    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivityTwo.getLaunchIntent(context, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(context, "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(context, barcode.rawValue, Toast.LENGTH_SHORT).show();


//            if (barcode.rawValue.equalsIgnoreCase(PreferencesManager.getInstance(context).getUid())){
//                AlertDialog.Builder builder = new AlertDialog.Builder(AssetIDScanActivity.this);
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
//                        goToActivity(AssetIDScanActivity.this, UploadPhotoActivity.class, null);
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
//            }else {
//                PreferencesManager.getInstance(context).setUID(barcode.rawValue);
//                goToActivity(AssetIDScanActivity.this,UploadPhotoActivity.class, null);
////            mTvResultHeader.setText("On Activity Result");
////            mTvResult.setText(barcode.rawValue);
//
//            }
//
//
            PreferencesManager.getInstance(context).setUID(barcode.rawValue);
            checkAssetId();
        }

    }
    public void checkAssetId() {
        showLoading();
        Call<AssetIDResponse> assetIDCall = apiServices.checkID("checkuid", PreferencesManager.getInstance(context).getToken(), PreferencesManager.getInstance(context).getUid());
        assetIDCall.enqueue(new Callback<AssetIDResponse>() {
            @Override
            public void onResponse(Call<AssetIDResponse> call, Response<AssetIDResponse> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getStatus().equalsIgnoreCase("success")) {
                    if (response.body().getExists().equalsIgnoreCase("yes")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AssetIDScanActivity.this);
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
                        reusetwo.setVisibility(View.VISIBLE);
                        crnNO.setVisibility(View.GONE);
                        crnNO.setText(PreferencesManager.getInstance(context).getUid());
                        heading.setText(R.string.dialog_heading);
                        body.setText(R.string.dialog_body);
                        reusetwo.setText(R.string.dialog_reuse);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToActivity(AssetIDScanActivity.this, UploadPhotoActivity.class, null);
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();

                            }
                        });

                    }else {
                        goToActivity(AssetIDScanActivity.this, UploadPhotoActivity.class, null);

                    }

                } else {
                    PreferencesManager.getInstance(context).clear();
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                    finishActivity(AssetIDScanActivity.this);
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

    @Override
    public void onScanned(Barcode barcode) {
        Toast.makeText(context, barcode.rawValue, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(context, "Camera permission denied!", Toast.LENGTH_LONG).show();

    }


}




