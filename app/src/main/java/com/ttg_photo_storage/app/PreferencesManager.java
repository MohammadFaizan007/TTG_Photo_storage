package com.ttg_photo_storage.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Time;


public class PreferencesManager {

    //app login variables
    private static final String PREF_NAME = "com.ttg_photo_storage";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String MOBILE = "mobile";
    private static final String TOKEN = "token";
    private static final String USERID = "user_ID";
    private static final String COUNTRY = "country";
    private static final String TIME = "time";
    private static final String UID = "uid";
    private static final String CRN = "crn";
    private static final String Email = "email";
    private static final String FILEDESC = "filedesc";
    private static final String ONEIMAGE = "oneimage";



    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //for fragment
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    //for getting instance
    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    public boolean clear() {
        return mPref.edit().clear().commit();
    }


    public String getNAME() {
        return mPref.getString(NAME, "");
    }

    public void setNAME(String value) {
        mPref.edit().putString(NAME, value).apply();
    }


    public String getType() {
        return mPref.getString(TYPE, "");
    }

    public void setType(String value) {
        mPref.edit().putString(TYPE, value).apply();
    }

    public String getToken() {
        return mPref.getString(TOKEN, "");
    }

    public void setToken(String value) {
        mPref.edit().putString(TOKEN, value).apply();
    }

    public String getUserid() {
        return mPref.getString(USERID, "");
    }

    public void setUserid(String value) {
        mPref.edit().putString(USERID, value).apply();
    }


    public String getMobile() {
        return mPref.getString(MOBILE, "");
    }

    public void setMobile(String value) {
        mPref.edit().putString(MOBILE, value).apply();
    }

    public String getCountry() {
        return mPref.getString(COUNTRY, "");
    }

    public void setCountry(String value) {
        mPref.edit().putString(COUNTRY, value).apply();
    }

    public String getTime() {
        return mPref.getString(TIME, "");
    }

    public void setTime(String value) {
        mPref.edit().putString(TIME, value).apply();
    }
    public String getUid() {
        return mPref.getString(UID, "");
    }

    public void setUID(String value) {
        mPref.edit().putString(UID, value).apply();
    }


    public String getCrnID() {
        return mPref.getString(CRN, "");
    }

    public void setCrnID(String value) {
        mPref.edit().putString(CRN, value).apply();
    }

    public String getEmail() {
        return mPref.getString(Email, "");
    }

    public void setEmail(String value) {
        mPref.edit().putString(Email, value).apply();
    }

    public String getFiledesc() {
        return mPref.getString(FILEDESC, "");
    }

    public void setFileDesc(String value) {
        mPref.edit().putString(FILEDESC, value).apply();
    }


    public String getOneimage() {
        return mPref.getString(ONEIMAGE, "");
    }

    public void setOneImage(String value) {
        mPref.edit().putString(ONEIMAGE, value).apply();
    }



}
