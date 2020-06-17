package com.ttg_photo_storage.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttg_photo_storage.R;
import com.ttg_photo_storage.activity.adapter.CrnResultAdapter;
import com.ttg_photo_storage.activity.adapter.PostResultAdapter;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;
import com.ttg_photo_storage.utils.LoggerUtil;
import com.ttg_photo_storage.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.login.client.FilesItem;
import model.login.client.UIdResponse;
import model.login.crn.CrnResultResponse;
import model.login.crn.UidsItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrnResultActivity extends BaseActivity {
    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.assedId)
    TextView assedId;
    @BindView(R.id.txt_Norecfound)
    TextView txtNorecfound;
    private List<UidsItem> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygetpost);
        ButterKnife.bind(this);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("Asset ID List");

        String sourceString = "<b>" + "CRN:  " + "</b> " + "<u>"+PreferencesManager.getInstance(context).getCrnID()+"</u>";
        assedId.setText(Html.fromHtml(sourceString));
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getPost();
        else
            showMessage(getResources().getString(R.string.alert_internet));
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

    private void getPost() {
        try {
            showLoading();
            Call<CrnResultResponse>resultCall = apiServices.crnResult("getpost",PreferencesManager.getInstance(context).getToken(),PreferencesManager.getInstance(context).getCrnID());
            resultCall.enqueue(new Callback<CrnResultResponse>() {
                @Override
                public void onResponse(Call<CrnResultResponse> call, Response<CrnResultResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        rvPost.setVisibility(View.VISIBLE);
                        txtNorecfound.setVisibility(View.GONE);
                        list = response.body().getUids();
                        CrnResultAdapter adapter = new CrnResultAdapter(context,list, CrnResultActivity.this);
                        rvPost.setAdapter(adapter);
                    }else {
                        txtNorecfound.setVisibility(View.VISIBLE);
                        rvPost.setVisibility(View.INVISIBLE
                        );

                    }
                }

                @Override
                public void onFailure(Call<CrnResultResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            showMessage("Invalid ");
            e.printStackTrace();
        }
    }

}
