package com.ttg_photo_storage.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developers.imagezipper.ImageZipper;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.AssetIDScanActivity;
import com.ttg_photo_storage.activity.CrnResultActivity;
import com.ttg_photo_storage.activity.ManualEntryActivity;
import com.ttg_photo_storage.activity.PostResultActivity;
import com.ttg_photo_storage.activity.UploadPhotoActivity;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseFragment;
import com.ttg_photo_storage.utils.NetworkUtils;
import com.ttg_photo_storage.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.zelory.compressor.Compressor;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class Dashboard extends BaseFragment implements View.OnClickListener, BarcodeReaderFragment.BarcodeReaderListener {
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

    //    Shipment Module
    @BindView(R.id.main_shipmant)
    NestedScrollView main_shipmant;
    @BindView(R.id.btn_AcceptShipment)
    Button btn_AcceptShipment;
    @BindView(R.id.btn_RejectShipment)
    Button btn_RejectShipment;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.imge_scan)
    ImageView imge_scan;
    @BindView(R.id.date)
    EditText timeDate;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.noOfVechile)
    EditText noOfVechile;
    @BindView(R.id.vehicleNumber)
    EditText vehicleNumber;
    @BindView(R.id.vehicleType)
    EditText vehicleType;
    @BindView(R.id.companyName)
    EditText companyName;
    @BindView(R.id.noOfLogisticsStaff)
    EditText noOfLogisticsStaff;
    @BindView(R.id.noOfBoxes)
    EditText noOfBoxes;
    @BindView(R.id.noOfPallets)
    EditText noOfPallets;
    @BindView(R.id.noOfDevices)
    EditText noOfDevices;
    @BindView(R.id.packageQuality)
    EditText packageQuality;
    @BindView(R.id.supervisorName)
    EditText supervisorName;
    @BindView(R.id.phone_no)
    EditText phone_no;
    @BindView(R.id.imageSignature)
    ImageView imageSignature;
    @BindView(R.id.checkbox_remember)
    CheckBox checkbox_remember;
    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.reason_message)
    EditText reason_message;
    @BindView(R.id.btn_Next)
    Button btn_Next;
    @BindView(R.id.btn_NextR)
    Button btn_NextR;

    @BindView(R.id.vehicleDetails)
    TextView vehicleDetails;
    @BindView(R.id.logisticsDetails)
    TextView logisticsDetails;
    @BindView(R.id.logisticsSupervisorDetails)
    TextView logisticsSupervisorDetails;
    @BindView(R.id.addComment)
    TextView addComment;
    @BindView(R.id.tv_reason_for_rejection)
    TextView tv_reason_for_rejection;

    @BindView(R.id.vehicleDetails_ll)
    LinearLayout vehicleDetails_ll;
    @BindView(R.id.logisticsDetails_ll)
    LinearLayout logisticsDetails_ll;
    @BindView(R.id.logisticsSupervisorDetails_ll)
    LinearLayout logisticsSupervisorDetails_ll;
    @BindView(R.id.addComment_ll)
    LinearLayout addComment_ll;
    @BindView(R.id.reason_ll)
    LinearLayout reason_ll;
    File IMAGE_SIGNATUREFile;
    private String edit_search_st = "", timeDate_st = "", noOfVechile_st = "", vehicleNumber_st = "", vehicleType_st = "",
            companyName_st = "", noOfLogisticsStaff_st = "", noOfBoxes_st = "", noOfPallets_st = "", noOfDevices_st = "", packageQuality_st = "",
            supervisorName_st = "", phone_no_st = "", et_message_st = "", reason_message_st = "", time_st = "";
    public String accept_st = "no";
    Unbinder unbinder;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = curFormater.format(new Date());
        Date date = new Date();
        String strDateFormat = "hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        timeDate.setText(dateStr);
        time.setText(formattedDate);
        PreferencesManager.getInstance(context).setAccept(accept_st);

        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")) {
            staff_layou.setVisibility(View.VISIBLE);
            heading_staff.setVisibility(View.VISIBLE);
            clint_layout.setVisibility(View.GONE);
            main_shipmant.setVisibility(View.GONE);

        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("client")) {
            staff_layou.setVisibility(View.GONE);
            clint_layout.setVisibility(View.VISIBLE);
            heading_staff.setVisibility(View.GONE);
            main_shipmant.setVisibility(View.GONE);

        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            staff_layou.setVisibility(View.GONE);
            clint_layout.setVisibility(View.GONE);
            heading_staff.setVisibility(View.GONE);
            main_shipmant.setVisibility(View.VISIBLE);
        }

        btn_RejectShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_RejectShipment.setBackground(getActivity().getResources().getDrawable(R.drawable.cancel_btn));
                btn_AcceptShipment.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_btn_bg_darkgreen));
                vehicleDetails.setVisibility(View.GONE);
                logisticsDetails.setVisibility(View.GONE);
                logisticsSupervisorDetails.setVisibility(View.GONE);
                addComment.setVisibility(View.GONE);
                vehicleDetails_ll.setVisibility(View.GONE);
                logisticsDetails_ll.setVisibility(View.GONE);
                logisticsSupervisorDetails_ll.setVisibility(View.GONE);
                addComment_ll.setVisibility(View.GONE);
                tv_reason_for_rejection.setVisibility(View.VISIBLE);
                reason_ll.setVisibility(View.VISIBLE);
                btn_Next.setVisibility(View.GONE);
                btn_NextR.setVisibility(View.VISIBLE);
                accept_st = "yes";
                PreferencesManager.getInstance(context).setAccept(accept_st);

            }
        });
        btn_AcceptShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_RejectShipment.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_btn_bg_darkgreen));
                btn_AcceptShipment.setBackground(getActivity().getResources().getDrawable(R.drawable.green_btn));
                vehicleDetails.setVisibility(View.VISIBLE);
                logisticsDetails.setVisibility(View.VISIBLE);
                logisticsSupervisorDetails.setVisibility(View.VISIBLE);
                addComment.setVisibility(View.VISIBLE);
                vehicleDetails_ll.setVisibility(View.VISIBLE);
                logisticsDetails_ll.setVisibility(View.VISIBLE);
                logisticsSupervisorDetails_ll.setVisibility(View.VISIBLE);
                addComment_ll.setVisibility(View.VISIBLE);
                tv_reason_for_rejection.setVisibility(View.GONE);
                reason_ll.setVisibility(View.GONE);
                btn_Next.setVisibility(View.VISIBLE);
                btn_NextR.setVisibility(View.GONE);
                accept_st = "no";
                PreferencesManager.getInstance(context).setAccept(accept_st);

            }
        });
        tapToScan.setOnClickListener(this);
        tapToManually.setOnClickListener(this);
        searchbtn.setOnClickListener(this);
        search_crn_btn.setOnClickListener(this);
        imge_scan.setOnClickListener(this);
        vehicleType.setOnClickListener(this);
        packageQuality.setOnClickListener(this);
        imageSignature.setOnClickListener(this);
        btn_Next.setOnClickListener(this);
        btn_NextR.setOnClickListener(this);
        timeDate.setOnClickListener(this);
        time.setOnClickListener(this);

        selectID_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb.getText().toString().equalsIgnoreCase("Search Result by CRN.\n(Shipment Details also Available)")) {
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
            case R.id.imge_scan:
                FragmentManager supportFragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = supportFragmentManager2.beginTransaction();
                Fragment fragmentById2 = supportFragmentManager2.findFragmentById(R.id.fm_container);
                if (fragmentById2 != null) {
                    fragmentTransaction2.remove(fragmentById2);
                }
                fragmentTransaction2.commitAllowingStateLoss();
                launchBarCodeActivity();
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
                        goToActivity(CrnResultActivity.class, null);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;

            case R.id.vehicleType:
                PopupMenu damperPopUp = new PopupMenu(context, vehicleType);
                damperPopUp.getMenuInflater().inflate(R.menu.vechicletype, damperPopUp.getMenu());
                damperPopUp.setOnMenuItemClickListener(item -> {
                    try {
                        vehicleType.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

                damperPopUp.show();
                break;
            case R.id.packageQuality:
                PopupMenu packaging = new PopupMenu(context, packageQuality);
                packaging.getMenuInflater().inflate(R.menu.packaging, packaging.getMenu());
                packaging.setOnMenuItemClickListener(item -> {
                    try {
                        packageQuality.setText(item.getTitle());
                        if (packageQuality.getText().toString().equalsIgnoreCase("Poor")) {
                            packageQuality.setTextColor(context.getResources().getColor(R.color.red));
                        } else if (packageQuality.getText().toString().equalsIgnoreCase("Fair")) {
                            packageQuality.setTextColor(context.getResources().getColor(R.color.yellow));
                        } else if (packageQuality.getText().toString().equalsIgnoreCase("Good")) {
                            packageQuality.setTextColor(context.getResources().getColor(R.color.success));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

                packaging.show();

                break;
            case R.id.imageSignature:
                signatureDialog();
                break;

            case R.id.date:
                datePicker(timeDate, true);
                break;

            case R.id.time:
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                currentMinute = calendar.get(Calendar.SECOND);
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 11) {
                            hourOfDay = hourOfDay - 12;
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        time.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

                break;

            case R.id.btn_Next:
                if (ValidationShipment()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        goToActivity(UploadPhotoActivity.class, null);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;

            case R.id.btn_NextR:
                if (ValidationShipmentReject()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        goToActivity(UploadPhotoActivity.class, null);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;


        }

    }

    private void datePicker(final EditText et, final boolean depart) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et.setText(Utils.changeDateFormat(dayOfMonth, monthOfYear, year));
                if (depart) {
                    Calendar departcal = Calendar.getInstance();
                    departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    departcal.set(Calendar.MONTH, monthOfYear);
                    departcal.set(Calendar.YEAR, year);
                }
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
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
        mPhotoEditor = new PhotoEditor.Builder(getActivity(), mPhotoEditorView)
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
                        imageSignature.setImageBitmap(scaled);
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
                        PreferencesManager.getInstance(context).setSignatureImage(IMAGE_SIGNATUREFile.getAbsolutePath());

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
            if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")) {
                goToActivity(AssetIDScanActivity.class, null);
            } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
                edit_search.setText(barcode.rawValue);
            }
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

    private boolean ValidationShipment() {
        try {
            edit_search_st = edit_search.getText().toString();
            timeDate_st = timeDate.getText().toString();
            time_st = time.getText().toString();
            noOfVechile_st = noOfVechile.getText().toString();
            vehicleNumber_st = vehicleNumber.getText().toString();
            vehicleType_st = vehicleType.getText().toString();
            companyName_st = companyName.getText().toString();
            noOfLogisticsStaff_st = noOfLogisticsStaff.getText().toString();
            noOfBoxes_st = noOfBoxes.getText().toString();
            noOfPallets_st = noOfPallets.getText().toString();
            noOfDevices_st = noOfDevices.getText().toString();
            packageQuality_st = packageQuality.getText().toString();
            supervisorName_st = supervisorName.getText().toString();
            phone_no_st = phone_no.getText().toString();
            et_message_st = et_message.getText().toString();

            PreferencesManager.getInstance(context).setCrnID(edit_search_st);
            PreferencesManager.getInstance(context).setDate(timeDate_st);
            PreferencesManager.getInstance(context).setTimeship(time_st);
            PreferencesManager.getInstance(context).setNoOfVechile(noOfVechile_st);
            PreferencesManager.getInstance(context).setVechileNumber(vehicleNumber_st);
            PreferencesManager.getInstance(context).setVechileType(vehicleType_st);
            PreferencesManager.getInstance(context).setCompanyName(companyName_st);
            PreferencesManager.getInstance(context).setLogistics_Staff(noOfLogisticsStaff_st);
            PreferencesManager.getInstance(context).setNoOfBoxes(noOfBoxes_st);
            PreferencesManager.getInstance(context).setNoOfPallets(noOfPallets_st);
            PreferencesManager.getInstance(context).setNoOfDevices(noOfDevices_st);
            PreferencesManager.getInstance(context).setPackagingQuality(packageQuality_st);
            PreferencesManager.getInstance(context).setSupervisorName(supervisorName_st);
            PreferencesManager.getInstance(context).setPhoneNumber(phone_no_st);
            PreferencesManager.getInstance(context).setMessage(et_message_st);
//
            if (edit_search_st.length() == 0) {
                edit_search_st = "";
                showError("Please enter CRN", edit_search);
                return false;
            } else if (timeDate_st.length() == 0) {
                timeDate_st = "";
                showError("Please enter date", timeDate);
                return false;
            } else if (time_st.length() == 0) {
                time_st = "";
                showError("Please enter time", time);
                return false;
//            } else if (noOfVechile_st.length() == 0) {
//                noOfVechile_st = "";
//                showError("Please enter no of vechile", noOfVechile);
//                return false;
//            } else if (vehicleNumber_st.length() == 0) {
//                vehicleNumber_st = "";
//                showError("Please enter vechile number", vehicleNumber);
//                return false;
//            } else if (vehicleType_st.length() == 0) {
//                vehicleType_st = "";
//                showError("Please enter vechile type", vehicleType);
//                return false;
//            } else if (companyName_st.length() == 0) {
//                companyName_st = "";
//                showError("Please enter company name", companyName);
//                return false;
//            } else if (noOfLogisticsStaff_st.length() == 0) {
//                noOfLogisticsStaff_st = "";
//                showError("Please enter no of logistics staff", noOfLogisticsStaff);
//                return false;
//            } else if (noOfBoxes_st.length() == 0) {
//                noOfBoxes_st = "";
//                showError("Please enter no of boxes", noOfBoxes);
//                return false;
//            } else if (noOfPallets_st.length() == 0) {
//                noOfPallets_st = "";
//                showError("Please enter no of pallets", noOfPallets);
//                return false;
//            } else if (noOfDevices_st.length() == 0) {
//                noOfDevices_st = "";
//                showError("Please enter no of devices", noOfDevices);
//                return false;
//            } else if (packageQuality_st.length() == 0) {
//                packageQuality_st = "";
//                showError("Please enter packaging quality", packageQuality);
//                return false;
//            } else if (supervisorName_st.length() == 0) {
//                supervisorName_st = "";
//                showError("Please enter supervisor name", supervisorName);
//                return false;
//            } else if (phone_no_st.length() == 0) {
//                phone_no_st = "";
//                showError("Please enter mobile no", phone_no);
//                return false;
//            } else if (IMAGE_SIGNATUREFile == null) {
//                showError("Please do your signature", et_message);
//                return false;
//            } else if (et_message_st.length() == 0) {
//                et_message_st = "";
//                showError("Please enter message", et_message);
//                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean ValidationShipmentReject() {
        try {
            edit_search_st = edit_search.getText().toString();
            timeDate_st = timeDate.getText().toString();
            reason_message_st = reason_message.getText().toString();
            time_st = time.getText().toString();

            PreferencesManager.getInstance(context).setCrnID(edit_search_st);
            PreferencesManager.getInstance(context).setDate(timeDate_st);
            PreferencesManager.getInstance(context).setReasonMessage(reason_message_st);
//
            if (edit_search_st.length() == 0) {
                edit_search_st = "";
                showError("Please enter CRN", edit_search);
                return false;
            } else if (timeDate_st.length() == 0) {
                timeDate_st = "";
                showError("Please enter date", timeDate);
                return false;
            } else if (time_st.length() == 0) {
                time_st = "";
                showError("Please enter time", time);
                return false;
            } else if (reason_message_st.length() == 0) {
                reason_message_st = "";
                showError("Please enter reason", timeDate);
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

