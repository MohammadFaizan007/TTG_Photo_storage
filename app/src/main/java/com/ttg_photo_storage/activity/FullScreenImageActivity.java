package com.ttg_photo_storage.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ttg_photo_storage.BuildConfig;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullScreenImageActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    String url = "";
    String comment = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        ButterKnife.bind(this);
        title.setText("Photo Details");
        url = getIntent().getStringExtra("image_url");
        comment = getIntent().getStringExtra("comment");
        ImageView fullScreenImageView = (ImageView) findViewById(R.id.fullScreenImageView);
        TextView comment_text = (TextView) findViewById(R.id.comment_text);
        Glide.with(this).load(BuildConfig.BASE_URL_FORIMAGE  +url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(fullScreenImageView);
        String sourceString = "<b>" + "Comment:  " + "</b> " + "<u>"+ comment+"</u>";
        comment_text.setText(Html.fromHtml(sourceString));

//        comment_text.setText(comment);
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

}
