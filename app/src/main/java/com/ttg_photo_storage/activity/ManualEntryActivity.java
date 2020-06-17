package com.ttg_photo_storage.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ManualEntryActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.assedID_et)
    EditText assedIDEt;
    @BindView(R.id.submit_btn)
    Button submit_btn;
    private String assetID_st = "";
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);
        ButterKnife.bind(this);
        title.setText("Type CRN");

    }

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
                        goToActivity(ManualEntryActivity.this, AssetIDScanActivity.class, null);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }



    private boolean Validation() {
        try {
            assetID_st = assedIDEt.getText().toString().trim();
            PreferencesManager.getInstance(context).setCrnID(assetID_st);

            if (assetID_st.length() ==0) {
                assetID_st = "";
                showError("Please enter a valid CRN ID", assedIDEt);
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




