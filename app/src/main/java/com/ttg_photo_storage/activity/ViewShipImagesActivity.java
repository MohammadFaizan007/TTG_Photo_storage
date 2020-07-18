package com.ttg_photo_storage.activity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.adapter.PostResultAdapter;
import com.ttg_photo_storage.activity.adapter.ViewShipImagesAdapter;
import com.ttg_photo_storage.activity.adapter.ViewShipImagesSideAdapter;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.login.client.UIdResponse;
import model.login.detailsWithoutCrn.ResponseShipmentDetails;
import model.login.viewShipDetails.FilesItem;
import model.login.viewShipDetails.ViewShipDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShipImagesActivity extends BaseActivity {
    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
//    @BindView(R.id.assedId)
//    TextView assedId;
    @BindView(R.id.txt_Norecfound)
    TextView txtNorecfound;
    String uid = "";
    private List<FilesItem> list;
    private List<model.login.detailsWithoutCrn.FilesItem> allList;
    String formattedDate, dateStr, currentTimeDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ship);
        ButterKnife.bind(this);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("Photo List");
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
        dateStr = curFormater.format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        formattedDate = sdf.format(cal.getTime());
        currentTimeDate = (dateStr + " " + formattedDate);
        Log.e("DateAndTime=========>", currentTimeDate);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        hideKeyboard();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

    private void getViewShipDetails() {
        try {
            showLoading();
            Call<ViewShipDetailsResponse> profileCall = apiServices.viewShipDetails("getship", PreferencesManager.getInstance(context).getToken(), PreferencesManager.getInstance(context).getPositionCrn(), PreferencesManager.getInstance(context).getPositionHash(),currentTimeDate);
            profileCall.enqueue(new Callback<ViewShipDetailsResponse>() {
                @Override
                public void onResponse(Call<ViewShipDetailsResponse> call, Response<ViewShipDetailsResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getShipments() != null) {
                            rvPost.setVisibility(View.VISIBLE);
                            txtNorecfound.setVisibility(View.GONE);
                            list = response.body().getShipments().getFiles();
                            ViewShipImagesAdapter adapter = new ViewShipImagesAdapter(context, list, ViewShipImagesActivity.this);
                            rvPost.setAdapter(adapter);
                        }
                    }else {
                        txtNorecfound.setVisibility(View.VISIBLE);
                        rvPost.setVisibility(View.INVISIBLE

                        );

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
            Call<ResponseShipmentDetails> profileCall = apiServices.viewShipDetailsShip("getship", PreferencesManager.getInstance(context).getToken(),  PreferencesManager.getInstance(context).getPositionHash(),currentTimeDate);
            profileCall.enqueue(new Callback<ResponseShipmentDetails>() {
                @Override
                public void onResponse(Call<ResponseShipmentDetails> call, Response<ResponseShipmentDetails> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getAllshipments() != null) {
                            rvPost.setVisibility(View.VISIBLE);
                            txtNorecfound.setVisibility(View.GONE);
                            allList = response.body().getAllshipments().getFiles();
                            ViewShipImagesSideAdapter adapterSide = new ViewShipImagesSideAdapter(context,allList,ViewShipImagesActivity.this);
                            rvPost.setAdapter(adapterSide);
                            }
                    }else {
                        txtNorecfound.setVisibility(View.VISIBLE);
                        rvPost.setVisibility(View.INVISIBLE

                        );

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

}
