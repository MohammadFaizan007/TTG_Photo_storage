package com.ttg_photo_storage.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.constants.CustomImageView;
import com.ttg_photo_storage.constants.DrawableView;
import com.ttg_photo_storage.utils.FileUtils;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;
import com.ttg_photo_storage.utils.Utils;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import model.login.upload.FilesAcceptedItem;
import model.login.upload.UploadPhotoResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoActivity extends BaseActivity implements IPickCancel, IPickResult {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_submit)
    Button btn_submit;
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
    Unbinder unbinder;
    ArrayList<FilesAcceptedItem> list = new ArrayList<FilesAcceptedItem>();
    ProgressDialog pd;
    Bitmap bmp;
    Bitmap alteredBitmap;


    int img_count = 0;

    private String imageOne_pic = "";
    private String imageTwo_pic = "";
    private String imageThree_pic = "";
    private String imageFour_pic = "";
    private String imageFive_pic = "";
    private String imageSix_pic = "";

    private String IMAGE_ONE = "file1";
    private String IMAGE_TWO = "file2";
    private String IMAGE_THREE = "file3";
    private String IMAGE_FOUR = "file4";
    private String IMAGE_FIVE = "file5";
    private String IMAGE_SIX = "file6";


    private String desc1_st = "";
    private String desc2_st = "";
    private String desc3_st = "";
    private String desc4_st = "";
    private String desc5_st = "";
    private String desc6_st = "";

    private File IMAGE_ONEfile;
    private File IMAGE_TWOFile;
    private File IMAGE_THREEFile;
    private File IMAGE_FOURFile;
    private File IMAGE_FIVEFile;
    private File IMAGE_SIXFile;

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Uploading Photo...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
//        pd.setMax(100);
        pd.show();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        ButterKnife.bind(this);
        title.setText("Upload Photo");

        String sourceString = "<b>" + "CRN:  " + "</b> " + PreferencesManager.getInstance(context).getCrnID();
        crnID.setText(Html.fromHtml(sourceString));


        String sourceString2 = "<b>" + "Asset ID:  " + "</b> " + PreferencesManager.getInstance(context).getUid();
        assedId.setText(Html.fromHtml(sourceString2));


//        crnID.setText("CRN:"+PreferencesManager.getInstance(context).getCrnID());
//        assedId.setText("<b>Asset ID:</b>"+PreferencesManager.getInstance(context).getUid());
        init();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void init() {
        desc1_st = des_one.getText().toString().trim();
        desc2_st = des_two.getText().toString().trim();
        desc3_st = des_three.getText().toString().trim();
        desc4_st = des_four.getText().toString().trim();
        desc5_st = des_five.getText().toString().trim();
        desc6_st = des_six.getText().toString().trim();
    }


    @OnClick({R.id.side_menu, R.id.imageOne, R.id.imageSecond, R.id.imageThird, R.id.imageFourth, R.id.imageFive, R.id.imageSix, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.imageOne:
                final AlertDialog.Builder alert = new AlertDialog.Builder(UploadPhotoActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image = (TextView) mView.findViewById(R.id.upload_image);
                TextView view_image = (TextView) mView.findViewById(R.id.view_image);
                TextView edit_image = (TextView) mView.findViewById(R.id.edit_image);
                TextView remove_image = (TextView) mView.findViewById(R.id.remove_image);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
                upload_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        selection = SELECTION.IMAGE_ONE;
                        showDialog();
                    }
                });
                if (IMAGE_ONEfile == null) {
                    view_image.setVisibility(View.GONE);
//                    edit_image.setVisibility(View.VISIBLE);
                    remove_image.setVisibility(View.GONE);

                } else {
                    view_image.setVisibility(View.VISIBLE);
                    remove_image.setVisibility(View.VISIBLE);
//                    edit_image.setVisibility(View.VISIBLE);
                }
//

                view_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_ONEfile.getAbsolutePath());
                        photoView.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap).into(photoView);

                        mBuilder.setView(mView);
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

//                edit_image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                        Button enableZoomBtn;
//                        DrawableView drawbleView;
//                        CustomImageView setImage;
//                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
//                        View mView = getLayoutInflater().inflate(R.layout.custom_dialog_edit, null);
//                        drawbleView = (DrawableView) findViewById(R.id.drawble_view);
//                        enableZoomBtn = (Button) findViewById(R.id.enable_zoom);
//                        setImage = (CustomImageView) findViewById(R.id.zoom_iv);
////                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_ONEfile.getAbsolutePath());
////                        setImage.set(R.drawable.choose_photo);
////                        enableZoomBtn.setText("EDIT");
////                        Glide.with(context).load(bitmap).into(touchImageView);
//                        mBuilder.setView(mView);
//                        AlertDialog mDialog = mBuilder.create();
//                        mDialog.show();
//
//                    }
//                });
                remove_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to Remove Image ")
                                .setTitle("Remove Image");
                        builder.setPositiveButton("delete", (dialog1, id) -> {
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
                final AlertDialog.Builder alertsecond = new AlertDialog.Builder(UploadPhotoActivity.this);
                View fView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image2 = (TextView) fView.findViewById(R.id.upload_image);
                TextView view_image2 = (TextView) fView.findViewById(R.id.view_image);

                TextView edit_image2 = (TextView) fView.findViewById(R.id.edit_image);
                TextView remove_image2 = (TextView) fView.findViewById(R.id.remove_image);

                alertsecond.setView(fView);
                final AlertDialog alertDialog2 = alertsecond.create();
                alertDialog2.setCanceledOnTouchOutside(true);
                alertDialog2.show();
                upload_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                        selection = SELECTION.IMAGE_TWO;
                        showDialog();
                    }
                });
                if (IMAGE_TWOFile == null) {
                    view_image2.setVisibility(View.GONE);
//                    edit_image.setVisibility(View.VISIBLE);
                    remove_image2.setVisibility(View.GONE);

                } else {
                    view_image2.setVisibility(View.VISIBLE);
                    remove_image2.setVisibility(View.VISIBLE);
//                    edit_image.setVisibility(View.VISIBLE);
                }
                view_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_TWOFile.getAbsolutePath());
                        photoView.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap).into(photoView);
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
                        builder.setPositiveButton("delete", (dialog1, id) -> {
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
                final AlertDialog.Builder alert3 = new AlertDialog.Builder(UploadPhotoActivity.this);
                View hView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image3 = (TextView) hView.findViewById(R.id.upload_image);
                TextView view_image3 = (TextView) hView.findViewById(R.id.view_image);
                TextView edit_image3 = (TextView) hView.findViewById(R.id.edit_image);
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
//                    edit_image.setVisibility(View.VISIBLE);
                    remove_image3.setVisibility(View.GONE);

                } else {
                    view_image3.setVisibility(View.VISIBLE);
                    remove_image3.setVisibility(View.VISIBLE);
//                    edit_image.setVisibility(View.VISIBLE);
                }
                view_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog3.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_THREEFile.getAbsolutePath());
                        photoView.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap).into(photoView);
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
                        builder.setPositiveButton("delete", (dialog1, id) -> {
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
                final AlertDialog.Builder alert4 = new AlertDialog.Builder(UploadPhotoActivity.this);
                View kView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image4 = (TextView) kView.findViewById(R.id.upload_image);
                TextView view_image4 = (TextView) kView.findViewById(R.id.view_image);
                TextView edit_image4 = (TextView) kView.findViewById(R.id.edit_image);
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
//                    edit_image.setVisibility(View.VISIBLE);
                    remove_image4.setVisibility(View.GONE);

                } else {
                    view_image4.setVisibility(View.VISIBLE);
                    remove_image4.setVisibility(View.VISIBLE);
//                    edit_image.setVisibility(View.VISIBLE);
                }

                view_image4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog4.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_FOURFile.getAbsolutePath());
                        photoView.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap).into(photoView);
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
                        builder.setPositiveButton("delete", (dialog1, id) -> {
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
                final AlertDialog.Builder alert5 = new AlertDialog.Builder(UploadPhotoActivity.this);
                View pView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image5 = (TextView) pView.findViewById(R.id.upload_image);
                TextView view_image5 = (TextView) pView.findViewById(R.id.view_image);
                TextView edit_image5 = (TextView) pView.findViewById(R.id.edit_image);
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
//                    edit_image.setVisibility(View.VISIBLE);
                    remove_image5.setVisibility(View.GONE);

                } else {
                    view_image5.setVisibility(View.VISIBLE);
                    remove_image5.setVisibility(View.VISIBLE);
//                    edit_image.setVisibility(View.VISIBLE);
                }
                view_image5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog5.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_FIVEFile.getAbsolutePath());
                        photoView.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap).into(photoView);
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
                        builder.setPositiveButton("delete", (dialog1, id) -> {
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
                final AlertDialog.Builder alert6 = new AlertDialog.Builder(UploadPhotoActivity.this);
                View zView = getLayoutInflater().inflate(R.layout.custom_dialog_photo, null);
                TextView upload_image6 = (TextView) zView.findViewById(R.id.upload_image);
                TextView view_image6 = (TextView) zView.findViewById(R.id.view_image);
                TextView edit_image6 = (TextView) zView.findViewById(R.id.edit_image);
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
//                    edit_image.setVisibility(View.VISIBLE);
                    remove_image6.setVisibility(View.GONE);

                } else {
                    view_image6.setVisibility(View.VISIBLE);
                    remove_image6.setVisibility(View.VISIBLE);
//                    edit_image.setVisibility(View.VISIBLE);
                }
                view_image6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog6.dismiss();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                        PhotoView photoView = mView.findViewById(R.id.imageView);
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_SIXFile.getAbsolutePath());
                        photoView.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap).into(photoView);
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
                        builder.setPositiveButton("delete", (dialog1, id) -> {
                            dialog1.dismiss();
                            File file = new File(IMAGE_SIXFile.getAbsolutePath());
                            if(file.exists()){
                                file.delete();
                                IMAGE_SIXFile = null;
                                des_six.setText("");
                                imageSix.setImageBitmap(null);
                                imageSix.setImageResource(R.drawable.choose_photo);
                            }else {

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

        }


    }

    @Override
    public void onCancelClick() {

    }


    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            switch (selection) {
                case IMAGE_ONE:
                case IMAGE_TWO:
                case IMAGE_THREE:
                case IMAGE_FOUR:
                case IMAGE_FIVE:
                case IMAGE_SIX:
//                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
//                            .setAspectRatio(1, 1)
//                            .start(context);

                    CropImage.activity(pickResult.getUri())
//                            .setCropShape(CropImageView.CropShape.OVAL)
                            .setInitialCropWindowPaddingRatio(0)
//                            .setAspectRatio(1, 1)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(context);
                    break;
            }
        } else {
            Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            switch (selection) {
                case IMAGE_ONE:
                    if (resultCode == RESULT_OK) {
                        IMAGE_ONEfile = FileUtils.getFile(context, result.getUri());
                        Log.e("Cheeck", IMAGE_ONEfile.toString());
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_ONEfile.getAbsolutePath());
                        imageOne.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap)
                                .placeholder(R.drawable.choose_photo).
                                error(R.drawable.choose_photo).into(imageOne);

//                        Glide.with(context).load(result.getUri()).
//                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
//                                        .placeholder(R.drawable.choose_photo)
//                                        .error(R.drawable.choose_photo))
//                                .into(imageSecond);

                        imageOne_pic = "Image_one";
                        img_count = 1;
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();

                    }
                    break;
                case IMAGE_TWO:
                    if (resultCode == RESULT_OK) {
                        IMAGE_TWOFile = FileUtils.getFile(context, result.getUri());
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_TWOFile.getAbsolutePath());
                        imageSecond.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap)
                                .placeholder(R.drawable.choose_photo)
                                .error(R.drawable.choose_photo)
                                .into(imageSecond);

//                        Glide.with(context).load(result.getUri()).
//                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
//                                        .placeholder(R.drawable.login_logo)
//                                        .error(R.drawable.login_logo))
//                                .into(imageSecond);

                        imageTwo_pic = "Image_Two";
                        img_count = 2;
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;
                case IMAGE_THREE:
                    if (resultCode == RESULT_OK) {
                        IMAGE_THREEFile = FileUtils.getFile(context, result.getUri());

                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_THREEFile.getAbsolutePath());
                        imageThird.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap)
                                .placeholder(R.drawable.choose_photo)
                                .error(R.drawable.choose_photo)
                                .into(imageThird);

//                        Glide.with(context).load(result.getUri()).
//                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
//                                        .placeholder(R.drawable.login_logo)
//                                        .error(R.drawable.login_logo))
//                                .into(imageThird);

                        imageThree_pic = "Image_Three";
                        img_count = 3;
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;
                case IMAGE_FOUR:
                    if (resultCode == RESULT_OK) {
                        IMAGE_FOURFile = FileUtils.getFile(context, result.getUri());
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_FOURFile.getAbsolutePath());
                        imageFourth.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap)
                                .placeholder(R.drawable.choose_photo)
                                .error(R.drawable.choose_photo)
                                .into(imageFourth);

//                        Glide.with(context).load(result.getUri()).
//                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
//                                        .placeholder(R.drawable.login_logo)
//                                        .error(R.drawable.login_logo))
//                                .into(imageFourth);

                        imageFour_pic = "Image_Four";
                        img_count = 4;
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;
                case IMAGE_FIVE:
                    if (resultCode == RESULT_OK) {
                        IMAGE_FIVEFile = FileUtils.getFile(context, result.getUri());
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_FIVEFile.getAbsolutePath());
                        imageFive.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap)
                                .placeholder(R.drawable.choose_photo)
                                .error(R.drawable.choose_photo)
                                .into(imageFive);

//                        Glide.with(context).load(result.getUri()).
//                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
//                                        .placeholder(R.drawable.login_logo)
//                                        .error(R.drawable.login_logo))
//                                .into(imageFive);

                        imageFour_pic = "Image_Five";
                        img_count = 5;

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;

                case IMAGE_SIX:
                    if (resultCode == RESULT_OK) {
                        IMAGE_SIXFile = FileUtils.getFile(context, result.getUri());
                        Bitmap bitmap = Utils.getCompressedBitmap(IMAGE_SIXFile.getAbsolutePath());
                        imageSix.setImageBitmap(bitmap);
                        Glide.with(context).load(bitmap)
                                .placeholder(R.drawable.choose_photo)
                                .error(R.drawable.choose_photo)
                                .into(imageSix);

//                        Glide.with(context).load(result.getUri()).
//                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
//                                        .placeholder(R.drawable.login_logo)
//                                        .error(R.drawable.login_logo))
//                                .into(imageSix);
                        imageFour_pic = "Image_Six";
                        img_count = 6;


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;
            }
        }
    }


    /*Selection Images*/
    private enum SELECTION {
        IMAGE_ONE, IMAGE_TWO, IMAGE_THREE, IMAGE_FOUR, IMAGE_FIVE, IMAGE_SIX
    }

    private SELECTION selection;
    private PickImageDialog dialog;

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case IMAGE_ONE:
                pickSetup.setTitle("Choose Image One");
                break;
            case IMAGE_TWO:
                pickSetup.setTitle("Choose Image Two");
                break;
            case IMAGE_THREE:
                pickSetup.setTitle("Choose Image Three");
                break;
            case IMAGE_FOUR:
                pickSetup.setTitle("Choose Image Four");
                break;
            case IMAGE_FIVE:
                pickSetup.setTitle("Choose Image Five");
                break;
            case IMAGE_SIX:
                pickSetup.setTitle("Choose Image Six");
                break;
        }

        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(this);

    }


    public void getPhotoUpload() {
        try {
            showProgressDialog();
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
            RequestBody addpost = RequestBody.create(MediaType.parse("text/plain"), "addpost");
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "details");

            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null;
            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null;

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
            RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getUid());
            RequestBody crn = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCrnID());
            Log.i("token>>>", token.toString());
            Log.i("addpost>>", addpost.toString());
            Log.i("description>>", description.toString());


            Call<UploadPhotoResponse> photoCall = apiServices.UploadFile6(token
                    , addpost, description,
                    body, body2, body3, body4, body5, body6, desc1, desc2, desc3, desc4, desc5, desc6,
                    uid, crn);

            photoCall.enqueue(new Callback<UploadPhotoResponse>() {
                @Override
                public void onResponse(Call<UploadPhotoResponse> call, Response<UploadPhotoResponse> response) {
//                    hideLoading();
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        PreferencesManager.getInstance(context).setFileDesc(response.body().getFilesDesc());

                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                                goToActivityWithFinish(UploadPhotoActivity.this, MainContainer.class, null);
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

    private boolean Validation() {
        try {

            desc1_st = des_one.getText().toString().trim();
            desc2_st = des_two.getText().toString().trim();
            desc3_st = des_three.getText().toString().trim();
            desc4_st = des_four.getText().toString().trim();
            desc5_st = des_five.getText().toString().trim();
            desc6_st = des_six.getText().toString().trim();

            if (IMAGE_ONEfile == null && IMAGE_TWOFile == null && IMAGE_THREEFile == null && IMAGE_FOURFile == null
                    && IMAGE_FIVEFile == null && IMAGE_SIXFile == null) {
                showError("Please select at least one image", des_one);
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




