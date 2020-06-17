package com.ttg_photo_storage.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.AssetIDScanActivity;
import com.ttg_photo_storage.activity.CrnResultActivity;
import com.ttg_photo_storage.activity.ManualEntryActivity;
import com.ttg_photo_storage.activity.PostResultActivity;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseFragment;
import com.ttg_photo_storage.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Dashboard extends BaseFragment implements View.OnClickListener ,BarcodeReaderFragment.BarcodeReaderListener {
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    @BindView(R.id.tapToScan)
    LinearLayout tapToScan;
    @BindView(R.id.tapToManually)
    LinearLayout tapToManually;
    @BindView(R.id.assedID_et)
    EditText assedIDEt;
    @BindView(R.id.crnID_et)
    EditText crnID_et;
    @BindView(R.id.staff_layou)
    LinearLayout staff_layou;
    @BindView(R.id.clint_layout)
    LinearLayout clint_layout;
    @BindView(R.id.search_btn)
    Button searchbtn;
    @BindView(R.id.search_crn_btn)
    Button search_crn_btn;
    @BindView(R.id.heading_staff)
    TextView heading_staff;

    @BindView(R.id.selectID_group)
    RadioGroup selectID_group;
    private String assetID_st = "";
    private String crnID_st = "";
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")) {
            staff_layou.setVisibility(View.VISIBLE);
            heading_staff.setVisibility(View.VISIBLE);
            clint_layout.setVisibility(View.GONE);

        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("client")) {
            staff_layou.setVisibility(View.GONE);
            clint_layout.setVisibility(View.VISIBLE);
            heading_staff.setVisibility(View.GONE);

        }
        tapToScan.setOnClickListener(this);
        tapToManually.setOnClickListener(this);
        searchbtn.setOnClickListener(this);
        search_crn_btn.setOnClickListener(this);

        selectID_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb.getText().toString().equalsIgnoreCase("Search Result by CRN.")) {
                    crnID_et.setVisibility(View.VISIBLE);
                    assedIDEt.setVisibility(View.GONE);
                    searchbtn.setVisibility(View.GONE);
                    search_crn_btn.setVisibility(View.VISIBLE);

                } else {
                    crnID_et.setVisibility(View.GONE);
                    assedIDEt.setVisibility(View.VISIBLE);
                    searchbtn.setVisibility(View.VISIBLE);
                    search_crn_btn.setVisibility(View.GONE);

                }
            }
        });
        return view;
    }



//    private void addBarcodeReaderFragment() {
//        BarcodeReaderFragment readerFragment = BarcodeReaderFragment.newInstance(true, false, View.VISIBLE);
//        readerFragment.setListener(this);
//        FragmentManager supportFragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fm_container, readerFragment);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

            case R.id.tapToManually:
                goToActivity(ManualEntryActivity.class, null);
                break;

            case R.id.search_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        goToActivity(PostResultActivity.class, null);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;
            case R.id.search_crn_btn:
                if (ValidationCrn()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        goToActivity(CrnResultActivity.class,null);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;
        }

    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(context, true, false);
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
            PreferencesManager.getInstance(context).setCrnID(barcode.rawValue);
            goToActivity(AssetIDScanActivity.class, null);
//            mTvResultHeader.setText("On Activity Result");
//            mTvResult.setText(barcode.rawValue);
        }

    }

    @Override
    public void onScanned(Barcode barcode) {
        Toast.makeText(context, barcode.rawValue, Toast.LENGTH_SHORT).show();
//        mTvResultHeader.setText("Barcode value from fragment");
//        mTvResult.setText(barcode.rawValue);
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

    private boolean ValidationCrn() {
        try {
            crnID_st = crnID_et.getText().toString().trim();
            PreferencesManager.getInstance(context).setCrnID(crnID_st);

            if (crnID_st.length() == 0) {
                crnID_st = "";
                showError("Please enter crn ID", assedIDEt);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean Validation() {
        try {
            assetID_st = assedIDEt.getText().toString().trim();
            PreferencesManager.getInstance(context).setUID(assetID_st);

            if (assetID_st.length() == 0) {
                assetID_st = "";
                showError("Please enter asset ID", assedIDEt);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(getContext());
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

