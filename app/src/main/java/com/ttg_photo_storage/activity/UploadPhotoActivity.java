package com.ttg_photo_storage.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.developers.imagezipper.ImageZipper;
import com.github.chrisbanes.photoview.PhotoView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.FileUtils;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;
import com.ttg_photo_storage.utils.Utils;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;
import id.zelory.compressor.Compressor;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import model.login.shipUpload.ShipUploadResponse;
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
    @BindView(R.id.btn_submitShipment)
    Button btn_submitShipment;
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
    @BindView(R.id.cardThirteen)
    ConstraintLayout cardThirteen;
    @BindView(R.id.cardFourteen)
    ConstraintLayout cardFourteen;
    @BindView(R.id.cardFifteen)
    ConstraintLayout cardFifteen;
    Unbinder unbinder;
    ArrayList<FilesAcceptedItem> list = new ArrayList<FilesAcceptedItem>();
    ProgressDialog pd;
    Bitmap alteredBitmap;
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;


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
    long time;
    String formattedDate, dateStr;
    PhotoEditor mPhotoEditor;

    private String imageOne_pic = "";
    private String imageTwo_pic = "";
    private String imageThree_pic = "";
    private String imageFour_pic = "";
    private String imageFive_pic = "";
    private String imageSix_pic = "";

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Uploading Photo...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setMax(100);
        pd.show();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        ButterKnife.bind(this);
        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")) {
            title.setText("Upload Photo");
        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            title.setText("Add Shipment Image");
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
        } else {
            cardThirteen.setVisibility(View.GONE);
            cardFourteen.setVisibility(View.GONE);
            cardFifteen.setVisibility(View.GONE);
            btn_submitShipment.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            assedId.setVisibility(View.VISIBLE);

        }

        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
        dateStr = curFormater.format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        formattedDate = sdf.format(cal.getTime());


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
        desc7_st = des_seven.getText().toString().trim();
        desc8_st = des_Eight.getText().toString().trim();
        desc9_st = des_nine.getText().toString().trim();
        desc10_st = des_ten.getText().toString().trim();
        desc11_st = des_eleven.getText().toString().trim();
        desc12_st = des_twele.getText().toString().trim();
        desc13_st = des_thirteen.getText().toString().trim();
        desc14_st = des_fourteen.getText().toString().trim();
        desc15_st = des_fifteen.getText().toString().trim();

    }


    @OnClick({R.id.side_menu, R.id.imageOne, R.id.imageSecond, R.id.imageThird, R.id.imageFourth, R.id.imageFive, R.id.imageSix,
            R.id.imageSeven, R.id.imageEight, R.id.imageNine, R.id.imageTen, R.id.imageEleven, R.id.imageTwele,
            R.id.btn_submit, R.id.imageThirteen, R.id.imageFourteen, R.id.imageFifteen, R.id.btn_submitShipment})
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alertsecond = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        selection = SELECTION.IMAGE_TWO;
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert3 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert4 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert5 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert6 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert7 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert8 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert9 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert10 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert11 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert12 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert13 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert14 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                final AlertDialog.Builder alert15 = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
//                        getPhotoUpload();
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
//                            getShipUploadReject();
                        }
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }

        }


    }

    @Override
    public void onCancelClick() {

    }


    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            File outputFile = FileUtils.getFile(context, pickResult.getUri());
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
                        Intent intent = new ImageEditorIntentBuilder(this, pickResult.getPath(), outputFile.getAbsolutePath())
//                                .withAddText() // Add the features you need
                                .withRotateFeature()
                                .withPaintFeature()
//                                .withFilterFeature()
                                .withCropFeature()
//                                .withBrightnessFeature()
//                                .withSaturationFeature()
//                                .withBeautyFeature()
                                .withStickerFeature()
                                .forcePortrait(true)  // Add this to force portrait mode (It's set to false by default)
                                .build();
                        EditImageActivity.start(context, intent, PHOTO_EDITOR_REQUEST_CODE);
                    } catch (Exception e) {
                        Toast.makeText(this, R.string.iamutkarshtiwari_github_io_ananas_not_selected, Toast.LENGTH_SHORT).show();
                        Log.i("Demo App", e.getMessage());
                    }
                    break;
            }
        } else {
            Log.i("RESULT", "ERROR = " + pickResult.getError());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                            IMAGE_ONEfile = new ImageZipper(UploadPhotoActivity.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageOne_pic = "Image_one";

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
                            IMAGE_TWOFile = new ImageZipper(UploadPhotoActivity.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileTWO);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageTwo_pic = "Image_two";
//                        IMAGE_TWOFile = Compressor.getDefault(context).compressToFile(fileTWO);
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
                            IMAGE_THREEFile = new ImageZipper(UploadPhotoActivity.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileTHREE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageThree_pic = "Image_Three";
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
                            IMAGE_FOURFile = new ImageZipper(UploadPhotoActivity.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileFOUR);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageFour_pic = "Image_Four";

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
                            IMAGE_FIVEFile = new ImageZipper(UploadPhotoActivity.this)
                                    .setQuality(90)
                                    .setMaxWidth(520)
                                    .setMaxHeight(720)
                                    .compressToFile(fileFIVE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageFour_pic = "Image_Five";
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
                            IMAGE_SIXFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_SEVENFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_EIGHTFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_NINEFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_TENFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_ELEVENFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_TWELEFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_THIRTEENFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_FOURTEENFile = new ImageZipper(UploadPhotoActivity.this)
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
                            IMAGE_FIFTEENFile = new ImageZipper(UploadPhotoActivity.this)
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


    /*Selection Images*/
    private enum SELECTION {
        IMAGE_ONE, IMAGE_TWO, IMAGE_THREE, IMAGE_FOUR, IMAGE_FIVE, IMAGE_SIX, IMAGE_SEVEN, IMAGE_EIGHT, IMAGE_NINE, IMAGE_TEN, IMAGE_ELEVEN, IMAGE_TWELVE, IMAGE_THIRTEEN, IMAGE_FOURTEEN, IMAGE_FIFTEEN
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
            case IMAGE_SEVEN:
                pickSetup.setTitle("Choose Image Seven");
                break;
            case IMAGE_EIGHT:
                pickSetup.setTitle("Choose Image Eight");
                break;
            case IMAGE_NINE:
                pickSetup.setTitle("Choose Image Nine");
                break;
            case IMAGE_TEN:
                pickSetup.setTitle("Choose Image Ten");
                break;
            case IMAGE_ELEVEN:
                pickSetup.setTitle("Choose Image Eleven");
                break;
            case IMAGE_TWELVE:
                pickSetup.setTitle("Choose Image Twelve");
                break;
            case IMAGE_THIRTEEN:
                pickSetup.setTitle("Choose Image Thirteen");
                break;
            case IMAGE_FOURTEEN:
                pickSetup.setTitle("Choose Image Fourteen");
                break;
            case IMAGE_FIFTEEN:
                pickSetup.setTitle("Choose Image Fifteen");
                break;
        }

        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
//        pickSetup.setFlip(false);
//        pickSetup.setPickTypes(EPickType.GALLERY, EPickType.CAMERA);
        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(UploadPhotoActivity.this);

    }


//    public void getPhotoUpload() {
//        try {
//            showProgressDialog();
//            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
//            RequestBody addpost = RequestBody.create(MediaType.parse("text/plain"), "addpost");
//            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "details");
//
//            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null, body7 = null,
//                    body8 = null, body9 = null, body10 = null, body11 = null, body12 = null;
//            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null,
//                    desc7 = null, desc8 = null, desc9 = null, desc10 = null, desc11 = null, desc12 = null;
//
//            if (IMAGE_ONEfile != null) {
//                RequestBody requestBody = RequestBody.create(MediaType.parse("file1/*"), IMAGE_ONEfile);
//                desc1 = RequestBody.create(MediaType.parse("desc1"), desc1_st);
//                body = MultipartBody.Part.createFormData("file1", IMAGE_ONEfile.getName(), requestBody);
//            }
//            if (IMAGE_TWOFile != null) {
//                RequestBody requestBody2 = RequestBody.create(MediaType.parse("file2/*"), IMAGE_TWOFile);
//                desc2 = RequestBody.create(MediaType.parse("desc2"), desc2_st);
//                body2 = MultipartBody.Part.createFormData("file2", IMAGE_TWOFile.getName(), requestBody2);
//            }
//            if (IMAGE_THREEFile != null) {
//                RequestBody requestBody3 = RequestBody.create(MediaType.parse("file3/*"), IMAGE_THREEFile);
//                desc3 = RequestBody.create(MediaType.parse("desc3"), desc3_st);
//                body3 = MultipartBody.Part.createFormData("file3", IMAGE_THREEFile.getName(), requestBody3);
//            }
//            if (IMAGE_FOURFile != null) {
//                RequestBody requestBody4 = RequestBody.create(MediaType.parse("file4/*"), IMAGE_FOURFile);
//                desc4 = RequestBody.create(MediaType.parse("desc4"), desc4_st);
//                body4 = MultipartBody.Part.createFormData("file4", IMAGE_FOURFile.getName(), requestBody4);
//            }
//            if (IMAGE_FIVEFile != null) {
//                RequestBody requestBody5 = RequestBody.create(MediaType.parse("file5/*"), IMAGE_FIVEFile);
//                desc5 = RequestBody.create(MediaType.parse("desc5"), desc5_st);
//                body5 = MultipartBody.Part.createFormData("file5", IMAGE_FIVEFile.getName(), requestBody5);
//            }
//            if (IMAGE_SIXFile != null) {
//                RequestBody requestBody6 = RequestBody.create(MediaType.parse("file6/*"), IMAGE_SIXFile);
//                desc6 = RequestBody.create(MediaType.parse("desc6"), desc6_st);
//                body6 = MultipartBody.Part.createFormData("file6", IMAGE_SIXFile.getName(), requestBody6);
//            }
//
//            if (IMAGE_SEVENFile != null) {
//                RequestBody requestBody7 = RequestBody.create(MediaType.parse("file7/*"), IMAGE_SEVENFile);
//                desc7 = RequestBody.create(MediaType.parse("desc7"), desc7_st);
//                body7 = MultipartBody.Part.createFormData("file7", IMAGE_SEVENFile.getName(), requestBody7);
//            }
//
//            if (IMAGE_EIGHTFile != null) {
//                RequestBody requestBody8 = RequestBody.create(MediaType.parse("file8/*"), IMAGE_EIGHTFile);
//                desc8 = RequestBody.create(MediaType.parse("desc8"), desc8_st);
//                body8 = MultipartBody.Part.createFormData("file8", IMAGE_EIGHTFile.getName(), requestBody8);
//            }
//
//            if (IMAGE_NINEFile != null) {
//                RequestBody requestBody9 = RequestBody.create(MediaType.parse("file9/*"), IMAGE_NINEFile);
//                desc9 = RequestBody.create(MediaType.parse("desc9"), desc9_st);
//                body9 = MultipartBody.Part.createFormData("file9", IMAGE_NINEFile.getName(), requestBody9);
//            }
//
//            if (IMAGE_TENFile != null) {
//                RequestBody requestBody10 = RequestBody.create(MediaType.parse("file10/*"), IMAGE_TENFile);
//                desc10 = RequestBody.create(MediaType.parse("desc10"), desc10_st);
//                body10 = MultipartBody.Part.createFormData("file10", IMAGE_TENFile.getName(), requestBody10);
//            }
//
//            if (IMAGE_ELEVENFile != null) {
//                RequestBody requestBody11 = RequestBody.create(MediaType.parse("file11/*"), IMAGE_ELEVENFile);
//                desc11 = RequestBody.create(MediaType.parse("desc11"), desc11_st);
//                body11 = MultipartBody.Part.createFormData("file11", IMAGE_ELEVENFile.getName(), requestBody11);
//            }
//
//            if (IMAGE_TWELEFile != null) {
//                RequestBody requestBody12 = RequestBody.create(MediaType.parse("file12/*"), IMAGE_TWELEFile);
//                desc12 = RequestBody.create(MediaType.parse("desc12"), desc12_st);
//                body12 = MultipartBody.Part.createFormData("file12", IMAGE_TWELEFile.getName(), requestBody12);
//            }
//            RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getUid());
//            RequestBody crn = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCrnID());
//            Log.i("token>>>", token.toString());
//            Log.i("addpost>>", addpost.toString());
//            Log.i("description>>", description.toString());
//
//
//            Call<UploadPhotoResponse> photoCall = apiServices.UploadFile6(token
//                    , addpost, description,
//                    body, body2, body3, body4, body5, body6, body7, body8, body9, body10, body11, body12, desc1, desc2, desc3, desc4,
//                    desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12,
//                    uid, crn);
//
//            photoCall.enqueue(new Callback<UploadPhotoResponse>() {
//                @Override
//                public void onResponse(Call<UploadPhotoResponse> call, Response<UploadPhotoResponse> response) {
////                    hideLoading();
//                    pd.dismiss();
//                    LoggerUtil.logItem(response.body());
//                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//                        PreferencesManager.getInstance(context).setFileDesc(response.body().getFilesDesc());
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotoActivity.this);
//                        ViewGroup viewGroup = findViewById(android.R.id.content);
//                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
//                        builder.setView(dialogView);
//                        TextView heading = dialogView.findViewById(R.id.heading);
//                        TextView body = dialogView.findViewById(R.id.body);
//                        TextView ok = dialogView.findViewById(R.id.buttonOk);
//                        heading.setText(R.string.dialog_heading);
//                        body.setText(R.string.dialog_success);
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();
//                        ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                                goToActivityWithFinish(UploadPhotoActivity.this, MainContainer.class, null);
//                            }
//                        });
//
//
//                    } else {
//                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UploadPhotoResponse> call, Throwable t) {
//
//                }
//            });
//
//
//        } catch (
//                Exception e) {
//            showMessage("Something went wrong please check token");
//            e.printStackTrace();
//        }
//
//    }

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
                    no_of_staff, no_of_box, no_of_pallets, no_of_devices, no_of_vahicle, supervisor_ph_no, is_reject,box_seal,eWayBil);
            photocall.enqueue(new Callback<ShipUploadResponse>() {
                @Override
                public void onResponse(Call<ShipUploadResponse> call, Response<ShipUploadResponse> response) {
                    pd.dismiss();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//                        PreferencesManager.getInstance(context).setFileDesc(response.body().getFilesDesc());

                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotoActivity.this);
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
                                goToActivityWithFinish(UploadPhotoActivity.this, MainContainer.class, null);
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

//    public void getShipUploadReject() {
//        try {
//            showProgressDialog();
//            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getToken());
//            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "addship");
//            RequestBody crn = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCrnID());
//
//            MultipartBody.Part body = null, body2 = null, body3 = null, body4 = null, body5 = null, body6 = null, body7 = null,
//                    body8 = null, body9 = null, body10 = null, body11 = null, body12 = null, body13 = null, body14 = null, body15 = null, signatureBody = null;
//            RequestBody desc1 = null, desc2 = null, desc3 = null, desc4 = null, desc5 = null, desc6 = null,
//                    desc7 = null, desc8 = null, desc9 = null, desc10 = null, desc11 = null, desc12 = null, desc13 = null, desc14 = null, desc15 = null;
//
//            if (IMAGE_ONEfile != null) {
//                RequestBody requestBody = RequestBody.create(MediaType.parse("file1/*"), IMAGE_ONEfile);
//                desc1 = RequestBody.create(MediaType.parse("desc1"), desc1_st);
//                body = MultipartBody.Part.createFormData("file1", IMAGE_ONEfile.getName(), requestBody);
//            }
//            if (IMAGE_TWOFile != null) {
//                RequestBody requestBody2 = RequestBody.create(MediaType.parse("file2/*"), IMAGE_TWOFile);
//                desc2 = RequestBody.create(MediaType.parse("desc2"), desc2_st);
//                body2 = MultipartBody.Part.createFormData("file2", IMAGE_TWOFile.getName(), requestBody2);
//            }
//            if (IMAGE_THREEFile != null) {
//                RequestBody requestBody3 = RequestBody.create(MediaType.parse("file3/*"), IMAGE_THREEFile);
//                desc3 = RequestBody.create(MediaType.parse("desc3"), desc3_st);
//                body3 = MultipartBody.Part.createFormData("file3", IMAGE_THREEFile.getName(), requestBody3);
//            }
//            if (IMAGE_FOURFile != null) {
//                RequestBody requestBody4 = RequestBody.create(MediaType.parse("file4/*"), IMAGE_FOURFile);
//                desc4 = RequestBody.create(MediaType.parse("desc4"), desc4_st);
//                body4 = MultipartBody.Part.createFormData("file4", IMAGE_FOURFile.getName(), requestBody4);
//            }
//            if (IMAGE_FIVEFile != null) {
//                RequestBody requestBody5 = RequestBody.create(MediaType.parse("file5/*"), IMAGE_FIVEFile);
//                desc5 = RequestBody.create(MediaType.parse("desc5"), desc5_st);
//                body5 = MultipartBody.Part.createFormData("file5", IMAGE_FIVEFile.getName(), requestBody5);
//            }
//            if (IMAGE_SIXFile != null) {
//                RequestBody requestBody6 = RequestBody.create(MediaType.parse("file6/*"), IMAGE_SIXFile);
//                desc6 = RequestBody.create(MediaType.parse("desc6"), desc6_st);
//                body6 = MultipartBody.Part.createFormData("file6", IMAGE_SIXFile.getName(), requestBody6);
//            }
//
//            if (IMAGE_SEVENFile != null) {
//                RequestBody requestBody7 = RequestBody.create(MediaType.parse("file7/*"), IMAGE_SEVENFile);
//                desc7 = RequestBody.create(MediaType.parse("desc7"), desc7_st);
//                body7 = MultipartBody.Part.createFormData("file7", IMAGE_SEVENFile.getName(), requestBody7);
//            }
//
//            if (IMAGE_EIGHTFile != null) {
//                RequestBody requestBody8 = RequestBody.create(MediaType.parse("file8/*"), IMAGE_EIGHTFile);
//                desc8 = RequestBody.create(MediaType.parse("desc8"), desc8_st);
//                body8 = MultipartBody.Part.createFormData("file8", IMAGE_EIGHTFile.getName(), requestBody8);
//            }
//
//            if (IMAGE_NINEFile != null) {
//                RequestBody requestBody9 = RequestBody.create(MediaType.parse("file9/*"), IMAGE_NINEFile);
//                desc9 = RequestBody.create(MediaType.parse("desc9"), desc9_st);
//                body9 = MultipartBody.Part.createFormData("file9", IMAGE_NINEFile.getName(), requestBody9);
//            }
//
//            if (IMAGE_TENFile != null) {
//                RequestBody requestBody10 = RequestBody.create(MediaType.parse("file10/*"), IMAGE_TENFile);
//                desc10 = RequestBody.create(MediaType.parse("desc10"), desc10_st);
//                body10 = MultipartBody.Part.createFormData("file10", IMAGE_TENFile.getName(), requestBody10);
//            }
//
//            if (IMAGE_ELEVENFile != null) {
//                RequestBody requestBody11 = RequestBody.create(MediaType.parse("file11/*"), IMAGE_ELEVENFile);
//                desc11 = RequestBody.create(MediaType.parse("desc11"), desc11_st);
//                body11 = MultipartBody.Part.createFormData("file11", IMAGE_ELEVENFile.getName(), requestBody11);
//            }
//
//            if (IMAGE_TWELEFile != null) {
//                RequestBody requestBody12 = RequestBody.create(MediaType.parse("file12/*"), IMAGE_TWELEFile);
//                desc12 = RequestBody.create(MediaType.parse("desc12"), desc12_st);
//                body12 = MultipartBody.Part.createFormData("file12", IMAGE_TWELEFile.getName(), requestBody12);
//            }
//            if (IMAGE_THIRTEENFile != null) {
//                RequestBody requestBody13 = RequestBody.create(MediaType.parse("file13/*"), IMAGE_THIRTEENFile);
//                desc13 = RequestBody.create(MediaType.parse("desc13"), desc13_st);
//                body13 = MultipartBody.Part.createFormData("file13", IMAGE_THIRTEENFile.getName(), requestBody13);
//            }
//
//            if (IMAGE_FOURTEENFile != null) {
//                RequestBody requestBody14 = RequestBody.create(MediaType.parse("file14/*"), IMAGE_FOURTEENFile);
//                desc14 = RequestBody.create(MediaType.parse("desc14"), desc14_st);
//                body14 = MultipartBody.Part.createFormData("file14", IMAGE_FOURTEENFile.getName(), requestBody14);
//            }
//            if (IMAGE_FIFTEENFile != null) {
//                RequestBody requestBody15 = RequestBody.create(MediaType.parse("file15/*"), IMAGE_FIFTEENFile);
//                desc15 = RequestBody.create(MediaType.parse("desc15"), desc15_st);
//                body15 = MultipartBody.Part.createFormData("file15", IMAGE_FIFTEENFile.getName(), requestBody15);
//            }
//
//            RequestBody input_time = RequestBody.create(MediaType.parse("text/plain"), dateStr + " " + formattedDate);
//            RequestBody ship_time = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getDate() + " " + PreferencesManager.getInstance(context).getTimeship());
//            RequestBody logistic_company = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getCompanyNameReject());
//            RequestBody vahicle_type = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody vahicle_container = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody vahicle_number = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody box_condition = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody supervisor_name = RequestBody.create(MediaType.parse("text/plain"), "");
////            if (SignatureFile != null) {
////                RequestBody requestBodySignature = RequestBody.create(MediaType.parse("file15/*"), SignatureFile);
////                signatureBody = MultipartBody.Part.createFormData("supervisor_sign", SignatureFile.getName(), requestBodySignature);
////
////            }
//
//            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getReasonMessage());
//            RequestBody no_of_box = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody no_of_pallets = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody no_of_devices = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody no_of_vahicle = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody supervisor_ph_no = RequestBody.create(MediaType.parse("text/plain"), "");
//            RequestBody is_reject = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getAccept());
//
//            Log.i("token>>>", token.toString());
//            Log.i("addpost>>", action.toString());
//            Call<ShipUploadResponse> photocall = apiServices.ShipUploadResject(token, action, crn, body, body2
//                    , body3, body4, body5, body6, body7, body8, body9, body10, body11, body12, body13, body14, body15, desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9, desc10, desc11, desc12, desc13, desc14, desc15
//                    , input_time, ship_time, logistic_company, vahicle_type, vahicle_container, vahicle_number, box_condition, supervisor_name, note,
//                    no_of_box, no_of_pallets, no_of_devices, no_of_vahicle, supervisor_ph_no, is_reject);
//            photocall.enqueue(new Callback<ShipUploadResponse>() {
//                @Override
//                public void onResponse(Call<ShipUploadResponse> call, Response<ShipUploadResponse> response) {
//                    pd.dismiss();
//                    LoggerUtil.logItem(response.body());
//                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotoActivity.this);
//                        ViewGroup viewGroup = findViewById(android.R.id.content);
//                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
//                        builder.setView(dialogView);
//                        TextView heading = dialogView.findViewById(R.id.heading);
//                        TextView body = dialogView.findViewById(R.id.body);
//                        TextView ok = dialogView.findViewById(R.id.buttonOk);
//                        heading.setText(R.string.dialog_heading);
//                        body.setText(R.string.ship_success);
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();
//                        ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                                goToActivityWithFinish(UploadPhotoActivity.this, MainContainer.class, null);
//                            }
//                        });
//
//
//                    } else {
//                        showToastS(response.body().getStatus() + "\nInvalid Token Credential");
//                    }
//
//                }
//
//
//                @Override
//                public void onFailure(Call<ShipUploadResponse> call, Throwable t) {
//
//                }
//            });
//
//
//        } catch (
//                Exception e) {
//            showMessage("Something went wrong please check token");
//            e.printStackTrace();
//        }
//
//    }


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




