package com.ttg_photo_storage.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtils {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtils.getConnectivityStatus(context);
        String status = null;
        switch (conn) {
            case NetworkUtils.TYPE_WIFI:
                status = "Wifi enabled";
                break;
            case NetworkUtils.TYPE_MOBILE:
                status = "Mobile data enabled";
                break;
            case NetworkUtils.TYPE_NOT_CONNECTED:
                status = "Not connected to Internet";
                break;
        }
        return status;
    }
}
