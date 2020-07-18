package com.ttg_photo_storage.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.adapter.ShipmentAdapter;
import com.ttg_photo_storage.activity.adapter.SideShipmentAdapter;
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
import model.login.ViewShip.ShipmentsItem;
import model.login.ViewShip.ViewShipResponse;
import model.login.responsewithout_CRN.AllshipmentsItem;
import model.login.responsewithout_CRN.ResponseSIdeMenu;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShipmentActivity extends BaseActivity {
    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txt_Norecfound)
    TextView txtNorecfound;
    private List<ShipmentsItem> list;
    private List<AllshipmentsItem> allList;
    String formattedDate, dateStr, currentTimeDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ship);
        ButterKnife.bind(this);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("View Shipment");
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
        dateStr = curFormater.format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        formattedDate = sdf.format(cal.getTime());
        currentTimeDate = (dateStr + " " + formattedDate);
        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("client")) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getPost();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        } else if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("ship")) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getSideView();
            } else {
                showMessage(getResources().getString(R.string.alert_internet));
            }
        }

//        String sourceString = "<b>" + "CRN:  " + "</b> " + "<u>"+PreferencesManager.getInstance(context).getCrnID()+"</u>";
//        assedId.setText(Html.fromHtml(sourceString));


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.side_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    private void getPost() {
        try {
            showLoading();
            Call<ViewShipResponse> resultCall = apiServices.viewShip("getship", PreferencesManager.getInstance(context).getToken(), PreferencesManager.getInstance(context).getCrnID(), currentTimeDate);
            resultCall.enqueue(new Callback<ViewShipResponse>() {
                @Override
                public void onResponse(Call<ViewShipResponse> call, Response<ViewShipResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getShipments() != null) {
                            rvPost.setVisibility(View.VISIBLE);
                            txtNorecfound.setVisibility(View.GONE);
                            list = response.body().getShipments();
                            ShipmentAdapter adapter = new ShipmentAdapter(context, list, ViewShipmentActivity.this);
                            rvPost.setAdapter(adapter);
                        } else {
                            txtNorecfound.setVisibility(View.VISIBLE);
                            rvPost.setVisibility(View.INVISIBLE
                            );

                        }

                    }
                }

                @Override
                public void onFailure(Call<ViewShipResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            showMessage("Invalid ");
            e.printStackTrace();
        }
    }

    private void getSideView() {
        try {
            showLoading();
            Call<ResponseSIdeMenu> resultCall = apiServices.viewShip("getship", PreferencesManager.getInstance(context).getToken(), currentTimeDate);
            resultCall.enqueue(new Callback<ResponseSIdeMenu>() {
                @Override
                public void onResponse(Call<ResponseSIdeMenu> call, Response<ResponseSIdeMenu> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getAllshipments() != null) {
                            rvPost.setVisibility(View.VISIBLE);
                            txtNorecfound.setVisibility(View.GONE);
                            allList = response.body().getAllshipments();
                            SideShipmentAdapter adapter = new SideShipmentAdapter(context, allList, ViewShipmentActivity.this);
                            rvPost.setAdapter(adapter);
                        }
                    } else {
                        txtNorecfound.setVisibility(View.VISIBLE);
                        rvPost.setVisibility(View.INVISIBLE
                        );

                    }
                }

                @Override
                public void onFailure(Call<ResponseSIdeMenu> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            showMessage("Invalid ");
            e.printStackTrace();
        }
    }

}
