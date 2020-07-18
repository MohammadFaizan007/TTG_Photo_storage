package com.ttg_photo_storage.app;

import android.content.Context;
import android.content.SharedPreferences;


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
    private static final String PROFILE_PIC = "profile-pic";


//    shipment Details
    private static final String DATE = "date";
    private static final String TIMESHIP = "timeShip";
    private static final String NO_OF_VECHILE = "no_of_vechile";
    private static final String VECHILE_NUMBER = "vechile_number";
    private static final String VECHILE_TYPE = "vechile_type";
    private static final String COMPANY_NAME = "company_name";
    private static final String LOGISTIC_STAFF = "logistics_staff";
    private static final String NO_OF_BOXES = "no_Of_Boxes";
    private static final String NO_OF_PALLETS = "no_Of_pallets";
    private static final String NO_OF_DEVICES = "no_Of_devices";
    private static final String PACKAGING_QUALITY = "packaging_quality";
    private static final String SUPERVISOR_NAME = "supervisor_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String MESSAGE = "message";
    private static final String SIGNATURE_IMAGE = "signature_image";
    private static final String REASON_MESSAGE = "reason_message";
    private static final String ACCEPT = "accept";
    private static final String POSITION_HASH = "position_hash";
    private static final String POSITION_CRN = "position_crn";





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


    public String getProfilePic() {
        return mPref.getString(PROFILE_PIC, "");
    }

    public void setProfilePic(String value) {
        mPref.edit().putString(PROFILE_PIC, value).apply();
    }

    public String getDate() {
        return mPref.getString(DATE, "");
    }

    public void setDate(String value) {
        mPref.edit().putString(DATE, value).apply();
    }

    public String getTimeship() {
        return mPref.getString(TIMESHIP, "");
    }

    public void setTimeship(String value) {
        mPref.edit().putString(TIMESHIP, value).apply();
    }
    public String getNoOfVechile() {
        return mPref.getString(NO_OF_VECHILE, "");
    }

    public void setNoOfVechile(String value) {
        mPref.edit().putString(NO_OF_VECHILE, value).apply();
    }

    public String getVechileNumber() {
        return mPref.getString(VECHILE_NUMBER, "");
    }

    public void setVechileNumber(String value) {
        mPref.edit().putString(VECHILE_NUMBER, value).apply();
    }

    public String getVechileType() {
        return mPref.getString(VECHILE_TYPE, "");
    }

    public void setVechileType(String value) {
        mPref.edit().putString(VECHILE_TYPE, value).apply();
    }

    public String getCompanyName() {
        return mPref.getString(COMPANY_NAME, "");
    }

    public void setCompanyName(String value) {
        mPref.edit().putString(COMPANY_NAME, value).apply();
    }

    public String getLogistics_Staff() {
        return mPref.getString(LOGISTIC_STAFF, "");
    }

    public void setLogistics_Staff(String value) {
        mPref.edit().putString(LOGISTIC_STAFF, value).apply();
    }

    public String getNoOfBoxes() {
        return mPref.getString(NO_OF_BOXES, "");
    }

    public void setNoOfBoxes(String value) {
        mPref.edit().putString(NO_OF_BOXES, value).apply();
    }

    public String getNoOfPallets() {
        return mPref.getString(NO_OF_PALLETS, "");
    }

    public void setNoOfPallets(String value) {
        mPref.edit().putString(NO_OF_PALLETS, value).apply();
    }

    public String getNoOfDevices() {
        return mPref.getString(NO_OF_DEVICES, "");
    }

    public void setNoOfDevices(String value) {
        mPref.edit().putString(NO_OF_DEVICES, value).apply();
    }

    public String getPackagingQuality() {
        return mPref.getString(PACKAGING_QUALITY, "");

    }

    public void setPackagingQuality(String value) {
        mPref.edit().putString(PACKAGING_QUALITY, value).apply();
    }

    public String getSupervisorName() {
        return mPref.getString(SUPERVISOR_NAME, "");
    }

    public void setSupervisorName(String value) {
        mPref.edit().putString(SUPERVISOR_NAME, value).apply();
    }

    public String getPhoneNumber() {
        return mPref.getString(PHONE_NUMBER, "");
    }

    public void setPhoneNumber(String value) {
        mPref.edit().putString(PHONE_NUMBER, value).apply();
    }

    public String getMessage() {
        return mPref.getString(MESSAGE, "");
    }

    public void setMessage(String value) {
        mPref.edit().putString(MESSAGE, value).apply();
    }


    public String getSignatureImage() {
        return mPref.getString(SIGNATURE_IMAGE, "");
    }

    public void setSignatureImage(String value) {
        mPref.edit().putString(SIGNATURE_IMAGE, value).apply();
    }

    public String getReasonMessage() {
        return mPref.getString(REASON_MESSAGE, "");
    }

    public void setReasonMessage(String value) {
        mPref.edit().putString(REASON_MESSAGE, value).apply();
    }

    public String getAccept() {
        return mPref.getString(ACCEPT, "");
    }

    public void setAccept(String value) {
        mPref.edit().putString(ACCEPT, value).apply();
    }


    public String getPositionHash() {
        return mPref.getString(POSITION_HASH, "");
    }

    public void setPositionHash(String value) {
        mPref.edit().putString(POSITION_HASH, value).apply();
    }

    public String getPositionCrn() {
        return mPref.getString(POSITION_CRN, "");
    }

    public void setPositionCrn(String value) {
        mPref.edit().putString(POSITION_CRN, value).apply();
    }

}
