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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostResultActivity extends BaseActivity {
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
    String uid = "";
    private List<FilesItem> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygetpost);
        ButterKnife.bind(this);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("Photo List");
        uid = getIntent().getStringExtra("uid_id");
        PreferencesManager.getInstance(context).setCrnID(uid);
        Log.i("uid>>>",uid+", "+PreferencesManager.getInstance(context).getCrnID());

        String sourceString = "<b>" + "Asset ID:  " + "</b> " + "<u>"+PreferencesManager.getInstance(context).getUid()+"</u>";
        assedId.setText(Html.fromHtml(sourceString));
//        assedId.setText("Asset ID:  " + PreferencesManager.getInstance(context).getUid());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getPost();
        else
            showMessage(getResources().getString(R.string.alert_internet));
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
            Call<UIdResponse>resultCall = apiServices.post("getpost",PreferencesManager.getInstance(context).getUid(),PreferencesManager.getInstance(context).getToken(), Integer.valueOf("2"));
            resultCall.enqueue(new Callback<UIdResponse>() {
                @Override
                public void onResponse(Call<UIdResponse> call, Response<UIdResponse> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        rvPost.setVisibility(View.VISIBLE);
                        txtNorecfound.setVisibility(View.GONE);
                        list = response.body().getFiles();
                        PostResultAdapter adapter = new PostResultAdapter(context,list,PostResultActivity.this);
                        rvPost.setAdapter(adapter);
                    }else {
                        txtNorecfound.setVisibility(View.VISIBLE);
                        rvPost.setVisibility(View.INVISIBLE

                        );

                    }
                }

                @Override
                public void onFailure(Call<UIdResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            showMessage("Invalid ");
            e.printStackTrace();
        }
    }

}
