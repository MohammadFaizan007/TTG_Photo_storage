package com.ttg_photo_storage.app;

import android.app.Application;


import com.ttg_photo_storage.R;

import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@ReportsCrashes(formKey = "",
        mailTo = "faizanmohd106@gmail.com",
        mode = ReportingInteractionMode.SILENT,
        resToastText = R.string.crash_toast_text)

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .disableCustomViewInflation()
                .build());
    }
}
