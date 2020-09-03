package com.ttg_photo_storage.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.BufferedOutputStream;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.developers.imagezipper.ImageZipper;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.dhaval2404.imagepicker.ImagePicker;
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
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import model.login.checkAssetID.AssetIDResponse;
import model.login.checkAssetID.FilesItem;
import model.login.shipImagesEdit.ShipImagesResponse;
import model.login.shipUpload.ShipUploadResponse;
import model.login.shipUpload.updateShip.UpdateImagesResponse;
import model.login.upload.FilesAcceptedItem;
import model.login.upload.UploadPhotoResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.NONE;

public class Demo extends BaseActivity /*implements IPickCancel, IPickResult*/ {
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_submitShipment)
    Button btn_submitShipment;
    @BindView(R.id.btn_updateShipment)
    Button btn_updateShipment;
    @BindView(R.id.assedId)
    TextView assedId;
    @BindView(R.id.crnID)
    TextView crnID;
    @BindView(R.id.imageOne)
    CircleImageView imageOne;
    @BindView(R.id.imageSecond)
    CircleImageView imageSecond;
    @BindView(R.id.imageThird)
    CircleImageView imageThird;
    @BindView(R.id.imageFourth)
    CircleImageView imageFourth;
    @BindView(R.id.imageFive)
    CircleImageView imageFive;
    @BindView(R.id.imageSix)
    CircleImageView imageSix;
    @BindView(R.id.imageSeven)
    CircleImageView imageSeven;
    @BindView(R.id.imageEight)
    CircleImageView imageEight;
    @BindView(R.id.imageNine)
    CircleImageView imageNine;
    @BindView(R.id.imageTen)
    CircleImageView imageTen;
    @BindView(R.id.imageEleven)
    CircleImageView imageEleven;
    @BindView(R.id.imageTwele)
    CircleImageView imageTwele;
    @BindView(R.id.imageThirteen)
    CircleImageView imageThirteen;
    @BindView(R.id.imageFourteen)
    CircleImageView imageFourteen;
    @BindView(R.id.imageFifteen)
    CircleImageView imageFifteen;

    @BindView(R.id.des_one)
    EditText des_one;
    @BindView(R.id.des_two)
    EditText des_two;
    @BindView(R.id.des_three)
    EditText des_three;
    @BindView(R.id.des_four)
    EditText des_four;
    @BindView(R.id.des_five)
    EditText des_five;
    @BindView(R.id.des_six)
    EditText des_six;
    @BindView(R.id.des_seven)
    EditText des_seven;
    @BindView(R.id.des_Eight)
    EditText des_Eight;
    @BindView(R.id.des_nine)
    EditText des_nine;
    @BindView(R.id.des_ten)
    EditText des_ten;
    @BindView(R.id.des_eleven)
    EditText des_eleven;
    @BindView(R.id.des_twele)
    EditText des_twele;
    @BindView(R.id.des_thirteen)
    EditText des_thirteen;
    @BindView(R.id.des_fourteen)
    EditText des_fourteen;
    @BindView(R.id.des_fifteen)
    EditText des_fifteen;
    @BindView(R.id.defectDescribe)
    EditText defectDescribe;
    @BindView(R.id.cardThirteen)
    ConstraintLayout cardThirteen;
    @BindView(R.id.cardFourteen)
    ConstraintLayout cardFourteen;
    @BindView(R.id.cardFifteen)
    ConstraintLayout cardFifteen;

    @BindView(R.id.defect_Details_ll)
    LinearLayout defect_Details_ll;
    @BindView(R.id.first_layer)
    LinearLayout first_layer;
    @BindView(R.id.second_layer)
    LinearLayout second_layer;
    @BindView(R.id.motherboard_check)
    CheckBox motherboard_check;
    @BindView(R.id.chassis_check)
    CheckBox chassis_check;
    @BindView(R.id.permanentMarking_check)
    CheckBox permanentMarking_check;
    @BindView(R.id.secreenSpot_check)
    CheckBox secreenSpot_check;
    @BindView(R.id.otherDefect_check)
    CheckBox otherDefect_check;
    @BindView(R.id.cpuMissing_check)
    CheckBox cpuMissing_check;
    @BindView(R.id.chassisCracked_check)
    CheckBox chassisCracked_check;
    @BindView(R.id.screenBrokrn_check)
    CheckBox screenBrokrn_check;
    @BindView(R.id.biosLocked_check)
    CheckBox biosLocked_check;
    @BindView(R.id.noDefects_check)
    CheckBox noDefects_check;
    @BindView(R.id.part_check)
    CheckBox part_check;
    @BindView(R.id.engraving_check)
    CheckBox engraving_check;
    @BindView(R.id.chassis2_check)
    CheckBox chassis2_check;


    Unbinder unbinder;
    ArrayList<FilesAcceptedItem> list = new ArrayList<FilesAcceptedItem>();
    private List<FilesItem> imageList;
    private List<model.login.shipImagesEdit.FilesItem> imageListShip;
    ProgressDialog pd;
    Bitmap alteredBitmap;


    private String desc1_st = "";
    private String desc2_st = "";
    private String desc3_st = "";
    private String desc4_st = "";
    private String desc5_st = "";
    private String desc6_st = "";
    private String desc7_st = "";
    private String desc8_st = "";
    private String desc9_st = "";
    private String desc10_st = "";
    private String desc11_st = "";
    private String desc12_st = "";
    private String desc13_st = "";
    private String desc14_st = "";
    private String desc15_st = "";
    private String describeDefect_st = "";

    private String motherboard_check_st = "", chassis_check_st = "", permanentMarking_check_st = "", secreenSpot_check_st = "", otherDefect_check_st = "",
            cpuMissing_check_st = "", chassisCracked_check_st = "", screenBrokrn_check_st = "", biosLocked_check_st = "", noDefects_check_st = "",
            part_check_st = "", engraving_check_st = "", chassis2_check_st = "";


    private File IMAGE_ONEfile;
    private File IMAGE_TWOFile;
    private File IMAGE_THREEFile;
    private File IMAGE_FOURFile;
    private File IMAGE_FIVEFile;
    private File IMAGE_SIXFile;
    private File IMAGE_SEVENFile;
    private File IMAGE_EIGHTFile;
    private File IMAGE_NINEFile;
    private File IMAGE_TENFile;
    private File IMAGE_ELEVENFile;
    private File IMAGE_TWELEFile;
    private File IMAGE_THIRTEENFile;
    private File IMAGE_FOURTEENFile;
    private File IMAGE_FIFTEENFile;
    private File SignatureFile;
    String formattedDate, dateStr, currentTimeDate;
    PhotoEditor mPhotoEditor;
    String hash = "";
    String crn = "";

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Uploading Photo...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setMax(100);
        pd.show();
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        ButterKnife.bind(this);
        hash = getIntent().getStringExtra("hash_id_ship");
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
        dateStr = curFormater.format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        formattedDate = sdf.format(cal.getTime());
        currentTimeDate = (dateStr + " " + formattedDate);
        Log.e("TIME======>", currentTimeDate.toString());

        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")) {
            title.setText("Upload Photo");
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                checkAssetId();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));

            }
        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            title.setText("Add Shipment Image");

        }


        if (hash != null) {
            checkImages();
            btn_updateShipment.setVisibility(View.VISIBLE);
            btn_submitShipment.setVisibility(View.GONE);
        } else {
            btn_updateShipment.setVisibility(View.GONE);
            btn_submitShipment.setVisibility(View.VISIBLE);
        }


        if (getIntent().getStringExtra("imageSignature") != null) {
            String signature_path = getIntent().getStringExtra("imageSignature");
            SignatureFile = new File(signature_path);
        } else {

        }
        String sourceString = "<u>" + "<b>" + "CRN:  " + "</b> " + PreferencesManager.getInstance(context).getCrnID() + "</u>";
        crnID.setText(Html.fromHtml(sourceString));
        String sourceString2 = "<b>" + "Asset ID:  " + "</b> " + PreferencesManager.getInstance(context).getUid();
        assedId.setText(Html.fromHtml(sourceString2));
        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            cardThirteen.setVisibility(View.VISIBLE);
            cardFourteen.setVisibility(View.VISIBLE);
            cardFifteen.setVisibility(View.VISIBLE);
            btn_submitShipment.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);
            assedId.setVisibility(View.GONE);
            defect_Details_ll.setVisibility(View.GONE);
        } else {
            cardThirteen.setVisibility(View.GONE);
            cardFourteen.setVisibility(View.GONE);
            cardFifteen.setVisibility(View.GONE);
            btn_submitShipment.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            assedId.setVisibility(View.VISIBLE);
            defect_Details_ll.setVisibility(View.VISIBLE);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @OnClick({R.id.side_menu, R.id.imageOne, R.id.imageSecond, R.id.imageThird, R.id.imageFourth, R.id.imageFive, R.id.imageSix,
            R.id.imageSeven, R.id.imageEight, R.id.imageNine, R.id.imageTen, R.id.imageEleven, R.id.imageTwele,
            R.id.btn_submit, R.id.imageThirteen, R.id.imageFourteen, R.id.imageFifteen, R.id.btn_submitShipment, R.id.btn_updateShipment, R.id.defectDescribe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

            case R.id.defectDescribe:
                PopupMenu defectPopUp = new PopupMenu(context, defectDescribe);
                defectPopUp.getMenuInflater().inflate(R.menu.defecttype, defectPopUp.getMenu());
                defectPopUp.setOnMenuItemClickListener(item -> {
                    try {
                        defectDescribe.setText(item.getTitle());
                        if (defectDescribe.getText().toString().equalsIgnoreCase("Notebook")) {
                            first_layer.setVisibility(View.VISIBLE);
                            second_layer.setVisibility(View.VISIBLE);
                            motherboard_check.setVisibility(View.VISIBLE);
                            cpuMissing_check.setVisibility(View.VISIBLE);
                            secreenSpot_check.setVisibility(View.VISIBLE);
                            screenBrokrn_check.setVisibility(View.VISIBLE);
                            noDefects_check.setVisibility(View.VISIBLE);
                            engraving_check.setVisibility(View.VISIBLE);
                            permanentMarking_check.setVisibility(View.VISIBLE);
                            chassisCracked_check.setVisibility(View.VISIBLE);
                            part_check.setVisibility(View.GONE);
                            biosLocked_check.setVisibility(View.GONE);
                            chassis2_check.setVisibility(View.GONE);
                            chassis_check.setVisibility(View.VISIBLE);
                            engraving_check.setVisibility(View.VISIBLE);
                            otherDefect_check.setVisibility(View.VISIBLE);

                        } else if (defectDescribe.getText().toString().equalsIgnoreCase("Desktop")) {
                            first_layer.setVisibility(View.VISIBLE);
                            second_layer.setVisibility(View.VISIBLE);
                            motherboard_check.setVisibility(View.VISIBLE);
                            cpuMissing_check.setVisibility(View.VISIBLE);
                            secreenSpot_check.setVisibility(View.GONE);
                            screenBrokrn_check.setVisibility(View.GONE);
                            biosLocked_check.setVisibility(View.VISIBLE);
                            otherDefect_check.setVisibility(View.VISIBLE);
                            permanentMarking_check.setVisibility(View.VISIBLE);
                            chassisCracked_check.setVisibility(View.GONE);
                            part_check.setVisibility(View.GONE);
                            biosLocked_check.setVisibility(View.VISIBLE);
                            chassis2_check.setVisibility(View.VISIBLE);
                            chassis_check.setVisibility(View.GONE);
                            engraving_check.setVisibility(View.GONE);
                            noDefects_check.setVisibility(View.VISIBLE);

                        } else if (defectDescribe.getText().toString().equalsIgnoreCase("Other Device")) {
                            first_layer.setVisibility(View.VISIBLE);
                            second_layer.setVisibility(View.VISIBLE);
                            motherboard_check.setVisibility(View.VISIBLE);
                            cpuMissing_check.setVisibility(View.GONE);
                            part_check.setVisibility(View.VISIBLE);
                            secreenSpot_check.setVisibility(View.GONE);
                            screenBrokrn_check.setVisibility(View.GONE);
                            biosLocked_check.setVisibility(View.GONE);
                            otherDefect_check.setVisibility(View.GONE);

                            biosLocked_check.setVisibility(View.GONE);
                            chassis2_check.setVisibility(View.GONE);
                            chassis_check.setVisibility(View.GONE);
                            engraving_check.setVisibility(View.GONE);

                            permanentMarking_check.setVisibility(View.GONE);
                            chassis_check.setVisibility(View.GONE);
                            chassisCracked_check.setVisibility(View.GONE);
                            noDefects_check.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

                defectPopUp.show();
                break;

            case R.id.imageOne:
                final AlertDialog.Builder alert = new AlertDialog.Builder(Demo.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image = (TextView) mView.findViewById(R.id.upload_image);
                TextView view_image = (TextView) mView.findViewById(R.id.view_image);
                TextView remove_image = (TextView) mView.findViewById(R.id.remove_image);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
                upload_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        selection = Demo.SELECTION.IMAGE_ONE;
                        showDialog();
                    }
                });
                if (IMAGE_ONEfile == null) {
                    view_image.setVisibility(View.GONE);
                    remove_image.setVisibility(View.GONE);

                } else {
                    view_image.setVisibility(View.VISIBLE);
                    remove_image.setVisibility(View.VISIBLE);
                }
//

                view_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_ONEfile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });


                remove_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_ONEfile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_ONEfile = null;
                                imageOne.setImageBitmap(null);
                                imageOne.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }


                });

                break;

            case R.id.imageSecond:
                final AlertDialog.Builder alertsecond = new AlertDialog.Builder(Demo.this);
                View fView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image2 = (TextView) fView.findViewById(R.id.upload_image);
                TextView view_image2 = (TextView) fView.findViewById(R.id.view_image);
                TextView remove_image2 = (TextView) fView.findViewById(R.id.remove_image);
                alertsecond.setView(fView);
                final AlertDialog alertDialog2 = alertsecond.create();
                alertDialog2.setCanceledOnTouchOutside(true);
                alertDialog2.show();
                upload_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                        selection = Demo.SELECTION.IMAGE_TWO;
                        showDialog();
                    }
                });
                if (IMAGE_TWOFile == null) {
                    view_image2.setVisibility(View.GONE);
                    remove_image2.setVisibility(View.GONE);

                } else {
                    view_image2.setVisibility(View.VISIBLE);
                    remove_image2.setVisibility(View.VISIBLE);
                }
                view_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_TWOFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_TWOFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_TWOFile = null;
                                des_two.setText("");
                                imageSecond.setImageBitmap(null);
                                imageSecond.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                break;
            case R.id.imageThird:
                final AlertDialog.Builder alert3 = new AlertDialog.Builder(Demo.this);
                View hView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image3 = (TextView) hView.findViewById(R.id.upload_image);
                TextView view_image3 = (TextView) hView.findViewById(R.id.view_image);
                TextView remove_image3 = (TextView) hView.findViewById(R.id.remove_image);
                alert3.setView(hView);
                final AlertDialog alertDialog3 = alert3.create();
                alertDialog3.setCanceledOnTouchOutside(true);
                alertDialog3.show();
                upload_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog3.dismiss();
                        selection = SELECTION.IMAGE_THREE;
                        showDialog();
                    }
                });
                if (IMAGE_THREEFile == null) {
                    view_image3.setVisibility(View.GONE);
                    remove_image3.setVisibility(View.GONE);

                } else {
                    view_image3.setVisibility(View.VISIBLE);
                    remove_image3.setVisibility(View.VISIBLE);
                }
                view_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog3.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_THREEFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        Glide.with(context).load(scaled).into(photoView);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog3.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_THREEFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_THREEFile = null;
                                des_three.setText("");
                                imageThird.setImageBitmap(null);
                                imageThird.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }

                });

                break;
            case R.id.imageFourth:
                final AlertDialog.Builder alert4 = new AlertDialog.Builder(Demo.this);
                View kView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image4 = (TextView) kView.findViewById(R.id.upload_image);
                TextView view_image4 = (TextView) kView.findViewById(R.id.view_image);
                TextView remove_image4 = (TextView) kView.findViewById(R.id.remove_image);
                alert4.setView(kView);
                final AlertDialog alertDialog4 = alert4.create();
                alertDialog4.setCanceledOnTouchOutside(true);
                alertDialog4.show();
                upload_image4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog4.dismiss();
                        selection = SELECTION.IMAGE_FOUR;
                        showDialog();
                    }
                });
                if (IMAGE_FOURFile == null) {
                    view_image4.setVisibility(View.GONE);
                    remove_image4.setVisibility(View.GONE);

                } else {
                    view_image4.setVisibility(View.VISIBLE);
                    remove_image4.setVisibility(View.VISIBLE);
                }

                view_image4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog4.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_FOURFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
//                        Glide.with(context).load(scaled).into(photoView);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog4.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_FOURFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_FOURFile = null;
                                des_four.setText("");
                                imageFourth.setImageBitmap(null);
                                imageFourth.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });


                break;
            case R.id.imageFive:
                final AlertDialog.Builder alert5 = new AlertDialog.Builder(Demo.this);
                View pView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image5 = (TextView) pView.findViewById(R.id.upload_image);
                TextView view_image5 = (TextView) pView.findViewById(R.id.view_image);
                TextView remove_image5 = (TextView) pView.findViewById(R.id.remove_image);
                alert5.setView(pView);
                final AlertDialog alertDialog5 = alert5.create();
                alertDialog5.setCanceledOnTouchOutside(true);
                alertDialog5.show();
                upload_image5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog5.dismiss();
                        selection = SELECTION.IMAGE_FIVE;
                        showDialog();
                    }
                });
                if (IMAGE_FIVEFile == null) {
                    view_image5.setVisibility(View.GONE);
                    remove_image5.setVisibility(View.GONE);

                } else {
                    view_image5.setVisibility(View.VISIBLE);
                    remove_image5.setVisibility(View.VISIBLE);
                }
                view_image5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog5.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_FIVEFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);

                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog5.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_FIVEFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_FIVEFile = null;
                                des_five.setText("");
                                imageFive.setImageBitmap(null);
                                imageFive.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;
            case R.id.imageSix:
                final AlertDialog.Builder alert6 = new AlertDialog.Builder(Demo.this);
                View zView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image6 = (TextView) zView.findViewById(R.id.upload_image);
                TextView view_image6 = (TextView) zView.findViewById(R.id.view_image);
                TextView remove_image6 = (TextView) zView.findViewById(R.id.remove_image);
                alert6.setView(zView);
                final AlertDialog alertDialog6 = alert6.create();
                alertDialog6.setCanceledOnTouchOutside(true);
                alertDialog6.show();

                upload_image6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog6.dismiss();
                        selection = SELECTION.IMAGE_SIX;
                        showDialog();
                    }
                });


                if (IMAGE_SIXFile == null) {
                    view_image6.setVisibility(View.GONE);
                    remove_image6.setVisibility(View.GONE);

                } else {
                    view_image6.setVisibility(View.VISIBLE);
                    remove_image6.setVisibility(View.VISIBLE);
                }
                view_image6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog6.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_SIXFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog6.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_SIXFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_SIXFile = null;
                                des_six.setText("");
                                imageSix.setImageBitmap(null);
                                imageSix.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;

            case R.id.imageSeven:
                final AlertDialog.Builder alert7 = new AlertDialog.Builder(Demo.this);
                View seveenView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image7 = (TextView) seveenView.findViewById(R.id.upload_image);
                TextView view_image7 = (TextView) seveenView.findViewById(R.id.view_image);
                TextView remove_image7 = (TextView) seveenView.findViewById(R.id.remove_image);
                alert7.setView(seveenView);
                final AlertDialog alertDialog7 = alert7.create();
                alertDialog7.setCanceledOnTouchOutside(true);
                alertDialog7.show();

                upload_image7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog7.dismiss();
                        selection = SELECTION.IMAGE_SEVEN;
                        showDialog();
                    }
                });


                if (IMAGE_SEVENFile == null) {
                    view_image7.setVisibility(View.GONE);
                    remove_image7.setVisibility(View.GONE);

                } else {
                    view_image7.setVisibility(View.VISIBLE);
                    remove_image7.setVisibility(View.VISIBLE);
                }
                view_image7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog7.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_SEVENFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog7.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_SEVENFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_SEVENFile = null;
                                des_seven.setText("");
                                imageSeven.setImageBitmap(null);
                                imageSeven.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;


            case R.id.imageEight:
                final AlertDialog.Builder alert8 = new AlertDialog.Builder(Demo.this);
                View eightView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image8 = (TextView) eightView.findViewById(R.id.upload_image);
                TextView view_image8 = (TextView) eightView.findViewById(R.id.view_image);
                TextView remove_image8 = (TextView) eightView.findViewById(R.id.remove_image);
                alert8.setView(eightView);
                final AlertDialog alertDialog8 = alert8.create();
                alertDialog8.setCanceledOnTouchOutside(true);
                alertDialog8.show();

                upload_image8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog8.dismiss();
                        selection = SELECTION.IMAGE_EIGHT;
                        showDialog();
                    }
                });


                if (IMAGE_EIGHTFile == null) {
                    view_image8.setVisibility(View.GONE);
                    remove_image8.setVisibility(View.GONE);

                } else {
                    view_image8.setVisibility(View.VISIBLE);
                    remove_image8.setVisibility(View.VISIBLE);
                }
                view_image8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog8.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_EIGHTFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog8.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_EIGHTFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_EIGHTFile = null;
                                des_Eight.setText("");
                                imageEight.setImageBitmap(null);
                                imageEight.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;


            case R.id.imageNine:
                final AlertDialog.Builder alert9 = new AlertDialog.Builder(Demo.this);
                View nineView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image9 = (TextView) nineView.findViewById(R.id.upload_image);
                TextView view_image9 = (TextView) nineView.findViewById(R.id.view_image);
                TextView remove_image9 = (TextView) nineView.findViewById(R.id.remove_image);
                alert9.setView(nineView);
                final AlertDialog alertDialog9 = alert9.create();
                alertDialog9.setCanceledOnTouchOutside(true);
                alertDialog9.show();

                upload_image9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog9.dismiss();
                        selection = SELECTION.IMAGE_NINE;
                        showDialog();
                    }
                });


                if (IMAGE_NINEFile == null) {
                    view_image9.setVisibility(View.GONE);
                    remove_image9.setVisibility(View.GONE);

                } else {
                    view_image9.setVisibility(View.VISIBLE);
                    remove_image9.setVisibility(View.VISIBLE);
                }
                view_image9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog9.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_NINEFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog9.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_NINEFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_NINEFile = null;
                                des_nine.setText("");
                                imageNine.setImageBitmap(null);
                                imageNine.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;


            case R.id.imageTen:
                final AlertDialog.Builder alert10 = new AlertDialog.Builder(Demo.this);
                View tenView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image10 = (TextView) tenView.findViewById(R.id.upload_image);
                TextView view_image10 = (TextView) tenView.findViewById(R.id.view_image);
                TextView remove_image10 = (TextView) tenView.findViewById(R.id.remove_image);
                alert10.setView(tenView);
                final AlertDialog alertDialog10 = alert10.create();
                alertDialog10.setCanceledOnTouchOutside(true);
                alertDialog10.show();

                upload_image10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog10.dismiss();
                        selection = SELECTION.IMAGE_TEN;
                        showDialog();
                    }
                });


                if (IMAGE_TENFile == null) {
                    view_image10.setVisibility(View.GONE);
                    remove_image10.setVisibility(View.GONE);

                } else {
                    view_image10.setVisibility(View.VISIBLE);
                    remove_image10.setVisibility(View.VISIBLE);
                }
                view_image10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog10.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_TENFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog10.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_TENFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_TENFile = null;
                                des_ten.setText("");
                                imageTen.setImageBitmap(null);
                                imageTen.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;


            case R.id.imageEleven:
                final AlertDialog.Builder alert11 = new AlertDialog.Builder(Demo.this);
                View elevenView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image11 = (TextView) elevenView.findViewById(R.id.upload_image);
                TextView view_image11 = (TextView) elevenView.findViewById(R.id.view_image);
                TextView remove_image11 = (TextView) elevenView.findViewById(R.id.remove_image);
                alert11.setView(elevenView);
                final AlertDialog alertDialog11 = alert11.create();
                alertDialog11.setCanceledOnTouchOutside(true);
                alertDialog11.show();

                upload_image11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog11.dismiss();
                        selection = SELECTION.IMAGE_ELEVEN;
                        showDialog();
                    }
                });


                if (IMAGE_ELEVENFile == null) {
                    view_image11.setVisibility(View.GONE);
                    remove_image11.setVisibility(View.GONE);

                } else {
                    view_image11.setVisibility(View.VISIBLE);
                    remove_image11.setVisibility(View.VISIBLE);
                }
                view_image11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog11.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_ELEVENFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog11.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_ELEVENFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_ELEVENFile = null;
                                des_eleven.setText("");
                                imageEleven.setImageBitmap(null);
                                imageEleven.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;


            case R.id.imageTwele:
                final AlertDialog.Builder alert12 = new AlertDialog.Builder(Demo.this);
                View twelveView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image12 = (TextView) twelveView.findViewById(R.id.upload_image);
                TextView view_image12 = (TextView) twelveView.findViewById(R.id.view_image);
                TextView remove_image12 = (TextView) twelveView.findViewById(R.id.remove_image);
                alert12.setView(twelveView);
                final AlertDialog alertDialog12 = alert12.create();
                alertDialog12.setCanceledOnTouchOutside(true);
                alertDialog12.show();

                upload_image12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog12.dismiss();
                        selection = SELECTION.IMAGE_TWELVE;
                        showDialog();
                    }
                });


                if (IMAGE_TWELEFile == null) {
                    view_image12.setVisibility(View.GONE);
                    remove_image12.setVisibility(View.GONE);

                } else {
                    view_image12.setVisibility(View.VISIBLE);
                    remove_image12.setVisibility(View.VISIBLE);
                }
                view_image12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog12.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_TWELEFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog12.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_TWELEFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_TWELEFile = null;
                                des_twele.setText("");
                                imageTwele.setImageBitmap(null);
                                imageTwele.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;

            case R.id.imageThirteen:
                final AlertDialog.Builder alert13 = new AlertDialog.Builder(Demo.this);
                View thirteenView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image13 = (TextView) thirteenView.findViewById(R.id.upload_image);
                TextView view_image13 = (TextView) thirteenView.findViewById(R.id.view_image);
                TextView remove_image13 = (TextView) thirteenView.findViewById(R.id.remove_image);
                alert13.setView(thirteenView);
                final AlertDialog alertDialog13 = alert13.create();
                alertDialog13.setCanceledOnTouchOutside(true);
                alertDialog13.show();

                upload_image13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog13.dismiss();
                        selection = SELECTION.IMAGE_THIRTEEN;
                        showDialog();
                    }
                });


                if (IMAGE_THIRTEENFile == null) {
                    view_image13.setVisibility(View.GONE);
                    remove_image13.setVisibility(View.GONE);

                } else {
                    view_image13.setVisibility(View.VISIBLE);
                    remove_image13.setVisibility(View.VISIBLE);
                }
                view_image13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog13.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_THIRTEENFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                remove_image13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog13.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_THIRTEENFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_THIRTEENFile = null;
                                des_thirteen.setText("");
                                imageThirteen.setImageBitmap(null);
                                imageThirteen.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                break;

            case R.id.imageFourteen:
                final AlertDialog.Builder alert14 = new AlertDialog.Builder(Demo.this);
                View fourteenView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image14 = (TextView) fourteenView.findViewById(R.id.upload_image);
                TextView view_image14 = (TextView) fourteenView.findViewById(R.id.view_image);
                TextView remove_image14 = (TextView) fourteenView.findViewById(R.id.remove_image);
                alert14.setView(fourteenView);
                final AlertDialog alertDialog14 = alert14.create();
                alertDialog14.setCanceledOnTouchOutside(true);
                alertDialog14.show();

                upload_image14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog14.dismiss();
                        selection = SELECTION.IMAGE_FOURTEEN;
                        showDialog();
                    }
                });


                if (IMAGE_FOURTEENFile == null) {
                    view_image14.setVisibility(View.GONE);
                    remove_image14.setVisibility(View.GONE);

                } else {
                    view_image14.setVisibility(View.VISIBLE);
                    remove_image14.setVisibility(View.VISIBLE);
                }
                view_image14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog14.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_FOURTEENFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });
                remove_image14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog14.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_FOURTEENFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_FOURTEENFile = null;
                                des_fourteen.setText("");
                                imageFourteen.setImageBitmap(null);
                                imageFourteen.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                break;

            case R.id.imageFifteen:
                final AlertDialog.Builder alert15 = new AlertDialog.Builder(Demo.this);
                View fifteenView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image15 = (TextView) fifteenView.findViewById(R.id.upload_image);
                TextView view_image15 = (TextView) fifteenView.findViewById(R.id.view_image);
                TextView remove_image15 = (TextView) fifteenView.findViewById(R.id.remove_image);
                alert15.setView(fifteenView);
                final AlertDialog alertDialog15 = alert15.create();
                alertDialog15.setCanceledOnTouchOutside(true);
                alertDialog15.show();

                upload_image15.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog15.dismiss();
                        selection = SELECTION.IMAGE_FIFTEEN;
                        showDialog();
                    }
                });


                if (IMAGE_FIFTEENFile == null) {
                    view_image15.setVisibility(View.GONE);
                    remove_image15.setVisibility(View.GONE);

                } else {
                    view_image15.setVisibility(View.VISIBLE);
                    remove_image15.setVisibility(View.VISIBLE);
                }
                view_image15.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog15.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Demo.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_FIFTEENFile.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        photoView.setImageBitmap(scaled);
                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });
                remove_image15.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog15.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("Remove", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_FIFTEENFile.getAbsolutePath());
                            if (file.exists()) {
                                file.delete();
                                IMAGE_FIFTEENFile = null;
                                des_fifteen.setText("");
                                imageFifteen.setImageBitmap(null);
                                imageFifteen.setImageResource(R.drawable.choose_photo);
                            } else {

                            }

                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                break;


            case R.id.btn_submit:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getPhotoUpload();
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;
            case R.id.btn_submitShipment:
                if (ValidationShip()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        if (PreferencesManager.getInstance(context).getAccept().equalsIgnoreCase("no")) {
                            getShipUpload();
                        } else if (PreferencesManager.getInstance(context).getAccept().equalsIgnoreCase("yes")) {
                            getShipUploadReject();
                        }
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;
            case R.id.btn_updateShipment:
                if (ValidationShip()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getShipUpdated(hash);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
                break;
        }


    }

    /*Selection Images*/
    private enum SELECTION {
        IMAGE_ONE, IMAGE_TWO, IMAGE_THREE, IMAGE_FOUR, IMAGE_FIVE, IMAGE_SIX, IMAGE_SEVEN, IMAGE_EIGHT, IMAGE_NINE, IMAGE_TEN, IMAGE_ELEVEN, IMAGE_TWELVE, IMAGE_THIRTEEN, IMAGE_FOURTEEN, IMAGE_FIFTEEN
    }


    private SELECTION selection;

    private void showDialog() {
        ImagePicker.Companion.with(this)
                .start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File outputFile = FileUtils.getFile(context, data.getData());
        switch (selection) {
            case IMAGE_ONE:
            case IMAGE_TWO:
            case IMAGE_THREE:
            case IMAGE_FOUR:
            case IMAGE_FIVE:
            case IMAGE_SIX:
            case IMAGE_SEVEN:
            case IMAGE_EIGHT:
            case IMAGE_NINE:
            case IMAGE_TEN:
            case IMAGE_ELEVEN:
            case IMAGE_TWELVE:
            case IMAGE_THIRTEEN:
            case IMAGE_FOURTEEN:
            case IMAGE_FIFTEEN:

                try {
                    Intent intent = new ImageEditorIntentBuilder(this, data.getData().getPath(), outputFile.getAbsolutePath())
                            .withRotateFeature()
                            .withPaintFeature()
                            .withCropFeature()
                            .withStickerFeature()
                            .forcePortrait(true)  // Add this to force portrait mode (It's set to false by default)
//                            .forcePortrait(false)
                            .build();
                    EditImageActivity.start(context, intent, PHOTO_EDITOR_REQUEST_CODE);
                } catch (Exception e) {
//                    Toast.makeText(this, R.string.iamutkarshtiwari_github_io_ananas_not_selected, Toast.LENGTH_SHORT).show();
                    Log.e("Demo App", e.getMessage());
                }
                break;
        }

        if (requestCode == PHOTO_EDITOR_REQUEST_CODE) {
            String newFilePath = data.getStringExtra(ImageEditorIntentBuilder.OUTPUT_PATH);
            boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IS_IMAGE_EDITED, false);
            switch (selection) {
                case IMAGE_ONE:
                    if (resultCode == RESULT_OK) {
                        File fileONE = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileONE.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageOne.setImageBitmap(scaled);
                        try {
                            IMAGE_ONEfile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (resultCode == PHOTO_EDITOR_REQUEST_CODE) {
//                        Exception error = newFilePath.getError();
                    }
//                        IMAGE_ONEfile = Compressor.getDefault(context).compressToFile(fileONE);

                    break;

                case IMAGE_TWO:
                    if (resultCode == RESULT_OK) {
                        File fileTWO = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileTWO.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageSecond.setImageBitmap(scaled);
                        try {
                            IMAGE_TWOFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileTWO);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_THREE:
                    if (resultCode == RESULT_OK) {
                        File fileTHREE = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileTHREE.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageThird.setImageBitmap(scaled);
                        try {
                            IMAGE_THREEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileTHREE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_FOUR:
                    if (resultCode == RESULT_OK) {
                        File fileFOUR = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileFOUR.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageFourth.setImageBitmap(scaled);
                        try {
                            IMAGE_FOURFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileFOUR);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case IMAGE_FIVE:
                    if (resultCode == RESULT_OK) {
                        File fileFIVE = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileFIVE.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageFive.setImageBitmap(scaled);
                        try {
                            IMAGE_FIVEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileFIVE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_SIX:
                    if (resultCode == RESULT_OK) {
                        File fileSIX = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileSIX.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageSix.setImageBitmap(scaled);
                        try {
                            IMAGE_SIXFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileSIX);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_SEVEN:
                    if (resultCode == RESULT_OK) {
                        File fileSEVEN = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileSEVEN.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageSeven.setImageBitmap(scaled);
                        try {
                            IMAGE_SEVENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileSEVEN);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_EIGHT:
                    if (resultCode == RESULT_OK) {
                        File fileEIGHT = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileEIGHT.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageEight.setImageBitmap(scaled);
                        try {
                            IMAGE_EIGHTFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileEIGHT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_NINE:
                    if (resultCode == RESULT_OK) {
                        File fileNINE = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileNINE.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageNine.setImageBitmap(scaled);
                        try {
                            IMAGE_NINEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileNINE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
//
                case IMAGE_TEN:
                    if (resultCode == RESULT_OK) {
                        File fileTEN = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileTEN.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageTen.setImageBitmap(scaled);
                        try {
                            IMAGE_TENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileTEN);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IMAGE_ELEVEN:
                    if (resultCode == RESULT_OK) {
                        File fileELEVEN = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileELEVEN.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageEleven.setImageBitmap(scaled);
                        try {
                            IMAGE_ELEVENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileELEVEN);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
//
                case IMAGE_TWELVE:
                    if (resultCode == RESULT_OK) {
                        File fileTWELE = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileTWELE.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageTwele.setImageBitmap(scaled);
                        try {
                            IMAGE_TWELEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileTWELE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case IMAGE_THIRTEEN:
                    if (resultCode == RESULT_OK) {
                        File fileThirteen = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileThirteen.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageThirteen.setImageBitmap(scaled);
                        try {
                            IMAGE_THIRTEENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileThirteen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case IMAGE_FOURTEEN:
                    if (resultCode == RESULT_OK) {
                        File fileFourteen = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileFourteen.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageFourteen.setImageBitmap(scaled);
                        try {
                            IMAGE_FOURTEENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileFourteen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case IMAGE_FIFTEEN:
                    if (resultCode == RESULT_OK) {
                        File fileFifteen = new File(newFilePath);
                        Bitmap bitmapImage = BitmapFactory.decodeFile(fileFifteen.getAbsolutePath());
                        int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                        imageFifteen.setImageBitmap(scaled);
                        try {
                            IMAGE_FIFTEENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileFifteen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }


    public void getPhotoUpload() {
        try {
            showProgressDialog();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody addpost = RequestBody.create(MediaType.parse("text/plain"), "addpost");
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "details");

            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null, body7 = null,
                    body8 = null, body9 = null, body10 = null, body11 = null, body12 = null;
            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null,
                    desc7 = null, desc8 = null, desc9 = null, desc10 = null, desc11 = null, desc12 = null;

            if (IMAGE_ONEfile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("file1/*"), IMAGE_ONEfile);
                desc1 = RequestBody.create(MediaType.parse("desc1"), desc1_st);
                body = MultipartBody.Part.createFormData("file1", IMAGE_ONEfile.getName(), requestBody);
            }
            if (IMAGE_TWOFile != null) {
                RequestBody requestBody2 = RequestBody.create(MediaType.parse("file2/*"), IMAGE_TWOFile);
                desc2 = RequestBody.create(MediaType.parse("desc2"), desc2_st);
                body2 = MultipartBody.Part.createFormData("file2", IMAGE_TWOFile.getName(), requestBody2);
            }
            if (IMAGE_THREEFile != null) {
                RequestBody requestBody3 = RequestBody.create(MediaType.parse("file3/*"), IMAGE_THREEFile);
                desc3 = RequestBody.create(MediaType.parse("desc3"), desc3_st);
                body3 = MultipartBody.Part.createFormData("file3", IMAGE_THREEFile.getName(), requestBody3);
            }
            if (IMAGE_FOURFile != null) {
                RequestBody requestBody4 = RequestBody.create(MediaType.parse("file4/*"), IMAGE_FOURFile);
                desc4 = RequestBody.create(MediaType.parse("desc4"), desc4_st);
                body4 = MultipartBody.Part.createFormData("file4", IMAGE_FOURFile.getName(), requestBody4);
            }
            if (IMAGE_FIVEFile != null) {
                RequestBody requestBody5 = RequestBody.create(MediaType.parse("file5/*"), IMAGE_FIVEFile);
                desc5 = RequestBody.create(MediaType.parse("desc5"), desc5_st);
                body5 = MultipartBody.Part.createFormData("file5", IMAGE_FIVEFile.getName(), requestBody5);
            }
            if (IMAGE_SIXFile != null) {
                RequestBody requestBody6 = RequestBody.create(MediaType.parse("file6/*"), IMAGE_SIXFile);
                desc6 = RequestBody.create(MediaType.parse("desc6"), desc6_st);
                body6 = MultipartBody.Part.createFormData("file6", IMAGE_SIXFile.getName(), requestBody6);
            }

            if (IMAGE_SEVENFile != null) {
                RequestBody requestBody7 = RequestBody.create(MediaType.parse("file7/*"), IMAGE_SEVENFile);
                desc7 = RequestBody.create(MediaType.parse("desc7"), desc7_st);
                body7 = MultipartBody.Part.createFormData("file7", IMAGE_SEVENFile.getName(), requestBody7);
            }

            if (IMAGE_EIGHTFile != null) {
                RequestBody requestBody8 = RequestBody.create(MediaType.parse("file8/*"), IMAGE_EIGHTFile);
                desc8 = RequestBody.create(MediaType.parse("desc8"), desc8_st);
                body8 = MultipartBody.Part.createFormData("file8", IMAGE_EIGHTFile.getName(), requestBody8);
            }

            if (IMAGE_NINEFile != null) {
                RequestBody requestBody9 = RequestBody.create(MediaType.parse("file9/*"), IMAGE_NINEFile);
                desc9 = RequestBody.create(MediaType.parse("desc9"), desc9_st);
                body9 = MultipartBody.Part.createFormData("file9", IMAGE_NINEFile.getName(), requestBody9);
            }

            if (IMAGE_TENFile != null) {
                RequestBody requestBody10 = RequestBody.create(MediaType.parse("file10/*"), IMAGE_TENFile);
                desc10 = RequestBody.create(MediaType.parse("desc10"), desc10_st);
                body10 = MultipartBody.Part.createFormData("file10", IMAGE_TENFile.getName(), requestBody10);
            }

            if (IMAGE_ELEVENFile != null) {
                RequestBody requestBody11 = RequestBody.create(MediaType.parse("file11/*"), IMAGE_ELEVENFile);
                desc11 = RequestBody.create(MediaType.parse("desc11"), desc11_st);
                body11 = MultipartBody.Part.createFormData("file11", IMAGE_ELEVENFile.getName(), requestBody11);
            }

            if (IMAGE_TWELEFile != null) {
                RequestBody requestBody12 = RequestBody.create(MediaType.parse("file12/*"), IMAGE_TWELEFile);
                desc12 = RequestBody.create(MediaType.parse("desc12"), desc12_st);
                body12 = MultipartBody.Part.createFormData("file12", IMAGE_TWELEFile.getName(), requestBody12);
            }
            RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getUid());
            RequestBody crn = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCrnID());
            RequestBody device_type = RequestBody.create(MediaType.parse("text/plain"), describeDefect_st);
            RequestBody defect = RequestBody.create(MediaType.parse("text/plain"), motherboard_check_st + " " + chassis_check_st + " " + permanentMarking_check_st + " " + secreenSpot_check_st + " " + otherDefect_check_st
                    + " " + cpuMissing_check_st + " " + chassisCracked_check_st + " " + screenBrokrn_check_st + " " + biosLocked_check_st + " " + noDefects_check_st + "" + engraving_check_st + "" + chassis2_check_st + "" + part_check_st);
            Log.i("token>>>", token.toString());
            Log.i("addpost>>", addpost.toString());
            Log.i("description>>", description.toString());


            Call<UploadPhotoResponse> photoCall = apiServices.UploadFile6(token
                    , addpost, description,
                    body, body2, body3, body4, body5, body6, body7, body8, body9, body10, body11, body12, desc1, desc2, desc3, desc4,
                    desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12,
                    uid, crn, device_type, defect);

            photoCall.enqueue(new Callback<UploadPhotoResponse>() {
                @Override
                public void onResponse(Call<UploadPhotoResponse> call, Response<UploadPhotoResponse> response) {
//                    hideLoading();
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        PreferencesManager.getInstance(context).setFileDesc(response.body().getFilesDesc());

                        AlertDialog.Builder builder = new AlertDialog.Builder(Demo.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText(R.string.dialog_success);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                goToActivityWithFinish(Demo.this, MainContainer.class, null);
                            }
                        });


                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
                    }

                }

                @Override
                public void onFailure(Call<UploadPhotoResponse> call, Throwable t) {

                }
            });


        } catch (
                Exception e) {
            showMessage("Something went wrong please check token");
            e.printStackTrace();
        }

    }

    public void getShipUpload() {
        try {
            showProgressDialog();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "addship");
            RequestBody crn = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCrnID());
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody declr_tick = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCheckTick());
            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null, body7 = null,
                    body8 = null, body9 = null, body10 = null, body11 = null, body12 = null, body13 = null, body14 = null, body15 = null, signatureBody = null;
            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null,
                    desc7 = null, desc8 = null, desc9 = null, desc10 = null, desc11 = null, desc12 = null, desc13 = null, desc14 = null, desc15 = null;

            if (IMAGE_ONEfile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("file1/*"), IMAGE_ONEfile);
                desc1 = RequestBody.create(MediaType.parse("desc1"), desc1_st);
                body = MultipartBody.Part.createFormData("file1", IMAGE_ONEfile.getName(), requestBody);
            }
            if (IMAGE_TWOFile != null) {
                RequestBody requestBody2 = RequestBody.create(MediaType.parse("file2/*"), IMAGE_TWOFile);
                desc2 = RequestBody.create(MediaType.parse("desc2"), desc2_st);
                body2 = MultipartBody.Part.createFormData("file2", IMAGE_TWOFile.getName(), requestBody2);
            }
            if (IMAGE_THREEFile != null) {
                RequestBody requestBody3 = RequestBody.create(MediaType.parse("file3/*"), IMAGE_THREEFile);
                desc3 = RequestBody.create(MediaType.parse("desc3"), desc3_st);
                body3 = MultipartBody.Part.createFormData("file3", IMAGE_THREEFile.getName(), requestBody3);
            }
            if (IMAGE_FOURFile != null) {
                RequestBody requestBody4 = RequestBody.create(MediaType.parse("file4/*"), IMAGE_FOURFile);
                desc4 = RequestBody.create(MediaType.parse("desc4"), desc4_st);
                body4 = MultipartBody.Part.createFormData("file4", IMAGE_FOURFile.getName(), requestBody4);
            }
            if (IMAGE_FIVEFile != null) {
                RequestBody requestBody5 = RequestBody.create(MediaType.parse("file5/*"), IMAGE_FIVEFile);
                desc5 = RequestBody.create(MediaType.parse("desc5"), desc5_st);
                body5 = MultipartBody.Part.createFormData("file5", IMAGE_FIVEFile.getName(), requestBody5);
            }
            if (IMAGE_SIXFile != null) {
                RequestBody requestBody6 = RequestBody.create(MediaType.parse("file6/*"), IMAGE_SIXFile);
                desc6 = RequestBody.create(MediaType.parse("desc6"), desc6_st);
                body6 = MultipartBody.Part.createFormData("file6", IMAGE_SIXFile.getName(), requestBody6);
            }

            if (IMAGE_SEVENFile != null) {
                RequestBody requestBody7 = RequestBody.create(MediaType.parse("file7/*"), IMAGE_SEVENFile);
                desc7 = RequestBody.create(MediaType.parse("desc7"), desc7_st);
                body7 = MultipartBody.Part.createFormData("file7", IMAGE_SEVENFile.getName(), requestBody7);
            }

            if (IMAGE_EIGHTFile != null) {
                RequestBody requestBody8 = RequestBody.create(MediaType.parse("file8/*"), IMAGE_EIGHTFile);
                desc8 = RequestBody.create(MediaType.parse("desc8"), desc8_st);
                body8 = MultipartBody.Part.createFormData("file8", IMAGE_EIGHTFile.getName(), requestBody8);
            }

            if (IMAGE_NINEFile != null) {
                RequestBody requestBody9 = RequestBody.create(MediaType.parse("file9/*"), IMAGE_NINEFile);
                desc9 = RequestBody.create(MediaType.parse("desc9"), desc9_st);
                body9 = MultipartBody.Part.createFormData("file9", IMAGE_NINEFile.getName(), requestBody9);
            }

            if (IMAGE_TENFile != null) {
                RequestBody requestBody10 = RequestBody.create(MediaType.parse("file10/*"), IMAGE_TENFile);
                desc10 = RequestBody.create(MediaType.parse("desc10"), desc10_st);
                body10 = MultipartBody.Part.createFormData("file10", IMAGE_TENFile.getName(), requestBody10);
            }

            if (IMAGE_ELEVENFile != null) {
                RequestBody requestBody11 = RequestBody.create(MediaType.parse("file11/*"), IMAGE_ELEVENFile);
                desc11 = RequestBody.create(MediaType.parse("desc11"), desc11_st);
                body11 = MultipartBody.Part.createFormData("file11", IMAGE_ELEVENFile.getName(), requestBody11);
            }

            if (IMAGE_TWELEFile != null) {
                RequestBody requestBody12 = RequestBody.create(MediaType.parse("file12/*"), IMAGE_TWELEFile);
                desc12 = RequestBody.create(MediaType.parse("desc12"), desc12_st);
                body12 = MultipartBody.Part.createFormData("file12", IMAGE_TWELEFile.getName(), requestBody12);
            }
            if (IMAGE_THIRTEENFile != null) {
                RequestBody requestBody13 = RequestBody.create(MediaType.parse("file13/*"), IMAGE_THIRTEENFile);
                desc13 = RequestBody.create(MediaType.parse("desc13"), desc13_st);
                body13 = MultipartBody.Part.createFormData("file13", IMAGE_THIRTEENFile.getName(), requestBody13);
            }

            if (IMAGE_FOURTEENFile != null) {
                RequestBody requestBody14 = RequestBody.create(MediaType.parse("file14/*"), IMAGE_FOURTEENFile);
                desc14 = RequestBody.create(MediaType.parse("desc14"), desc14_st);
                body14 = MultipartBody.Part.createFormData("file14", IMAGE_FOURTEENFile.getName(), requestBody14);
            }
            if (IMAGE_FIFTEENFile != null) {
                RequestBody requestBody15 = RequestBody.create(MediaType.parse("file15/*"), IMAGE_FIFTEENFile);
                desc15 = RequestBody.create(MediaType.parse("desc15"), desc15_st);
                body15 = MultipartBody.Part.createFormData("file15", IMAGE_FIFTEENFile.getName(), requestBody15);
            }
            RequestBody input_time = RequestBody.create(MediaType.parse("text/plain"), dateStr + " " + formattedDate);
            RequestBody ship_time = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getDate() + " " + PreferencesManager.getInstance(context).getTimeship());
            RequestBody logistic_company = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCompanyName());
            RequestBody vahicle_type = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getVechileType());
            RequestBody vahicle_container = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getNoOfVechile());
            RequestBody vahicle_number = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getVechileNumber());
            RequestBody box_condition = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getPackagingQuality());
            RequestBody supervisor_name = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getSupervisorName());
            if (SignatureFile != null) {
                RequestBody requestBodySignature = RequestBody.create(MediaType.parse("file15/*"), SignatureFile);
                signatureBody = MultipartBody.Part.createFormData("supervisor_sign", SignatureFile.getName(), requestBodySignature);

            }

            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getMessage());
            RequestBody no_of_staff = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getLogistics_Staff());
            RequestBody no_of_box = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getNoOfBoxes());
            RequestBody no_of_pallets = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getNoOfPallets());
            RequestBody no_of_devices = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getNoOfDevices());
            RequestBody no_of_vahicle = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getNoOfVechile());
            RequestBody supervisor_ph_no = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getPhoneNumber());
            RequestBody is_reject = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getAccept());
            RequestBody box_seal = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getBoxSeal());
            RequestBody eWayBil = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getWAY_BIll());


            Log.i("token>>>", token.toString());
            Log.i("addpost>>", action.toString());
            Call<ShipUploadResponse> photocall = apiServices.ShipUpload(token, action, crn, description, declr_tick, body, body2
                    , body3, body4, body5, body6, body7, body8, body9, body10, body11, body12, body13, body14, body15, desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12, desc13, desc14, desc15
                    , input_time, ship_time, logistic_company, vahicle_type, vahicle_container, vahicle_number, box_condition, supervisor_name, signatureBody, note,
                    no_of_staff, no_of_box, no_of_pallets, no_of_devices, no_of_vahicle, supervisor_ph_no, is_reject, box_seal, eWayBil);
            photocall.enqueue(new Callback<ShipUploadResponse>() {
                @Override
                public void onResponse(Call<ShipUploadResponse> call, Response<ShipUploadResponse> response) {
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//                        PreferencesManager.getInstance(context).setFileDesc(response.body().getFilesDesc());

                        AlertDialog.Builder builder = new AlertDialog.Builder(Demo.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText(R.string.ship_success);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                goToActivityWithFinish(Demo.this, MainContainer.class, null);
                            }
                        });


                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
                    }

                }


                @Override
                public void onFailure(Call<ShipUploadResponse> call, Throwable t) {

                }
            });


        } catch (
                Exception e) {
            showMessage("Something went wrong please check token");
            e.printStackTrace();
        }

    }

    public void getShipUploadReject() {
        try {
            showProgressDialog();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "addship");
            RequestBody crn = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCrnID());
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody declr_tick = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCheckTick());
            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null, body7 = null,
                    body8 = null, body9 = null, body10 = null, body11 = null, body12 = null, body13 = null, body14 = null, body15 = null, signatureBody = null;
            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null,
                    desc7 = null, desc8 = null, desc9 = null, desc10 = null, desc11 = null, desc12 = null, desc13 = null, desc14 = null, desc15 = null;

            if (IMAGE_ONEfile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("file1/*"), IMAGE_ONEfile);
                desc1 = RequestBody.create(MediaType.parse("desc1"), desc1_st);
                body = MultipartBody.Part.createFormData("file1", IMAGE_ONEfile.getName(), requestBody);
            }
            if (IMAGE_TWOFile != null) {
                RequestBody requestBody2 = RequestBody.create(MediaType.parse("file2/*"), IMAGE_TWOFile);
                desc2 = RequestBody.create(MediaType.parse("desc2"), desc2_st);
                body2 = MultipartBody.Part.createFormData("file2", IMAGE_TWOFile.getName(), requestBody2);
            }
            if (IMAGE_THREEFile != null) {
                RequestBody requestBody3 = RequestBody.create(MediaType.parse("file3/*"), IMAGE_THREEFile);
                desc3 = RequestBody.create(MediaType.parse("desc3"), desc3_st);
                body3 = MultipartBody.Part.createFormData("file3", IMAGE_THREEFile.getName(), requestBody3);
            }
            if (IMAGE_FOURFile != null) {
                RequestBody requestBody4 = RequestBody.create(MediaType.parse("file4/*"), IMAGE_FOURFile);
                desc4 = RequestBody.create(MediaType.parse("desc4"), desc4_st);
                body4 = MultipartBody.Part.createFormData("file4", IMAGE_FOURFile.getName(), requestBody4);
            }
            if (IMAGE_FIVEFile != null) {
                RequestBody requestBody5 = RequestBody.create(MediaType.parse("file5/*"), IMAGE_FIVEFile);
                desc5 = RequestBody.create(MediaType.parse("desc5"), desc5_st);
                body5 = MultipartBody.Part.createFormData("file5", IMAGE_FIVEFile.getName(), requestBody5);
            }
            if (IMAGE_SIXFile != null) {
                RequestBody requestBody6 = RequestBody.create(MediaType.parse("file6/*"), IMAGE_SIXFile);
                desc6 = RequestBody.create(MediaType.parse("desc6"), desc6_st);
                body6 = MultipartBody.Part.createFormData("file6", IMAGE_SIXFile.getName(), requestBody6);
            }

            if (IMAGE_SEVENFile != null) {
                RequestBody requestBody7 = RequestBody.create(MediaType.parse("file7/*"), IMAGE_SEVENFile);
                desc7 = RequestBody.create(MediaType.parse("desc7"), desc7_st);
                body7 = MultipartBody.Part.createFormData("file7", IMAGE_SEVENFile.getName(), requestBody7);
            }

            if (IMAGE_EIGHTFile != null) {
                RequestBody requestBody8 = RequestBody.create(MediaType.parse("file8/*"), IMAGE_EIGHTFile);
                desc8 = RequestBody.create(MediaType.parse("desc8"), desc8_st);
                body8 = MultipartBody.Part.createFormData("file8", IMAGE_EIGHTFile.getName(), requestBody8);
            }

            if (IMAGE_NINEFile != null) {
                RequestBody requestBody9 = RequestBody.create(MediaType.parse("file9/*"), IMAGE_NINEFile);
                desc9 = RequestBody.create(MediaType.parse("desc9"), desc9_st);
                body9 = MultipartBody.Part.createFormData("file9", IMAGE_NINEFile.getName(), requestBody9);
            }

            if (IMAGE_TENFile != null) {
                RequestBody requestBody10 = RequestBody.create(MediaType.parse("file10/*"), IMAGE_TENFile);
                desc10 = RequestBody.create(MediaType.parse("desc10"), desc10_st);
                body10 = MultipartBody.Part.createFormData("file10", IMAGE_TENFile.getName(), requestBody10);
            }

            if (IMAGE_ELEVENFile != null) {
                RequestBody requestBody11 = RequestBody.create(MediaType.parse("file11/*"), IMAGE_ELEVENFile);
                desc11 = RequestBody.create(MediaType.parse("desc11"), desc11_st);
                body11 = MultipartBody.Part.createFormData("file11", IMAGE_ELEVENFile.getName(), requestBody11);
            }

            if (IMAGE_TWELEFile != null) {
                RequestBody requestBody12 = RequestBody.create(MediaType.parse("file12/*"), IMAGE_TWELEFile);
                desc12 = RequestBody.create(MediaType.parse("desc12"), desc12_st);
                body12 = MultipartBody.Part.createFormData("file12", IMAGE_TWELEFile.getName(), requestBody12);
            }
            if (IMAGE_THIRTEENFile != null) {
                RequestBody requestBody13 = RequestBody.create(MediaType.parse("file13/*"), IMAGE_THIRTEENFile);
                desc13 = RequestBody.create(MediaType.parse("desc13"), desc13_st);
                body13 = MultipartBody.Part.createFormData("file13", IMAGE_THIRTEENFile.getName(), requestBody13);
            }

            if (IMAGE_FOURTEENFile != null) {
                RequestBody requestBody14 = RequestBody.create(MediaType.parse("file14/*"), IMAGE_FOURTEENFile);
                desc14 = RequestBody.create(MediaType.parse("desc14"), desc14_st);
                body14 = MultipartBody.Part.createFormData("file14", IMAGE_FOURTEENFile.getName(), requestBody14);
            }
            if (IMAGE_FIFTEENFile != null) {
                RequestBody requestBody15 = RequestBody.create(MediaType.parse("file15/*"), IMAGE_FIFTEENFile);
                desc15 = RequestBody.create(MediaType.parse("desc15"), desc15_st);
                body15 = MultipartBody.Part.createFormData("file15", IMAGE_FIFTEENFile.getName(), requestBody15);
            }
            RequestBody input_time = RequestBody.create(MediaType.parse("text/plain"), dateStr + " " + formattedDate);
            RequestBody ship_time = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getDate() + " " + PreferencesManager.getInstance(context).getTimeship());
            RequestBody logistic_company = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCompanyNameReject());
            RequestBody vahicle_type = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody vahicle_container = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody vahicle_number = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody box_condition = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody supervisor_name = RequestBody.create(MediaType.parse("text/plain"), "");
            if (SignatureFile != null) {
                RequestBody requestBodySignature = RequestBody.create(MediaType.parse("file15/*"), SignatureFile);
                signatureBody = MultipartBody.Part.createFormData("supervisor_sign", SignatureFile.getName(), requestBodySignature);

            }

            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getReasonMessage());
            RequestBody no_of_staff = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody no_of_box = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody no_of_pallets = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody no_of_devices = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody no_of_vahicle = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody supervisor_ph_no = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody is_reject = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getAccept());

            Log.i("token>>>", token.toString());
            Log.i("addpost>>", action.toString());
            Call<ShipUploadResponse> photocall = apiServices.ShipUploadResject(token, action, crn, description, declr_tick, body, body2
                    , body3, body4, body5, body6, body7, body8, body9, body10, body11, body12, body13, body14, body15, desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12, desc13, desc14, desc15
                    , input_time, ship_time, logistic_company, vahicle_type, vahicle_container, vahicle_number, box_condition, supervisor_name, signatureBody, note,
                    no_of_staff, no_of_box, no_of_pallets, no_of_devices, no_of_vahicle, supervisor_ph_no, is_reject);
            photocall.enqueue(new Callback<ShipUploadResponse>() {
                @Override
                public void onResponse(Call<ShipUploadResponse> call, Response<ShipUploadResponse> response) {
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Demo.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText(R.string.ship_success);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                goToActivityWithFinish(Demo.this, MainContainer.class, null);
                            }
                        });


                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
                    }

                }


                @Override
                public void onFailure(Call<ShipUploadResponse> call, Throwable t) {

                }
            });


        } catch (
                Exception e) {
            showMessage("Something went wrong please check token");
            e.printStackTrace();
        }

    }


    private boolean Validation() {
        try {
            desc1_st = des_one.getText().toString().trim();
            desc2_st = des_two.getText().toString().trim();
            desc3_st = des_three.getText().toString().trim();
            desc4_st = des_four.getText().toString().trim();
            desc5_st = des_five.getText().toString().trim();
            desc6_st = des_six.getText().toString().trim();
            desc7_st = des_seven.getText().toString().trim();
            desc8_st = des_Eight.getText().toString().trim();
            desc9_st = des_nine.getText().toString().trim();
            desc10_st = des_ten.getText().toString().trim();
            desc11_st = des_eleven.getText().toString().trim();
            desc12_st = des_twele.getText().toString().trim();
            desc13_st = des_thirteen.getText().toString().trim();
            desc14_st = des_fourteen.getText().toString().trim();
            desc15_st = des_fifteen.getText().toString().trim();
            describeDefect_st = defectDescribe.getText().toString();


            if (motherboard_check.isChecked()) {
                motherboard_check_st = "Motherboard Faulty ,";
            }
            if (chassis_check.isChecked()) {
                chassis_check_st = "Chassis Broken ,";
            }
            if (permanentMarking_check.isChecked()) {
                permanentMarking_check_st = "Permanent Marking ,";
            }
            if (secreenSpot_check.isChecked()) {
                secreenSpot_check_st = "Screen Spots ,";
            }
            if (otherDefect_check.isChecked()) {
                otherDefect_check_st = "Other Defects ,";
            }
            if (cpuMissing_check.isChecked()) {
                cpuMissing_check_st = "CPU Missing/Faulty ,";
            }
            if (chassisCracked_check.isChecked()) {
                chassisCracked_check_st = "Chassis Cracked ,";
            }
            if (screenBrokrn_check.isChecked()) {
                screenBrokrn_check_st = "Screen Broken/Line/Missing ,";
            }

            if (biosLocked_check.isChecked()) {
                biosLocked_check_st = "BIOS Locked ,";
            }
            if (noDefects_check.isChecked()) {
                noDefects_check_st = "No Defect Found ,";
            }
            if (engraving_check.isChecked()) {
                engraving_check_st = "Engraving/Scratch ,";
            }
            if (chassis2_check.isChecked()) {
                chassis2_check_st = "Chassis Broken/Cracked ,";
            }

            if (part_check.isChecked()) {
                part_check_st = "Parts Missing faulty ,";
            }

            if (IMAGE_ONEfile == null && IMAGE_TWOFile == null && IMAGE_THREEFile == null && IMAGE_FOURFile == null
                    && IMAGE_FIVEFile == null && IMAGE_SIXFile == null && IMAGE_SEVENFile == null && IMAGE_EIGHTFile == null
                    && IMAGE_NINEFile == null && IMAGE_TENFile == null && IMAGE_ELEVENFile == null && IMAGE_TWELEFile == null) {
                showError("Please select at least one image", des_one);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean ValidationShip() {
        try {

            desc1_st = des_one.getText().toString().trim();
            desc2_st = des_two.getText().toString().trim();
            desc3_st = des_three.getText().toString().trim();
            desc4_st = des_four.getText().toString().trim();
            desc5_st = des_five.getText().toString().trim();
            desc6_st = des_six.getText().toString().trim();
            desc7_st = des_seven.getText().toString().trim();
            desc8_st = des_Eight.getText().toString().trim();
            desc9_st = des_nine.getText().toString().trim();
            desc10_st = des_ten.getText().toString().trim();
            desc11_st = des_eleven.getText().toString().trim();
            desc12_st = des_twele.getText().toString().trim();
            desc13_st = des_thirteen.getText().toString().trim();
            desc14_st = des_fourteen.getText().toString().trim();
            desc15_st = des_fifteen.getText().toString().trim();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
                        if (response.body().getFiles() != null) {
                            imageList = response.body().getFiles();
                            LoggerUtil.logItem(imageList);
                            for (int i = 0; i < imageList.size(); i++) {
                                if (imageList.size() == 1) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 2) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 3) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }
                                } else if (imageList.size() == 4) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }
                                } else if (imageList.size() == 5) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 6) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 7) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }
                                } else if (imageList.size() == 8) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 9) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 10) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }

                                } else if (imageList.size() == 11) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }
                                } else if (imageList.size() == 12) {
                                    if (imageList.get(i).getFile1() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile1();
                                        Picasso.with(context).load(imageUri1).into(imageOne);
                                        des_one.setText(imageList.get(i).getDesc1());
                                        new DownloadsImage().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile2() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile2();
                                        Picasso.with(context).load(imageUri1).into(imageSecond);
                                        des_two.setText(imageList.get(i).getDesc2());
                                        new DownloadsImage2().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile3() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile3();
                                        Picasso.with(context).load(imageUri1).into(imageThird);
                                        des_three.setText(imageList.get(i).getDesc3());
                                        new DownloadsImage3().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile4() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile4();
                                        Picasso.with(context).load(imageUri1).into(imageFourth);
                                        des_four.setText(imageList.get(i).getDesc4());
                                        new DownloadsImage4().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile5() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile5();
                                        Picasso.with(context).load(imageUri1).into(imageFive);
                                        des_five.setText(imageList.get(i).getDesc5());
                                        new DownloadsImage5().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile6() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile6();
                                        Picasso.with(context).load(imageUri1).into(imageSix);
                                        des_six.setText(imageList.get(i).getDesc6());
                                        new DownloadsImage6().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile7() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile7();
                                        Picasso.with(context).load(imageUri1).into(imageSeven);
                                        des_seven.setText(imageList.get(i).getDesc7());
                                        new DownloadsImage7().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile8() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile8();
                                        Picasso.with(context).load(imageUri1).into(imageEight);
                                        des_Eight.setText(imageList.get(i).getDesc8());
                                        new DownloadsImage8().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile9() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile9();
                                        Picasso.with(context).load(imageUri1).into(imageNine);
                                        des_nine.setText(imageList.get(i).getDesc9());
                                        new DownloadsImage9().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile10() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile10();
                                        Picasso.with(context).load(imageUri1).into(imageTen);
                                        des_ten.setText(imageList.get(i).getDesc10());
                                        new DownloadsImage10().execute(imageUri1);
                                    }
                                    if (imageList.get(i).getFile11() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile11();
                                        Picasso.with(context).load(imageUri1).into(imageEleven);
                                        des_eleven.setText(imageList.get(i).getDesc11());
                                        new DownloadsImage11().execute(imageUri1);
                                    }

                                    if (imageList.get(i).getFile12() != null) {
                                        String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageList.get(i).getFile12();
                                        Picasso.with(context).load(imageUri1).into(imageTwele);
                                        des_twele.setText(imageList.get(i).getDesc12());
                                        new DownloadsImage12().execute(imageUri1);
                                    }
                                }

                            }
                        }


                    }

                }
            }

            @Override
            public void onFailure(Call<AssetIDResponse> call, Throwable t) {

            }
        });
    }

    public void checkImages() {
        Log.e("PARAM==>", this.currentTimeDate + ",," + this.hash);
        showLoading();
        Call<ShipImagesResponse> call = apiServices.checkImages("getship", PreferencesManager.getInstance(context).getToken(), currentTimeDate, hash);

        call.enqueue(new Callback<ShipImagesResponse>() {
            @Override
            public void onResponse(Call<ShipImagesResponse> call, Response<ShipImagesResponse> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getStatus().equalsIgnoreCase("success")) {
                   if (response.body().getAllshipments().getFiles() != null) {
                        imageListShip = response.body().getAllshipments().getFiles();
                        LoggerUtil.logItem(imageListShip);
                        for (int i = 0; i < imageListShip.size(); i++) {
                            if (imageListShip.size() == 1) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 2) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }
                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 3) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 4) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 5) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 6) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 7) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 8) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 9) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 10) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }

                                if (imageListShip.get(9).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(9).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTen);
                                    des_ten.setText(imageListShip.get(9).getDesc());
                                    new DownloadsImage10().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 11) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }

                                if (imageListShip.get(9).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(9).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTen);
                                    des_ten.setText(imageListShip.get(9).getDesc());
                                    new DownloadsImage10().execute(imageUri1);
                                }

                                if (imageListShip.get(10).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(10).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEleven);
                                    des_eleven.setText(imageListShip.get(10).getDesc());
                                    new DownloadsImage11().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 12) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }

                                if (imageListShip.get(9).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(9).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTen);
                                    des_ten.setText(imageListShip.get(9).getDesc());
                                    new DownloadsImage10().execute(imageUri1);
                                }

                                if (imageListShip.get(10).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(10).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEleven);
                                    des_eleven.setText(imageListShip.get(10).getDesc());
                                    new DownloadsImage11().execute(imageUri1);
                                }

                                if (imageListShip.get(11).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(11).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTwele);
                                    des_twele.setText(imageListShip.get(11).getDesc());
                                    new DownloadsImage12().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 13) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }

                                if (imageListShip.get(9).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(9).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTen);
                                    des_ten.setText(imageListShip.get(9).getDesc());
                                    new DownloadsImage10().execute(imageUri1);
                                }

                                if (imageListShip.get(10).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(10).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEleven);
                                    des_eleven.setText(imageListShip.get(10).getDesc());
                                    new DownloadsImage11().execute(imageUri1);
                                }

                                if (imageListShip.get(11).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(11).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTwele);
                                    des_twele.setText(imageListShip.get(11).getDesc());
                                    new DownloadsImage12().execute(imageUri1);
                                }

                                if (imageListShip.get(12).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(12).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThirteen);
                                    des_thirteen.setText(imageListShip.get(12).getDesc());
                                    new DownloadsImage13().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 14) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }

                                if (imageListShip.get(9).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(9).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTen);
                                    des_ten.setText(imageListShip.get(9).getDesc());
                                    new DownloadsImage10().execute(imageUri1);
                                }

                                if (imageListShip.get(10).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(10).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEleven);
                                    des_eleven.setText(imageListShip.get(10).getDesc());
                                    new DownloadsImage11().execute(imageUri1);
                                }

                                if (imageListShip.get(11).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(11).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTwele);
                                    des_twele.setText(imageListShip.get(11).getDesc());
                                    new DownloadsImage12().execute(imageUri1);
                                }

                                if (imageListShip.get(12).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(12).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThirteen);
                                    des_thirteen.setText(imageListShip.get(12).getDesc());
                                    new DownloadsImage13().execute(imageUri1);
                                }

                                if (imageListShip.get(13).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(13).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourteen);
                                    des_fourteen.setText(imageListShip.get(13).getDesc());
                                    new DownloadsImage14().execute(imageUri1);
                                }
                            } else if (imageListShip.size() == 15) {
                                if (imageListShip.get(0).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(0).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageOne);
                                    des_one.setText(imageListShip.get(0).getDesc());
                                    new DownloadsImage().execute(imageUri1);
                                }

                                if (imageListShip.get(1).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(1).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSecond);
                                    des_two.setText(imageListShip.get(1).getDesc());
                                    new DownloadsImage2().execute(imageUri1);
                                }
                                if (imageListShip.get(2).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(2).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThird);
                                    des_three.setText(imageListShip.get(2).getDesc());
                                    new DownloadsImage3().execute(imageUri1);
                                }

                                if (imageListShip.get(3).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(3).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourth);
                                    des_four.setText(imageListShip.get(3).getDesc());
                                    new DownloadsImage4().execute(imageUri1);
                                }

                                if (imageListShip.get(4).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(4).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFive);
                                    des_five.setText(imageListShip.get(4).getDesc());
                                    new DownloadsImage5().execute(imageUri1);
                                }

                                if (imageListShip.get(5).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(5).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSix);
                                    des_six.setText(imageListShip.get(5).getDesc());
                                    new DownloadsImage6().execute(imageUri1);
                                }

                                if (imageListShip.get(6).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(6).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageSeven);
                                    des_seven.setText(imageListShip.get(6).getDesc());
                                    new DownloadsImage7().execute(imageUri1);
                                }

                                if (imageListShip.get(7).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(7).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEight);
                                    des_Eight.setText(imageListShip.get(7).getDesc());
                                    new DownloadsImage8().execute(imageUri1);
                                }

                                if (imageListShip.get(8).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(8).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageNine);
                                    des_nine.setText(imageListShip.get(8).getDesc());
                                    new DownloadsImage9().execute(imageUri1);
                                }

                                if (imageListShip.get(9).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(9).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTen);
                                    des_ten.setText(imageListShip.get(9).getDesc());
                                    new DownloadsImage10().execute(imageUri1);
                                }

                                if (imageListShip.get(10).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(10).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageEleven);
                                    des_eleven.setText(imageListShip.get(10).getDesc());
                                    new DownloadsImage11().execute(imageUri1);
                                }

                                if (imageListShip.get(11).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(11).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageTwele);
                                    des_twele.setText(imageListShip.get(11).getDesc());
                                    new DownloadsImage12().execute(imageUri1);
                                }

                                if (imageListShip.get(12).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(12).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageThirteen);
                                    des_thirteen.setText(imageListShip.get(12).getDesc());
                                    new DownloadsImage13().execute(imageUri1);
                                }

                                if (imageListShip.get(13).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(13).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFourteen);
                                    des_fourteen.setText(imageListShip.get(13).getDesc());
                                    new DownloadsImage14().execute(imageUri1);
                                }

                                if (imageListShip.get(14).getFile() != null) {
                                    String imageUri1 = BuildConfig.BASE_URL_FORIMAGE + imageListShip.get(14).getFile();
                                    Picasso.with(context).load(imageUri1).into(imageFifteen);
                                    des_fifteen.setText(imageListShip.get(14).getDesc());
                                    new DownloadsImage15().execute(imageUri1);
                                }
                            }


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ShipImagesResponse> call, Throwable t) {

            }
        });

    }


    public void getShipUpdated(String key) {
        try {
            showProgressDialog();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "addship");
            RequestBody hash = RequestBody.create(MediaType.parse("text/plain"),key );
            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null, body7 = null,
                    body8 = null, body9 = null, body10 = null, body11 = null, body12 = null, body13 = null, body14 = null, body15 = null;
            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null,
                    desc7 = null, desc8 = null, desc9 = null, desc10 = null, desc11 = null, desc12 = null, desc13 = null, desc14 = null, desc15 = null;

            if (IMAGE_ONEfile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("file1/*"), IMAGE_ONEfile);
                desc1 = RequestBody.create(MediaType.parse("desc1"), desc1_st);
                body = MultipartBody.Part.createFormData("file1", IMAGE_ONEfile.getName(), requestBody);
            }
            if (IMAGE_TWOFile != null) {
                RequestBody requestBody2 = RequestBody.create(MediaType.parse("file2/*"), IMAGE_TWOFile);
                desc2 = RequestBody.create(MediaType.parse("desc2"), desc2_st);
                body2 = MultipartBody.Part.createFormData("file2", IMAGE_TWOFile.getName(), requestBody2);
            }
            if (IMAGE_THREEFile != null) {
                RequestBody requestBody3 = RequestBody.create(MediaType.parse("file3/*"), IMAGE_THREEFile);
                desc3 = RequestBody.create(MediaType.parse("desc3"), desc3_st);
                body3 = MultipartBody.Part.createFormData("file3", IMAGE_THREEFile.getName(), requestBody3);
            }
            if (IMAGE_FOURFile != null) {
                RequestBody requestBody4 = RequestBody.create(MediaType.parse("file4/*"), IMAGE_FOURFile);
                desc4 = RequestBody.create(MediaType.parse("desc4"), desc4_st);
                body4 = MultipartBody.Part.createFormData("file4", IMAGE_FOURFile.getName(), requestBody4);
            }
            if (IMAGE_FIVEFile != null) {
                RequestBody requestBody5 = RequestBody.create(MediaType.parse("file5/*"), IMAGE_FIVEFile);
                desc5 = RequestBody.create(MediaType.parse("desc5"), desc5_st);
                body5 = MultipartBody.Part.createFormData("file5", IMAGE_FIVEFile.getName(), requestBody5);
            }
            if (IMAGE_SIXFile != null) {
                RequestBody requestBody6 = RequestBody.create(MediaType.parse("file6/*"), IMAGE_SIXFile);
                desc6 = RequestBody.create(MediaType.parse("desc6"), desc6_st);
                body6 = MultipartBody.Part.createFormData("file6", IMAGE_SIXFile.getName(), requestBody6);
            }

            if (IMAGE_SEVENFile != null) {
                RequestBody requestBody7 = RequestBody.create(MediaType.parse("file7/*"), IMAGE_SEVENFile);
                desc7 = RequestBody.create(MediaType.parse("desc7"), desc7_st);
                body7 = MultipartBody.Part.createFormData("file7", IMAGE_SEVENFile.getName(), requestBody7);
            }

            if (IMAGE_EIGHTFile != null) {
                RequestBody requestBody8 = RequestBody.create(MediaType.parse("file8/*"), IMAGE_EIGHTFile);
                desc8 = RequestBody.create(MediaType.parse("desc8"), desc8_st);
                body8 = MultipartBody.Part.createFormData("file8", IMAGE_EIGHTFile.getName(), requestBody8);
            }

            if (IMAGE_NINEFile != null) {
                RequestBody requestBody9 = RequestBody.create(MediaType.parse("file9/*"), IMAGE_NINEFile);
                desc9 = RequestBody.create(MediaType.parse("desc9"), desc9_st);
                body9 = MultipartBody.Part.createFormData("file9", IMAGE_NINEFile.getName(), requestBody9);
            }

            if (IMAGE_TENFile != null) {
                RequestBody requestBody10 = RequestBody.create(MediaType.parse("file10/*"), IMAGE_TENFile);
                desc10 = RequestBody.create(MediaType.parse("desc10"), desc10_st);
                body10 = MultipartBody.Part.createFormData("file10", IMAGE_TENFile.getName(), requestBody10);
            }

            if (IMAGE_ELEVENFile != null) {
                RequestBody requestBody11 = RequestBody.create(MediaType.parse("file11/*"), IMAGE_ELEVENFile);
                desc11 = RequestBody.create(MediaType.parse("desc11"), desc11_st);
                body11 = MultipartBody.Part.createFormData("file11", IMAGE_ELEVENFile.getName(), requestBody11);
            }

            if (IMAGE_TWELEFile != null) {
                RequestBody requestBody12 = RequestBody.create(MediaType.parse("file12/*"), IMAGE_TWELEFile);
                desc12 = RequestBody.create(MediaType.parse("desc12"), desc12_st);
                body12 = MultipartBody.Part.createFormData("file12", IMAGE_TWELEFile.getName(), requestBody12);
            }
            if (IMAGE_THIRTEENFile != null) {
                RequestBody requestBody13 = RequestBody.create(MediaType.parse("file13/*"), IMAGE_THIRTEENFile);
                desc13 = RequestBody.create(MediaType.parse("desc13"), desc13_st);
                body13 = MultipartBody.Part.createFormData("file13", IMAGE_THIRTEENFile.getName(), requestBody13);
            }

            if (IMAGE_FOURTEENFile != null) {
                RequestBody requestBody14 = RequestBody.create(MediaType.parse("file14/*"), IMAGE_FOURTEENFile);
                desc14 = RequestBody.create(MediaType.parse("desc14"), desc14_st);
                body14 = MultipartBody.Part.createFormData("file14", IMAGE_FOURTEENFile.getName(), requestBody14);
            }
            if (IMAGE_FIFTEENFile != null) {
                RequestBody requestBody15 = RequestBody.create(MediaType.parse("file15/*"), IMAGE_FIFTEENFile);
                desc15 = RequestBody.create(MediaType.parse("desc15"), desc15_st);
                body15 = MultipartBody.Part.createFormData("file15", IMAGE_FIFTEENFile.getName(), requestBody15);
            }
            RequestBody update = RequestBody.create(MediaType.parse("text/plain"), "true");

            Log.i("token>>>", token.toString());
            Log.i("addpost>>", action.toString());
            Call<UpdateImagesResponse> updatecall = apiServices.ShipUpdated(token, action, hash,  body, body2
                    , body3, body4, body5, body6, body7, body8, body9, body10, body11, body12, body13, body14, body15, desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12, desc13, desc14, desc15
                    , update
                   );
            updatecall.enqueue(new Callback<UpdateImagesResponse>() {
                @Override
                public void onResponse(Call<UpdateImagesResponse> call, Response<UpdateImagesResponse> response) {
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//                        PreferencesManager.getInstance(context).setFileDesc(response.body().getFilesDesc());

                        AlertDialog.Builder builder = new AlertDialog.Builder(Demo.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        TextView heading = dialogView.findViewById(R.id.heading);
                        TextView body = dialogView.findViewById(R.id.body);
                        TextView ok = dialogView.findViewById(R.id.buttonOk);
                        heading.setText(R.string.dialog_heading);
                        body.setText("Images Updated Successfully");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                goToActivityWithFinish(Demo.this, MainContainer.class, null);
                            }
                        });


                    } else {
                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
                    }

                }


                @Override
                public void onFailure(Call<UpdateImagesResponse> call, Throwable t) {

                }
            });


        } catch (
                Exception e) {
            showMessage("Something went wrong please check token");
            e.printStackTrace();
        }

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

    class DownloadsImage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
                        try {
                            IMAGE_ONEfile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        IMAGE_ONEfile = fileONE;
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage2 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
                        try {
                            IMAGE_TWOFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        IMAGE_TWOFile = fileONE;
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage3 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_THREEFile = fileONE;
                        try {
                            IMAGE_THREEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage4 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_FOURFile = fileONE;
                        try {
                            IMAGE_FOURFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage5 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_FIVEFile = fileONE;
                        try {
                            IMAGE_FIVEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage6 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_SIXFile = fileONE;
                        try {
                            IMAGE_SIXFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage7 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_SEVENFile = fileONE;
                        try {
                            IMAGE_SEVENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage8 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_EIGHTFile = fileONE;
                        try {
                            IMAGE_EIGHTFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage9 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_NINEFile = fileONE;
                        try {
                            IMAGE_NINEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage10 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_TENFile = fileONE;
                        try {
                            IMAGE_TENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage11 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
//                        IMAGE_ELEVENFile = fileONE;
                        try {
                            IMAGE_ELEVENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage12 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
                        IMAGE_TWELEFile = fileONE;
                        try {
                            IMAGE_TWELEFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage13 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
                        IMAGE_THIRTEENFile = fileONE;
                        try {
                            IMAGE_THIRTEENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage14 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
                        IMAGE_FOURTEENFile = fileONE;
                        try {
                            IMAGE_FOURTEENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }

    class DownloadsImage15 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/TTG Storage"); //Creates app specific folder

            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(Demo.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        File fileONE = new File(path);
                        IMAGE_FIFTEENFile = fileONE;
                        try {
                            IMAGE_FIFTEENFile = new ImageZipper(Demo.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            showToast("Image Saved!");
        }
    }


}







